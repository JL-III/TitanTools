package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.TitanEnchants;
import com.playtheatria.jliii.generalutils.items.CustomModelData;
import com.playtheatria.jliii.generalutils.items.TitanItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.disableEffect;
import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.enableEffect;

public class ToggleAncientPower implements Listener {

    private record ItemCheck(
            boolean hasItemMeta,
            boolean isTitanTool,
            boolean isAllowedTitanType,
            boolean isCharged,
            boolean isImbued,
            boolean isActive
    ) {};

    @EventHandler
    public void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        if (processPlayerStateValidation(event.getPlayer())) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;
            ItemCheck itemCheck = retrieveItemRecord(itemInMainHand);
            if (processItemValidation(itemCheck)) {
                player.setCooldown(coolDown,25);
                event.setCancelled(true);
                toggleEnchant(itemInMainHand, player,itemCheck);
            }
        }
    }
    //Item has already been checked if it is charged or imbued when it is called here
    private void toggleEnchant(ItemStack item, Player player, ItemCheck itemCheck) {
        String color = TitanItemInfo.getColorStringLiteral(item);
        player.sendActionBar(powerLevelConversion(item, itemCheck.isActive, color, itemCheck.isCharged));
        enableEffect(player);
    }

    public static String powerLevelConversion(ItemStack item, boolean isActive, String color, boolean isCharged) {
        int itemLevelToGet = (isActive ? 0 : 1);
        String loreToAdd;
        if (isCharged) {
            loreToAdd = TitanItemInfo.CHARGED_LORE_MATRIX.get(color)[itemLevelToGet];
        } else {
            loreToAdd = TitanItemInfo.IMBUED_LORE_MATRIX.get(color)[itemLevelToGet];
        }
        List<String> loreList = TitanItemInfo.getLore(item);
        int index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index, loreToAdd);
        TitanItemInfo.setLore(item, loreList);
        return ChatColor.YELLOW + (isActive ? "Ancient Power deactivated!" : "Ancient Power Activated!");
    }

    public static void handleFullInventory(ItemStack item, Player player) {
        ItemCheck itemCheck = new ItemCheck(
                item.hasItemMeta(),
                TitanItemInfo.isTitanTool(item),
                TitanItemInfo.isAllowedTitanType(item),
                TitanItemInfo.isCharged(item),
                TitanItemInfo.isImbued(item),
                (TitanItemInfo.isActiveCharged(item) || TitanItemInfo.isActiveImbued(item))
        );
        String color = TitanItemInfo.getColorStringLiteral(item);
        powerLevelConversion(item, itemCheck.isActive, color, itemCheck.isCharged);
        player.sendMessage("§CInventory is full - Ancient Power deactivated");
        disableEffect(player);
    }

    public static void disableEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        int index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,TitanItemInfo.CHARGED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void imbue(ItemStack item){
        List<String> loreList = TitanItemInfo.getLore(item);
        int index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        String color = TitanItemInfo.getColorStringLiteral(item);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(CustomModelData.IMBUED_TITAN_TOOL);
        item.setItemMeta(meta);
        loreList.set(index, TitanItemInfo.IMBUED_LORE_MATRIX.get(color)[1]);
        TitanItemInfo.setLore(item, loreList);
    }

    public static void disableImbuedEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        int index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,TitanItemInfo.IMBUED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    private static boolean processPlayerStateValidation(Player player) {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

    private static boolean processItemValidation(ItemCheck itemCheck){
        if (!itemCheck.hasItemMeta) {
            return false;
        }
        if (!itemCheck.isCharged && !itemCheck.isImbued) {
            return false;
        }
        if (!itemCheck.isAllowedTitanType) {
            return false;
        }
        if (!itemCheck.isTitanTool) {
            return false;
        }

        return true;
    }

    private ItemCheck retrieveItemRecord(ItemStack itemStack) {
        return new ItemCheck(
                itemStack.hasItemMeta(),
                TitanItemInfo.isTitanTool(itemStack),
                TitanItemInfo.isAllowedTitanType(itemStack),
                TitanItemInfo.isCharged(itemStack),
                TitanItemInfo.isImbued(itemStack),
                (TitanItemInfo.isActiveCharged(itemStack) || TitanItemInfo.isActiveImbued(itemStack))
        );
    }

    public static void printLog(boolean isImbued, int itemLevel, int itemLevelToGet, String color) {
        Bukkit.getConsoleSender().sendMessage("Item imbued status: " + isImbued);
        Bukkit.getConsoleSender().sendMessage("Item powerLevel: " + itemLevel);
        Bukkit.getConsoleSender().sendMessage("ItemLevel to get: " + itemLevelToGet);
        Bukkit.getConsoleSender().sendMessage("Item Color: " + color);
    }

}
