package com.nessxxiii.titanenchants.listeners.mcMMO;

import com.gmail.nossr50.events.skills.McMMOPlayerNotificationEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.nessxxiii.titanenchants.items.ItemInfo;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class McMMOManager implements Listener {

    Player player;
    Material coolDown = Material.COD_SPAWN_EGG;

    @EventHandler
    public void onRightClick(PlayerInteractEvent event){
        if(!event.getAction().isRightClick())return;
        player = event.getPlayer();
    }

    @EventHandler
    public void cancelMcMMO(McMMOPlayerAbilityActivateEvent event){
        if (ItemInfo.isActiveCharged(event.getPlayer().getInventory().getItemInMainHand())
                || ItemInfo.isActiveImbued(event.getPlayer().getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }

    }
    @EventHandler
    public void cancelMcMMOMessage(McMMOPlayerNotificationEvent notificationEvent){
        if (!notificationEvent.getEventNotificationType().toString().equals("ToolReady")) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        if(ItemInfo.isActiveCharged(item) || ItemInfo.isActiveImbued(item)) {
            notificationEvent.setCancelled(true);
            if (!player.hasCooldown(coolDown) && !player.isSneaking()) {
                player.sendActionBar(Component.text("This seems to have no affect..."));
                player.setCooldown(coolDown, 20 * 15);
            }
        }
    }

}
