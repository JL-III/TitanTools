package com.nessxxiii.titanenchants.listeners.mcMMO;

import com.gmail.nossr50.events.skills.McMMOPlayerNotificationEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.playtheatria.jliii.generalutils.items.TitanItemInfo;
import net.kyori.adventure.text.Component;
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
        //TODO is this doing anything???
        if(!event.getAction().isRightClick()) return;
        player = event.getPlayer();
    }

    @EventHandler
    public void cancelMcMMO(McMMOPlayerAbilityActivateEvent event){
        if (TitanItemInfo.isActiveCharged(event.getPlayer().getInventory().getItemInMainHand())
                || TitanItemInfo.isActiveImbued(event.getPlayer().getInventory().getItemInMainHand())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelMcMMOMessage(McMMOPlayerNotificationEvent notificationEvent){
        if (!notificationEvent.getEventNotificationType().toString().equals("ToolReady")) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!item.hasItemMeta()) return;
        if (TitanItemInfo.isTitanTool(item)) {
            notificationEvent.setCancelled(true);
            if (!player.hasCooldown(coolDown) && !player.isSneaking()) {
                player.sendActionBar(Component.text(""));
                player.setCooldown(coolDown, 20 * 15);
            }
        }
    }

}
