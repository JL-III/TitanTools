package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
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

public class ToggleAncientPower implements Listener {

    private Plugin plugin;
    private static Player player;

    public ToggleAncientPower(Plugin plugin) {
        this.plugin = plugin;
    };

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        event.getPlayer().sendMessage("Pass isrightclick sending to validateToggleAttempt ");
        player = event.getPlayer();

        if (processPlayerStateValidation()) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;
            player.sendMessage("Passed validate toggle attempt");

            if (processItemValidation(itemInMainHand)
                    && ItemInfo.isCharged (itemInMainHand)) {
                player.setCooldown(coolDown,25);
                player.sendMessage("Your item is charged with ancient power");
                event.setCancelled(true);
                ToggleAncientPower.toggleEnchant(itemInMainHand,player,false);
            } else if (processItemValidation(itemInMainHand)
                    && ItemInfo.isImbued(itemInMainHand)) {
                player.setCooldown(coolDown,25);
                player.sendMessage("Your item is imbued with ancient power");
                ToggleAncientPower.toggleEnchant(itemInMainHand, player, true);
            } else {
                player.sendMessage("You cannot toggle this item since it is not imbued or charged");
            }
        }
    }

    public static void toggleEnchant(ItemStack item, Player player, boolean isImbued) {
        String caseOneString = ItemInfo.IMBUED_TWO;
        String caseTwoString = ItemInfo.IMBUED_THREE;
        String caseThreeString = ItemInfo.IMBUED_INACTIVE;
        String caseDormantString = ItemInfo.IMBUED_ONE;
        int itemLevel = ItemInfo.getItemLevel(item);

        if (!isImbued) {
            caseOneString = ItemInfo.CHARGED_TWO;
            caseTwoString = ItemInfo.CHARGED_THREE;
            caseThreeString = ItemInfo.CHARGED_INACTIVE;
            caseDormantString = ItemInfo.CHARGED_ONE;
        }
        switch (itemLevel) {
            case 1 -> {
                powerLevelConversion(item, caseOneString);
                player.sendMessage(ChatColor.GREEN + "Titan Tool set to Enchant2");
            }
            case 2 -> {
                powerLevelConversion(item, caseTwoString);
                player.sendMessage(ChatColor.GREEN + "Titan Tool set to Enchant3");
            }
            case 3 -> {
                powerLevelConversion(item, caseThreeString);
                player.sendMessage(ChatColor.GREEN + "Titan Tool set to dormant");
            }
            default -> {
                if (ItemInfo.isDormantCharged(item)) {
                    powerLevelConversion(item, caseDormantString);
                    player.sendMessage(ChatColor.GREEN + "Titan Tool set to Enchant1");
                } else {
                    player.sendMessage("An error has occurred");
                }
            }
        }
        new TitanEnchantEffects().enableEffect(player);
    }

    public static void disableEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void imbue(ItemStack item){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index, ItemInfo.IMBUED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void disableImbuedEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void powerLevelConversion(ItemStack item, String loreChange) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,loreChange);
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

    private static boolean processItemValidation(ItemStack item){
        if (!ItemInfo.hasCharge(item) && !ItemInfo.isImbued(item)) return false;
        if (!ItemInfo.isAllowedTitanType(item)) return false;
        if (!item.hasItemMeta()) return false;
        return ItemInfo.isTitanTool(item);
    }
}
