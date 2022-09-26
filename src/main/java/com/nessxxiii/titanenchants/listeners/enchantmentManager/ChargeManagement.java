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
        Material cooldown = Material.COD_SPAWN_EGG;
        if (player.hasCooldown(cooldown)) {
            player.sendActionBar(Component.text(ChatColor.DARK_RED + "You must wait " + ChatColor.YELLOW + (player.getCooldown(cooldown) / 20) + ChatColor.DARK_RED + " seconds before attempting to use a powercrystal."));
            return;
        }
        player.setCooldown(cooldown, 100);
        addChargeLore(itemClicked,numberOfCharge * 100);
        player.getItemOnCursor().setAmount(0);
        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,player.getEyeLocation(),100);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE,10, 1);
        event.setCancelled(true);
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "*-----------------------------------------------------------------------*");
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_GREEN + player.getName() + ChatColor.AQUA + " tried to add a power crystal.");
        Bukkit.getConsoleSender().sendMessage(ChatColor.AQUA + "Completed attempt to charge Titan Tool.");
        Bukkit.getConsoleSender().sendMessage(itemClicked.displayName());
        for (Component component : Objects.requireNonNull(itemClicked.getItemMeta().lore())) {
            Bukkit.getConsoleSender().sendMessage(component);
        }
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "*-----------------------------------------------------------------------*");
    }

    public static void addChargeLore(ItemStack item, Integer amount){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        int chargeIndex = index + 1;

        if (ItemInfo.hasCharge(item)) {
            String string = loreList.get(chargeIndex);
            String string1 = string.substring(24);
            int previousCharge = Integer.parseInt(string1);
            int newCharge = previousCharge + (amount);
            loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE + " " + newCharge);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(loreList);
            item.setItemMeta(meta);
            return;
        } else
        loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE + " " + (amount));
        loreList.set(index,ItemInfo.CHARGED_ONE);
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
