package com.nessxxiii.titantools.listeners;

import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import java.util.List;

public class ItemDamageEvent implements Listener {
    @EventHandler
    public void onEntityDamageEvent(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Item item)) return;
        ItemStack itemBeingDamaged = item.getItemStack();
        Response<List<String>> loreListResponse = ItemInfo.getLore(itemBeingDamaged);
        if (loreListResponse.error() != null) {
            return;
        }

        if (ItemInfo.isTitanTool(loreListResponse.value())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerItemDamageEvent(PlayerItemDamageEvent event) {
        if (event.isCancelled()) return;
        ItemStack itemBeingDamaged = event.getItem();
        Response<List<String>> loreListResponse = ItemInfo.getLore(itemBeingDamaged);
        if (loreListResponse.error() != null) {
            return;
        }

        if (ItemInfo.isTitanTool(loreListResponse.value())) {
            event.setCancelled(true);
            return;
        }

        if (event.getPlayer().hasPermission("titan.enchants.autorepair")) {
            Damageable damageable = (Damageable) event.getItem().getItemMeta();
            if (itemBeingDamaged.getType().getMaxDurability() - damageable.getDamage() < itemBeingDamaged.getType().getMaxDurability() / 2) {
                event.setCancelled(true);
                damageable.setDamage(0);
                itemBeingDamaged.setItemMeta(damageable);
            }
        }
    }
}
