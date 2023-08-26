package com.nessxxiii.titanenchants.listeners.enchantmentManagement;

import com.nessxxiii.titanenchants.util.Utils;
import com.playtheatria.jliii.generalutils.enums.ToolColor;
import com.playtheatria.jliii.generalutils.enums.ToolStatus;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.disableEffect;
import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.enableEffect;

public class ToggleAncientPower implements Listener {

    @EventHandler
    public void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        if (processPlayerStateValidation(event.getPlayer())) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;

            Response<List<String>> loreListResponse = TitanItem.getLore(itemInMainHand);
            if (loreListResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
                return;
            }

            boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
            if (!isTitanTool) return;

            Response<ToolStatus> toolStatusResponse = TitanItem.getStatus(loreListResponse.value(), isTitanTool);
            if (toolStatusResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
                return;
            }

            Response<Boolean> hasChargeResponse = TitanItem.hasCharge(loreListResponse.value(), isTitanTool);
            if (hasChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(hasChargeResponse.error());
                return;
            }

            player.setCooldown(coolDown,25);
            event.setCancelled(true);
            toggleEnchant(itemInMainHand, player, loreListResponse.value(), isTitanTool, toolStatusResponse, hasChargeResponse);
        }
    }
    //Item has already been checked if it is charged or imbued when it is called here
    private void toggleEnchant(ItemStack item, Player player, List<String> loreList, boolean isTitanTool, Response<ToolStatus> toolStatusResponse, Response<Boolean> hasChargeResponse) {
        Response<ToolColor> toolColorResponse = TitanItem.getColor(loreList);
        if (toolColorResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(toolColorResponse.error());
            return;
        }
        player.sendActionBar(powerLevelConversion(item, loreList, isTitanTool, toolColorResponse.value(), toolStatusResponse.value(), hasChargeResponse));
        enableEffect(player);
    }

    public static String powerLevelConversion(ItemStack item, List<String> loreList, boolean isTitanTool, ToolColor color, ToolStatus status, Response<Boolean> hasChargeResponse) {
        String statusLore = TitanItem.generateStatusLore(color, status == ToolStatus.ON ? ToolStatus.OFF : ToolStatus.ON);
        Response<Integer> statusLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(statusLoreIndexResponse.error());
            return statusLoreIndexResponse.error();
        }

        if (hasChargeResponse.value()) {
            Response<Integer> getChargeResponse = TitanItem.getCharge(loreList, isTitanTool, hasChargeResponse, 39);
            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return getChargeResponse.error();
            }
            String chargeLore = TitanItem.generateChargeLore(color, getChargeResponse.value());

            Response<Integer> chargeLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.CHARGE_PREFIX, isTitanTool);
            if (chargeLoreIndexResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(chargeLoreIndexResponse.error());
                return chargeLoreIndexResponse.error();
            }
            loreList.set(chargeLoreIndexResponse.value(), chargeLore);
            loreList.set(statusLoreIndexResponse.value(), statusLore);
        } else {
            loreList.set(statusLoreIndexResponse.value(), statusLore);
        }
        TitanItem.setLore(item, loreList);
        return ChatColor.YELLOW + (status == ToolStatus.ON ? "Ancient Power deactivated!" : "Ancient Power activated!");
    }

    public static void handleFullInventory(ItemStack item, Player player) {
//        Utils.ItemRecord itemRecord = new Utils.ItemRecord(
//                item.hasItemMeta(),
//                TitanItem.isTitanTool(item),
//                TitanItem.isAllowedTitanType(item),
//                TitanItem.isChargedTitanTool(item),
//                TitanItem.isImbuedTitanTool(item),
//                (TitanItem.getStatus(item) == ToolStatus.ON)
//        );
//        ToolColor color = TitanItem.getColor(item);
//        // TODO seems really weird to be passing in the entire item and then properties about it here.
//        powerLevelConversion(item, itemRecord.isActive(), color, TitanItem.getStatus(item), itemRecord.isCharged());
//        player.sendMessage("§CInventory is full - Ancient Power deactivated");
//        disableEffect(player);
    }
//    public static void imbue(ItemStack item){
//        List<String> loreList = TitanItemInfo.getLore(item);
//        int index = TitanItemInfo.getAncientPowerLoreIndex(loreList);
//        String color = TitanItemInfo.getColorStringLiteral(item);
//        ItemMeta meta = item.getItemMeta();
//        meta.setCustomModelData(CustomModelData.IMBUED_TITAN_TOOL);
//        item.setItemMeta(meta);
//        loreList.set(index, TitanItemInfo.IMBUED_LORE_MATRIX.get(color)[1]);
//        TitanItemInfo.setLore(item, loreList);
//    }

    private static boolean processPlayerStateValidation(Player player) {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

    private static boolean processItemValidation(Utils.ItemRecord itemRecord){
        if (!itemRecord.hasItemMeta()) {
            return false;
        }
        if (!itemRecord.isCharged() && !itemRecord.isImbued()) {
            return false;
        }
        if (!itemRecord.isAllowedTitanType()) {
            return false;
        }
        if (!itemRecord.isTitanTool()) {
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
