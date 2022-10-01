package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.ItemInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Objects;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.depletedChargeEffect;


public class ChargeManagement implements Listener {


    @EventHandler
    public static void applyCharge(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        if (event.getCurrentItem() == null) return;

        ItemStack itemOnCursor = player.getItemOnCursor();
        ItemStack itemClicked = event.getCurrentItem();
        if (itemOnCursor.getItemMeta() == null) return;
        int numberOfCharge = itemOnCursor.getAmount();
        if (itemClicked.getType() == Material.AIR) return;
        if (!ItemInfo.isPowerCrystal(itemOnCursor)) return;
        if (ItemInfo.isPowerCrystal(itemClicked)) return;

        if (!ItemInfo.isTitanTool(itemClicked)) {
            return;
        }
        if (!ItemInfo.isAllowedTitanType(itemClicked)) {
            return;
        }
        if (ItemInfo.isImbued(itemClicked)) {
            return;
        }
        addChargeLore(itemClicked,numberOfCharge * 100);
        player.getItemOnCursor().setAmount(0);
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,player.getEyeLocation(),100);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE,10, 1);
        event.setCancelled(true);
    }

    public static void addChargeLore(ItemStack item, Integer amount){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        int chargeIndex = index + 1;
        String itemColor = ItemInfo.getColor(item);
        int finalCharge;
        if (ItemInfo.hasCharge(item)) {
            String string = loreList.get(chargeIndex);
            String string1 = string.substring(24);
            int previousCharge = Integer.parseInt(string1);
            finalCharge = previousCharge + (amount);
            Bukkit.getConsoleSender().sendMessage("1 Final Charge amount: " + finalCharge);
        } else {
            finalCharge = amount;
            Bukkit.getConsoleSender().sendMessage("2 Final Charge amount: " + finalCharge);
        }
        if (itemColor != null) {
            switch (itemColor)
            {
                case "RED" -> {
                    loreList.set(index,ItemInfo.CHARGED_RED_ONE_COMPOSITE);
                    loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE_RED + finalCharge);
                    Bukkit.getConsoleSender().sendMessage("3 Final Charge amount: " + finalCharge);
                }
                case "YELLOW" -> {
                    loreList.set(index,ItemInfo.CHARGED_YELLOW_ONE_COMPOSITE);
                    loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE_YELLOW + finalCharge);
                    Bukkit.getConsoleSender().sendMessage("3 Final Charge amount: " + finalCharge);
                }
                case "BLUE" -> {
                    loreList.set(index,ItemInfo.CHARGED_BLUE_ONE_COMPOSITE);
                    loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE_BLUE + finalCharge);
                    Bukkit.getConsoleSender().sendMessage("3 Final Charge amount: " + finalCharge);
                }
                default -> {}
            }
        }
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void decreaseChargeLore(ItemStack item, Player player, Integer amountTaken){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        Integer chargeIndex = index + 1;
        if (ItemInfo.hasCharge(item)) {
            String string = loreList.get(chargeIndex);
            String string1 = string.substring(24);
            int previousCharge = Integer.parseInt(string1);
            int remainingCharge = previousCharge - amountTaken;
            if (remainingCharge < 1 && ItemInfo.getColor(item).equals("RED")) {
                loreList.set(index,ItemInfo.ANCIENT_RED);
                loreList.set(chargeIndex,ItemInfo.ANCIENT_DEPLETED);
                depletedChargeEffect(player);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(loreList);
                item.setItemMeta(meta);
            } else if (remainingCharge < 1 && ItemInfo.getColor(item).equals("YELLOW")) {
                loreList.set(index, ItemInfo.ANCIENT_YELLOW);
                loreList.set(chargeIndex,ItemInfo.ANCIENT_DEPLETED);
                depletedChargeEffect(player);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(loreList);
                item.setItemMeta(meta);
            } else if (remainingCharge < 1 && ItemInfo.getColor(item).equals("BLUE")) {
                loreList.set(index, ItemInfo.ANCIENT_BLUE);
                loreList.set(chargeIndex, ItemInfo.ANCIENT_DEPLETED);
                depletedChargeEffect(player);
                ItemMeta meta = item.getItemMeta();
                meta.setLore(loreList);
                item.setItemMeta(meta);
            } else
                loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE + " " + remainingCharge);
            player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CPowerLvl: " + ChatColor.GREEN + amountTaken + " "
                    + ChatColor.ITALIC + "§x§F§F§0§0§4§CCharge: " + ChatColor.YELLOW + (remainingCharge >= 1 ? remainingCharge : 0)));
            ItemMeta meta = item.getItemMeta();
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
    }
}
