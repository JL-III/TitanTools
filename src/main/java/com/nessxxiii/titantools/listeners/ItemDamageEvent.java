package com.nessxxiii.titantools.listeners;

import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Response;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemDamageEvent implements Listener {
    private final Debugger debugger;
    public ItemDamageEvent(Debugger debugger) {
        this.debugger = debugger;
    }

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        ItemStack itemBeingDamaged = event.getItem();
        Response<List<String>> loreListResponse = ItemInfo.getLore(itemBeingDamaged);
        if (loreListResponse.error() != null) {
            debugger.sendDebugIfEnabled("Response from ItemDamageEvent: " + loreListResponse.error());
            return;
        }

        if (ItemInfo.isTitanTool(loreListResponse.value())) {
            event.setCancelled(true);
        }
    }

}
