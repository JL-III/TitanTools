package com.nessxxiii.titanenchants.listeners.enchantmentManagement;

import com.playtheatria.jliii.generalutils.items.PowerCrystalInfo;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;


public class ChargeManagement implements Listener {


    @EventHandler
    public void applyCharge(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (isValidated(player.getItemOnCursor(), event.getCurrentItem())) {
            ItemStack itemOnCursor = player.getItemOnCursor();
            try {
                int chargeAmount = getChargeAmount(itemOnCursor, itemOnCursor.getAmount());
                player.getItemOnCursor().setAmount(0);
                addChargeLore(player, event.getCurrentItem(),chargeAmount);
                event.setCancelled(true);
            } catch (NumberFormatException exception) {
                player.sendMessage("There was an issue finding the charge amount.");
            }

        }
    }

    private int getChargeAmount(ItemStack itemOnCursor, int amount) {

        return switch (PowerCrystalInfo.getPowerCrystalType(itemOnCursor)) {
            case COMMON -> 5 * amount;
            case UNCOMMON -> 50 * amount;
            case SUPER -> 100 * amount;
            case EPIC -> 250 * amount;
            case ULTRA -> 1000 * amount;
            default -> 0;
        };
    }

    private boolean isValidated(ItemStack itemOnCursor, ItemStack itemClicked) {
        //item on cursor must be powercrystal
        if (itemOnCursor.getItemMeta() == null) return false;
        if (!PowerCrystalInfo.isPowerCrystal(itemOnCursor)) return false;
        //item clicked is the titan tool
        if (itemClicked == null || itemClicked.getType() == Material.AIR) return false;
        if (PowerCrystalInfo.isPowerCrystal(itemClicked)) return false;
        //Check if item is a titan tool, an allowed type and is not imbued.

        Response<List<String>> loreListResponse = TitanItem.getLore(itemClicked);
        if (!loreListResponse.isSuccess()) return false;
        List<String> loreList = loreListResponse.value();
        Response<Boolean> isTitanToolResponse = TitanItem.isTitanTool(loreList);
        Response<Boolean> isChargedTitanToolResponse = TitanItem.isChargedTitanTool(loreList, isTitanToolResponse);

        if (!isTitanToolResponse.isSuccess()) return false;
        if (!isTitanToolResponse.value()) return false;
        if (!TitanItem.isAllowedType(itemClicked, TitanItem.ALLOWED_TITAN_TYPES)) return false;
        if (!isChargedTitanToolResponse.isSuccess()) return false;
        return isChargedTitanToolResponse.value();
    }

    public static void addChargeLore(Player player, ItemStack item, Integer amount){
        //TODO disabled
//        List<String> loreList = TitanItemInfo.getLore(item);
//        int index = TitanItemInfo.getChargeLoreIndex(loreList);
//        int chargeIndex = index + 1;
//        ToolColor color = TitanItemInfo.getColor(item);
//        int previousCharge;
//        int finalCharge;
//        if (TitanItemInfo.isChargedTitanTool(item)) {
//            previousCharge = Integer.parseInt(loreList.get(chargeIndex).substring(24));
//            finalCharge = previousCharge + (amount);
//        } else {
//            finalCharge = amount;
//        }
//        if (color != null) {
//            loreList.set(chargeIndex, TitanItemInfo.CHARGE_PREFIX + color + finalCharge);
//            ItemMeta meta = item.getItemMeta();
//            meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
//            item.setItemMeta(meta);
//            TitanItemInfo.setLore(item, loreList);
//            TitanEnchantEffects.addChargeEffect(player);
//        }
    }

    public static void decreaseChargeLore(ItemStack item, Player player, Integer amountTaken){
        //TODO disabled
//        List<String> loreList = item.getItemMeta().getLore();
//        int index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
//        if (TitanItemInfo.isChargedAndActive(item)) {
//            int remainingCharge = Integer.parseInt(loreList.get(index + 1).substring(24)) - amountTaken;
//            String color = TitanItemInfo.getColor(item);
//            ItemMeta meta = item.getItemMeta();
//            if (remainingCharge < 1) {
//                loreList.set(index, TitanItemInfo.ANCIENT_POWER_STRING + color + TitanItemInfo.CHARGED);
//                loreList.set(index + 1, TitanItemInfo.ANCIENT_DEPLETED);
//                meta.setCustomModelData(CustomModelData.UNCHARGED_TITAN_TOOL);
//                depletedChargeEffect(player);
//            } else {
//                loreList.set(index + 1, TitanItemInfo.CHARGE_STRING + color + remainingCharge);
//                player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CPowerLvl: " + ChatColor.GREEN + amountTaken + " "
//                        + ChatColor.ITALIC + "§x§F§F§0§0§4§CCharge: " + ChatColor.YELLOW + (remainingCharge > 1 ? remainingCharge : 0)));
//            }
//            meta.setLore(loreList);
//            item.setItemMeta(meta);
//        }
    }

    public static void printLog(Player player, ItemStack item, int previousCharge, int amount, int finalCharge, String indexString, String chargeIndexString) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + player.getName() + "§x§f§b§0§0§f§1" + " used a PowerCrystal on: " + ChatColor.AQUA + item.getType().name());
        Bukkit.getConsoleSender().sendMessage(item.getItemMeta().hasDisplayName() ? Component.text("Name: ", TextColor.color(5, 250, 83)).append(item.getItemMeta().displayName()) : Component.text("No display name to show", TextColor.color(168, 50, 50)));
        Bukkit.getConsoleSender().sendMessage("§x§f§b§0§0§f§1" + "Prev Charge " + ChatColor.GREEN + previousCharge + "§x§f§b§0§0§f§1" + " PCrystal Charge: " + ChatColor.GREEN + amount);
        Bukkit.getConsoleSender().sendMessage(indexString + " " + chargeIndexString + " " + finalCharge);
    }

}
