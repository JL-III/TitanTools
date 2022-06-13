package com.nessxxiii.titanenchants.util;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ToggleChargedItem {

    public static void toggleChargedEnchant(ItemStack item, Player player){
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return;
        if (ItemInfo.getItemLevel(item) == 1) {
            enchant1ToEnchant2(item);
            new TitanEnchantEffects().disableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Titan Tool set to Enchant2");
        } else if (ItemInfo.getItemLevel(item) == 2) {
            enchant2ToEnchant3(item);
            new TitanEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Titan Tool set to Enchant3");
        } else if (ItemInfo.getItemLevel(item) == 3) {
            disableEnchant(item);
            new TitanEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Titan Tool set to dormant");
        } else if (ItemInfo.isDormantCharged(item)) {
            enableEnchantTo1(item);
            new TitanEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Titan Tool set to Enchant1");
        }

    }

    public static void enableEnchantTo1(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void enchant1ToEnchant2(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_TWO);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void enchant2ToEnchant3(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_THREE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void disableEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
}
