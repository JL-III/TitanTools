package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.CustomModelData;
import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import com.playtheatria.jliii.generalutils.items.PowerCrystalInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.*;


public class ChargeManagement implements Listener {


    @EventHandler
    public static void applyCharge(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (isValidated(player.getItemOnCursor(), event.getCurrentItem())) {
            ItemStack itemOnCursor = player.getItemOnCursor();
            int chargeAmount = getChargeAmount(itemOnCursor, itemOnCursor.getAmount());
            player.getItemOnCursor().setAmount(0);
            addChargeLore(player, event.getCurrentItem(),chargeAmount);
            event.setCancelled(true);
        }
    }

    private static int getChargeAmount(ItemStack itemOnCursor, int amount) {

        return switch (PowerCrystalInfo.getPowerCrystalType(itemOnCursor)) {
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
        if (!PowerCrystalInfo.isPowerCrystal(itemOnCursor)) return false;
//        Bukkit.getConsoleSender().sendMessage("Checking is validated");
        //item clicked is the titan tool
        if (itemClicked.getType() == Material.AIR || itemClicked.getType() == null) return false;
        if (PowerCrystalInfo.isPowerCrystal(itemClicked)) return false;
        if (!ItemInfo.isTitanTool(itemClicked)) return false;
        if (!ItemInfo.isAllowedTitanType(itemClicked)) return false;
        if (ItemInfo.isImbued(itemClicked)) return false;
//        Bukkit.getConsoleSender().sendMessage("Returned true");
        return true;
    }

    public static void addChargeLore(Player player, ItemStack item, Integer amount){
        List<String> loreList = item.getItemMeta().getLore();
        int index = ItemInfo.getAncientPowerLoreIndex(loreList);
        int chargeIndex = index + 1;
        String color = ItemInfo.getColor(item);
        int previousCharge;
        int finalCharge;
        if (ItemInfo.isCharged(item)) {
            previousCharge = Integer.parseInt(loreList.get(chargeIndex).substring(24));
            finalCharge = previousCharge + (amount);
        } else {
            finalCharge = amount;
        }
        if (color != null) {
            loreList.set(index, ItemInfo.ANCIENT_POWER_STRING + color + ItemInfo.CHARGED_ONE);
            loreList.set(chargeIndex, ItemInfo.CHARGE_STRING + color + finalCharge);
            ItemMeta meta = item.getItemMeta();
            meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
            item.setItemMeta(meta);
            ItemInfo.setLore(item, loreList);
            TitanEnchantEffects.addChargeEffect(player);
        }
    }

    public static void decreaseChargeLore(ItemStack item, Player player, Integer amountTaken){
        List<String> loreList = item.getItemMeta().getLore();
        int index = ItemInfo.getAncientPowerLoreIndex(loreList);
        if (ItemInfo.isChargedAndActive(item)) {
            int remainingCharge = Integer.parseInt(loreList.get(index + 1).substring(24)) - amountTaken;
            String color = ItemInfo.getColor(item);
            ItemMeta meta = item.getItemMeta();
            if (remainingCharge < 1) {
                loreList.set(index, ItemInfo.ANCIENT_POWER_STRING + color + ItemInfo.CHARGED);
                loreList.set(index + 1, ItemInfo.ANCIENT_DEPLETED);
                meta.setCustomModelData(CustomModelData.UNCHARGED_TITAN_TOOL);
                depletedChargeEffect(player);
            } else {
                loreList.set(index + 1, ItemInfo.CHARGE_STRING + color + remainingCharge);
                player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CPowerLvl: " + ChatColor.GREEN + amountTaken + " "
                        + ChatColor.ITALIC + "§x§F§F§0§0§4§CCharge: " + ChatColor.YELLOW + (remainingCharge > 1 ? remainingCharge : 0)));
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
    }

    public static void printLog(Player player, ItemStack item, int previousCharge, int amount, int finalCharge, String indexString, String chargeIndexString) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + player.getName() + "§x§f§b§0§0§f§1" + " used a PowerCrystal on: " + ChatColor.AQUA + item.getType().name());
        Bukkit.getConsoleSender().sendMessage(item.getItemMeta().hasDisplayName() ? Component.text("Name: ", TextColor.color(5, 250, 83)).append(item.getItemMeta().displayName()) : Component.text("No display name to show", TextColor.color(168, 50, 50)));
        Bukkit.getConsoleSender().sendMessage("§x§f§b§0§0§f§1" + "Prev Charge " + ChatColor.GREEN + previousCharge + "§x§f§b§0§0§f§1" + " PCrystal Charge: " + ChatColor.GREEN + amount);
        Bukkit.getConsoleSender().sendMessage(indexString + " " + chargeIndexString + " " + finalCharge);
    }

}
