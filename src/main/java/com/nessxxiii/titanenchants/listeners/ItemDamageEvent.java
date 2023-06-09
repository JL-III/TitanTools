package com.nessxxiii.titanenchants.listeners;

import com.playtheatria.jliii.generalutils.items.TitanItemInfo;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

public class ItemDamageEvent implements Listener{

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        ItemStack itemBeingDamaged = event.getItem();

        if (TitanItemInfo.isTitanTool(itemBeingDamaged) && (TitanItemInfo.isActiveCharged(itemBeingDamaged) || TitanItemInfo.isActiveImbued(itemBeingDamaged))) {
            event.setCancelled(true);
        }
    }

}
