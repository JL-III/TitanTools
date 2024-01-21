package com.nessxxiii.titantools.listeners.util;

import com.nessxxiii.titantools.events.util.DebugEvent;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Debug implements Listener {

    @EventHandler
    public void onDebug(DebugEvent event) {
        Utils.sendPluginMessage(event.getSender(), ChatColor.LIGHT_PURPLE + "Begin-Debug");
        List<String> lore = event.getLore();
        boolean isTitanTool = ItemInfo.isTitanTool(lore);
        ItemStack itemStack = event.getItemStack();
        Utils.sendPluginMessage(event.getSender(), "isTitanTool: " + isTitanTool);
        Utils.sendPluginMessage(event.getSender(), "Contains charge lore: " + ItemInfo.hasChargeLore(lore, isTitanTool));
        Utils.sendPluginMessage(event.getSender(), "ToolColor: " + ItemInfo.getColor(lore));
        Utils.sendPluginMessage(event.getSender(), "ToolStatus: " + ItemInfo.getStatus(lore, isTitanTool));
        Utils.sendPluginMessage(event.getSender(), "isChargedTitanTool: " + ItemInfo.isChargedTitanTool(lore, isTitanTool));
        Utils.sendPluginMessage(event.getSender(), "chargeLoreIndex: " + ItemInfo.getTitanLoreIndex(lore, ItemInfo.CHARGE_PREFIX, isTitanTool));
        Utils.sendPluginMessage(event.getSender(), "statusLoreIndex: " + ItemInfo.getTitanLoreIndex(lore, ItemInfo.STATUS_PREFIX, isTitanTool));
        Utils.sendPluginMessage(event.getSender(), "Get charge amount: " + ItemInfo.getCharge(lore, isTitanTool, ItemInfo.hasChargeLore(lore, isTitanTool), 39));
        if (itemStack.getItemMeta().hasCustomModelData()) {
            Utils.sendPluginMessage(event.getSender(), "Current custom model data: " + itemStack.getItemMeta().getCustomModelData());
        } else {
            Utils.sendPluginMessage(event.getSender(), "This item does not have custom model data.");
        }
        Utils.sendPluginMessage(event.getSender(), ChatColor.LIGHT_PURPLE + "End-Debug");

    }
}
