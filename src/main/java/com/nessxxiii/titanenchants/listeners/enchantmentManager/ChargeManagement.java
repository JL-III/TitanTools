package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.ItemInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.depletedChargeEffect;


public class ChargeManagement implements Listener {


    @EventHandler
    public static void applyCharge(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();

        if (isValidated(player.getItemOnCursor(), event.getCurrentItem())) {
            ItemStack itemOnCursor = player.getItemOnCursor();
            int chargeAmount = getChargeAmount(itemOnCursor, itemOnCursor.getAmount());
            player.getItemOnCursor().setAmount(0);
            addChargeLore(event.getCurrentItem(),chargeAmount);
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,player.getEyeLocation(),100);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE,10, 1);
            event.setCancelled(true);
        }
    }

    private static int getChargeAmount(ItemStack itemOnCursor, int amount) {

        return switch (ItemInfo.getPowerCrystalType(itemOnCursor)) {
            case COMMON -> 5 * amount;
            case UNCOMMON -> 50 * amount;
            case SUPER -> 100 * amount;
            case EPIC -> 250 * amount;
            case ULTRA -> 1000 * amount;
            default -> 0;
        };
    }

    private static boolean isValidated(ItemStack itemOnCursor, ItemStack itemClicked) {
        //item on cursor must be powercrystal
        if (itemOnCursor.getItemMeta() == null) return false;
        if (!ItemInfo.isPowerCrystal(itemOnCursor)) return false;

        //item clicked is the titan tool
        if (itemClicked.getType() == Material.AIR) return false;
        if (ItemInfo.isPowerCrystal(itemClicked)) return false;
        if (!ItemInfo.isTitanTool(itemClicked)) return false;
        if (!ItemInfo.isAllowedTitanType(itemClicked)) return false;
        if (ItemInfo.isImbued(itemClicked)) return false;


        return true;
    }

    public static void addChargeLore(ItemStack item, Integer amount){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        int chargeIndex = index + 1;
        String itemColor = ItemInfo.getColor(item);
        int finalCharge;
        if (ItemInfo.isChargedAndActive(item)) {
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
                    loreList.set(index,ItemInfo.CHARGED_RED_ONE);
                    loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE_RED + " " + finalCharge);
                    Bukkit.getConsoleSender().sendMessage("3 RED Final Charge amount: " + finalCharge);
                }
                case "YELLOW" -> {
                    loreList.set(index,ItemInfo.CHARGED_YELLOW_ONE);
                    loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE_YELLOW + " " + finalCharge);
                    Bukkit.getConsoleSender().sendMessage("3 YELLOW Final Charge amount: " + finalCharge);
                }
                case "BLUE" -> {
                    loreList.set(index,ItemInfo.CHARGED_BLUE_ONE);
                    loreList.set(chargeIndex,ItemInfo.ANCIENT_CHARGE_BLUE + " " + finalCharge);
                    Bukkit.getConsoleSender().sendMessage("3 BLUE Final Charge amount: " + finalCharge);
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
        if (ItemInfo.isChargedAndActive(item)) {
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
