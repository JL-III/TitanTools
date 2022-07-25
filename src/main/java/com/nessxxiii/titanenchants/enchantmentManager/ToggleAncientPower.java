package com.nessxxiii.titanenchants.enchantmentManager;

import com.nessxxiii.titanenchants.Items.ItemInfo;
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

    public ToggleAncientPower(Plugin plugin) {
        this.plugin = plugin;
    };

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        event.getPlayer().sendMessage("Pass isrightclick sending to validateToggleAttempt ");
        ValidateToggleAttempt validateToggleAttempt = new ValidateToggleAttempt(event);
        if (validateToggleAttempt.processValidation()) {
            Player player = event.getPlayer();
            Material coolDown = Material.JIGSAW;
            player.sendMessage("Passed validate toggle attempt");
            if (validateToggleAttempt.processItemValidation(player.getInventory().getItemInMainHand())
                    && ItemInfo.isCharged (player.getInventory().getItemInMainHand())) {
                player.setCooldown(coolDown,25);
                player.sendMessage("Your item is charged with ancient power");
                event.setCancelled(true);
                ToggleAncientPower.toggleChargedEnchant(player.getInventory().getItemInMainHand(),player);
            } else if (validateToggleAttempt.processItemValidation(player.getInventory().getItemInMainHand())
                    && ItemInfo.isImbued(player.getInventory().getItemInMainHand())) {
                player.setCooldown(coolDown,25);
                player.sendMessage("Your item is imbued with ancient power");
                ToggleAncientPower.toggleImbuedEnchantment(player.getInventory().getItemInMainHand(), player);
            } else {
                player.sendMessage("You cannot toggle this item since it is not imbued or charged");
            }
        }
    }

    public static void toggleChargedEnchant(ItemStack item, Player player) {
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

    public static void imbue(ItemStack item){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index, ItemInfo.IMBUED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void toggleImbuedEnchantment(ItemStack item, Player player){
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return;
        if (ItemInfo.getItemLevel(item) == 1) {
            imbuedEnchant1ToEnchant2(item);
            new TitanEnchantEffects().disableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Pick set to Enchant2");
        } else if (ItemInfo.getItemLevel(item) == 2) {
            imbuedEnchant2ToEnchant3(item);
            new TitanEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Pick set to Enchant3");
        } else if (ItemInfo.getItemLevel(item) == 3) {
            disableImbuedEnchant(item);
            new TitanEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Pick set to dormant");
        } else if (ItemInfo.isDormantCharged(item)) {
            enableImbuedEnchantTo1(item);
            new TitanEnchantEffects().enableEffect(player);
            player.sendMessage(ChatColor.GREEN + "Pick set to Enchant1");
        }
    }

    public static void enableImbuedEnchantTo1(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void imbuedEnchant1ToEnchant2(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_TWO);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }
    public static void imbuedEnchant2ToEnchant3(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_THREE);
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

}
