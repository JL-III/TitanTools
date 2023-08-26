package com.nessxxiii.titanenchants.listeners;

import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ItemDamageEvent implements Listener{

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        ItemStack itemBeingDamaged = event.getItem();
        Response<List<String>> loreListResponse = TitanItem.getLore(itemBeingDamaged);
        if (loreListResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            return;
        }

        if (TitanItem.isTitanTool(loreListResponse.value())) {
            event.setCancelled(true);
        }
    }

}
