package com.nessxxiii.titantools.listeners.utils;

import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Response;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;

public class ItemDamageEvent implements Listener {
    private final Debugger debugger;
    public ItemDamageEvent(Debugger debugger) {
        this.debugger = debugger;
    }

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        if (event.isCancelled()) return;
        ItemStack itemBeingDamaged = event.getItem();
        Response<List<String>> loreListResponse = ItemInfo.getLore(itemBeingDamaged);
        if (loreListResponse.error() != null) {
            debugger.sendDebugIfEnabled("Response from ItemDamageEvent: " + loreListResponse.error());
            return;
        }

        if (ItemInfo.isTitanTool(loreListResponse.value())) {
            event.setCancelled(true);
            return;
        }

        if (event.getPlayer().hasPermission("titan.enchants.autorepair")) {
            Damageable damageable = (Damageable) event.getItem().getItemMeta();
            if (itemBeingDamaged.getType().getMaxDurability() - damageable.getDamage() < itemBeingDamaged.getType().getMaxDurability() / 2) {
                Utils.sendPluginMessage(event.getPlayer(), "Item is damaged, repairing...");
                event.setCancelled(true);
                damageable.setDamage(0);
                itemBeingDamaged.setItemMeta(damageable);
            }
        }
    }
}
