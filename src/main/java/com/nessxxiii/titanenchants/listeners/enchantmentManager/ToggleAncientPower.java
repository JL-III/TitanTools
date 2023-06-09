package com.nessxxiii.titanenchants.listeners.enchantmentManager;

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
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.disableEffect;
import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.enableEffect;

public class ToggleAncientPower implements Listener {

    private Plugin plugin;
    private static Player player;

    public ToggleAncientPower(Plugin plugin) {
        this.plugin = plugin;
    };

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        player = event.getPlayer();

        if (processPlayerStateValidation()) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;
            if (processItemValidation(itemInMainHand)) {
                if (TitanItemInfo.isCharged(itemInMainHand)) {
                    player.setCooldown(coolDown,25);
                    event.setCancelled(true);
                    ToggleAncientPower.toggleEnchant(itemInMainHand, player,false);
                } else if (TitanItemInfo.isImbued(itemInMainHand)) {
                    player.setCooldown(coolDown,25);
                    ToggleAncientPower.toggleEnchant(itemInMainHand, player, true);
                }
            }
        }
    }

    public static void toggleEnchant(ItemStack item, Player player, boolean isImbued) {

        int itemLevel = TitanItemInfo.getItemLevel(item);
        String color = TitanItemInfo.getColorStringLiteral(item);

        switch (itemLevel) {
            case 1, 2 -> {
                powerLevelConversion(item, itemLevel, color, isImbued, true);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 1));
                enableEffect(player);
            }
            case 3 -> {
                powerLevelConversion(item, itemLevel, color, isImbued, true);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power deactivated");
                disableEffect(player);
            }
            default -> {
                if (TitanItemInfo.isDormantCharged(item)) {
                    powerLevelConversion(item, itemLevel, color, isImbued, true);
                    player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 1));
                    enableEffect(player);
                } else {
                    player.sendActionBar("An error has occurred");
                }
            }
        }
    }

    public static void handleFullInventory(ItemStack item, Player player, boolean isImbued, int currentLevel) {
        String color = TitanItemInfo.getColorStringLiteral(item);

        switch (currentLevel) {
            case 3,2 -> {
                powerLevelConversion(item, currentLevel, color, isImbued, false);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power set to PowerLvl: " + (currentLevel - 1));
                disableEffect(player);
            }
            case 1 -> {
                powerLevelConversion(item, currentLevel, color, isImbued, false);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power deactivated");
                disableEffect(player);
            }
            default -> {
            }
        }
    }

    public static void powerLevelConversion(ItemStack item, int itemLevel, String color, boolean isImbued, boolean increment) {
        int itemLevelToGet;
        String loreToAdd;
        if (increment) {
            itemLevelToGet = itemLevel <= 2 ? itemLevel + 1 : 0;
        } else {
            itemLevelToGet = itemLevel >= 1 ? itemLevel - 1 : 0;
        }
        if (isImbued) {
            loreToAdd = TitanItemInfo.IMBUED_LORE_MATRIX.get(color)[itemLevelToGet];
        } else {
            loreToAdd = TitanItemInfo.CHARGED_LORE_MATRIX.get(color)[itemLevelToGet];
        }
//        printLog(isImbued, itemLevel, itemLevelToGet, color);
        List<String> loreList = TitanItemInfo.getLore(item);
        Integer index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index, loreToAdd);
        TitanItemInfo.setLore(item, loreList);
    }


    public static void disableEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,TitanItemInfo.CHARGED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void imbue(ItemStack item){
        List<String> loreList = TitanItemInfo.getLore(item);
        Integer index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        String color = TitanItemInfo.getColorStringLiteral(item);
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(CustomModelData.IMBUED_TITAN_TOOL);
        item.setItemMeta(meta);
        loreList.set(index, TitanItemInfo.IMBUED_LORE_MATRIX.get(color)[1]);
        TitanItemInfo.setLore(item, loreList);
    }

    public static void disableImbuedEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,TitanItemInfo.IMBUED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    private static boolean processPlayerStateValidation() {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

    //TODO items are checked twice here, is there a way to reduce the checks?
    //TODO perhaps there is a method to get all info on a pick immediately and store in a temporary object to reference throughout the methods
    private static boolean processItemValidation(ItemStack item){
        if (!TitanItemInfo.isCharged(item) && !TitanItemInfo.isImbued(item)) {
//            Bukkit.getConsoleSender().sendMessage("item was not charged and not imbued");
            return false;
        }
        if (!TitanItemInfo.isAllowedTitanType(item)) {
//            Bukkit.getConsoleSender().sendMessage("Item is not allowed titan type");
            return false;
        }
        if (!item.hasItemMeta()) {
//            Bukkit.getConsoleSender().sendMessage("Item does not have item meta");
            return false;
        }
        if (!TitanItemInfo.isTitanTool(item)) {
//            Bukkit.getConsoleSender().sendMessage("Item is not a titan tool");
            return false;
        }
        return true;
    }

    public static void printLog(boolean isImbued, int itemLevel, int itemLevelToGet, String color) {
        Bukkit.getConsoleSender().sendMessage("Item imbued status: " + isImbued);
        Bukkit.getConsoleSender().sendMessage("Item powerLevel: " + itemLevel);
        Bukkit.getConsoleSender().sendMessage("ItemLevel to get: " + itemLevelToGet);
        Bukkit.getConsoleSender().sendMessage("Item Color: " + color);
    }

}
