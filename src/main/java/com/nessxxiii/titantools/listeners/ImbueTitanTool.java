package com.nessxxiii.titantools.listeners;

import com.nessxxiii.titantools.enums.ToolColor;
import com.nessxxiii.titantools.events.ImbueToolSucceedEvent;
import com.nessxxiii.titantools.events.ImbueToolAttemptEvent;
import com.nessxxiii.titantools.items.CustomModelData;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.TitanEnchantEffects;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ImbueTitanTool implements Listener {

    @EventHandler
    public void onImbueTitanTool(ImbueToolAttemptEvent event) {
        Player player = event.getPlayer();

        Response<List<String>> loreListResponse = ItemInfo.getLore(player.getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            return;
        }
        List<String> lore = loreListResponse.value();
        ItemStack item = player.getInventory().getItemInMainHand();
        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            Utils.sendPluginMessage(player, "This is not a titan tool.");
            return;
        }
        if (ItemInfo.isImbuedTitanTool(loreListResponse.value(), isTitanTool)) {
            Utils.sendPluginMessage(player, "That item is already imbued!");
            return;
        }

        boolean isAllowedType = ItemInfo.isAllowedType(item, ItemInfo.ALLOWED_TITAN_TYPES);
        if (!isAllowedType) {
            Utils.sendPluginMessage(player, "This is not an allowed type");
            return;
        }
        boolean hasChargeLore = ItemInfo.hasChargeLore(lore, isTitanTool);
        if (!hasChargeLore) {
            Utils.sendPluginMessage(player, "Cannot imbue an item that doesn't have charge lore.");
            return;
        }
        Response<Integer> chargeAmountResponse = ItemInfo.getCharge(lore, isTitanTool, hasChargeLore, 39);
        if (chargeAmountResponse.error() != null) {
            Utils.sendPluginMessage(player, chargeAmountResponse.error());
            return;
        }
        if (chargeAmountResponse.value() != 0) {
            Utils.sendPluginMessage(player, "You cannot imbue a tool with a charge!");
            return;
        }
        Response<Integer> chargeLoreIndexResponse = ItemInfo.getTitanLoreIndex(lore, ItemInfo.CHARGE_PREFIX, isTitanTool);
        if (chargeLoreIndexResponse.error() != null) {
            Utils.sendPluginMessage(player, chargeLoreIndexResponse.error());
            return;
        }
        Response<Integer> statusLoreIndexResponse = ItemInfo.getTitanLoreIndex(lore, ItemInfo.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            Utils.sendPluginMessage(player, statusLoreIndexResponse.error());
            return;
        }
        Response<ToolColor> toolColorResponse = ItemInfo.getColor(lore);
        if (toolColorResponse.error() != null) {
            Utils.sendPluginMessage(player, toolColorResponse.error());
            return;
        }

        List<String> loreListNoCharge = new ArrayList<>();
        for (int i = 0; i < lore.size(); i++) {
            if (i == chargeLoreIndexResponse.value()) continue;
            loreListNoCharge.add(lore.get(i));
        }

        ItemMeta meta = event.getPlayer().getInventory().getItemInMainHand().getItemMeta();
        meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
        meta.setLore(loreListNoCharge);
        item.setItemMeta(meta);
        TitanEnchantEffects.enableEffect(event.getPlayer());
        Utils.sendPluginMessage(event.getPlayer(), "Successfully imbued titan tool.");
        Bukkit.getPluginManager().callEvent(new ImbueToolSucceedEvent(event.getPlayer(), item));
    }
}
