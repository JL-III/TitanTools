package com.nessxxiii.titanenchants.listeners.mcMMOManagement;

import com.gmail.nossr50.datatypes.interactions.NotificationType;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.events.skills.McMMOPlayerNotificationEvent;
import com.gmail.nossr50.events.skills.McMMOPlayerSkillEvent;
import com.gmail.nossr50.events.skills.abilities.McMMOPlayerAbilityActivateEvent;
import com.playtheatria.jliii.generalutils.enums.ToolStatus;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class McMMO implements Listener {

    Material coolDown = Material.COD_SPAWN_EGG;

    @EventHandler(priority = EventPriority.HIGH)
    public void cancelMcMMO(McMMOPlayerAbilityActivateEvent event){
        Response<List<String>> loreListResponse = TitanItem.getLore(event.getPlayer().getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) return;
        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        Bukkit.getConsoleSender().sendMessage("isTitanTool: " + isTitanTool);
        if (isTitanTool) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void cancelMcMMOMessage(McMMOPlayerSkillEvent event) {
        event.getPlayer().sendMessage("SkillType " + event.getSkill().name());
    }


    @EventHandler
    public void cancelMcMMOMessage(McMMOPlayerNotificationEvent notificationEvent){
        if (notificationEvent.getEventNotificationType() != NotificationType.TOOL) return;
        if (notificationEvent.getWho() == null) return;
        Player player = notificationEvent.getWho();
        Response<List<String>> loreListResponse = TitanItem.getLore(player.getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) return;
        if (TitanItem.isTitanTool(loreListResponse.value())) {
            notificationEvent.setCancelled(true);
            if (!player.hasCooldown(coolDown) && !player.isSneaking()) {
                player.sendActionBar(Component.text(""));
                player.setCooldown(coolDown, 20 * 15);
            }
        }
    }

}
