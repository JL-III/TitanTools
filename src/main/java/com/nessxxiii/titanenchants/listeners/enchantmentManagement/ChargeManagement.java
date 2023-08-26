package com.nessxxiii.titanenchants.listeners.enchantmentManagement;

import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import com.playtheatria.jliii.generalutils.enums.ToolColor;
import com.playtheatria.jliii.generalutils.items.CustomModelData;
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
import org.bukkit.inventory.meta.ItemMeta;

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
                addChargeLore(player, event.getCurrentItem(), chargeAmount);
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

        Response<List<String>> loreListResponse = TitanItem.getLore(itemClicked);
        if (loreListResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            return false;
        }

        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        if (!isTitanTool) return false;

        Response<Boolean> isChargedTitanToolResponse = TitanItem.isChargedTitanTool(loreListResponse.value(), isTitanTool);
        if (isChargedTitanToolResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(isChargedTitanToolResponse.error());
            return false;
        }

        if (!TitanItem.isAllowedType(itemClicked, TitanItem.ALLOWED_TITAN_TYPES)) return false;
        return isChargedTitanToolResponse.value();
    }

    public static void addChargeLore(Player player, ItemStack item, Integer amount){
        Response<List<String>> loreListResponse = TitanItem.getLore(item);
        if (loreListResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            return;
        }
        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            Bukkit.getConsoleSender().sendMessage("Failed to add charge to a non Titan Tool.");
            return;
        }

        Response<Boolean> hasChargeResponse = TitanItem.hasCharge(loreListResponse.value(), isTitanTool);
        if (hasChargeResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(hasChargeResponse.error());
            return;
        }

        Response<Integer> indexResponse = TitanItem.getTitanLoreIndex(loreListResponse.value(), TitanItem.CHARGE_PREFIX, isTitanTool);
        if (indexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(indexResponse.error());
            return;
        }

        int finalCharge;
        if (hasChargeResponse.value()) {
            Response<Integer> previousChargeResponse = TitanItem.getCharge(loreListResponse.value(), isTitanTool, hasChargeResponse, 0);
            if (previousChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(previousChargeResponse.error());
                return;
            }
            finalCharge = previousChargeResponse.value() + (amount);
        } else {
            finalCharge = amount;
        }
        Response<ToolColor> colorResponse = TitanItem.getColor(loreListResponse.value());
        if (colorResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(colorResponse.error());
            return;
        }
        List<String> updatedLoreList = loreListResponse.value();
        updatedLoreList.set(indexResponse.value(), TitanItem.generateChargeLore(colorResponse.value(), finalCharge));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
        item.setItemMeta(meta);
        TitanItem.setLore(item, updatedLoreList);
        TitanEnchantEffects.addChargeEffect(player);
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
