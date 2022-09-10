package com.nessxxiii.titanenchants.listeners;

import com.nessxxiii.titanenchants.items.ItemInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDamageEvent implements Listener{

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        ItemStack itemBeingDamaged = event.getItem();

        if (ItemInfo.isTitanTool(itemBeingDamaged) && (ItemInfo.isActiveCharged(itemBeingDamaged) || ItemInfo.isActiveImbued(itemBeingDamaged))) {
            event.setCancelled(true);
        }
    }

}
