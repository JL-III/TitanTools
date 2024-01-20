package com.nessxxiii.titantools.listeners.commands.admin;

import com.nessxxiii.titantools.events.commands.admin.SetModelDataEvent;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.meta.ItemMeta;

public class SetModelData implements Listener {

    @EventHandler
    public void onSetModelData(SetModelDataEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            Utils.sendPluginMessage(player, "You must be holding an item.");
            return;
        }
        try {
            if (player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                Utils.sendPluginMessage(player, "Previous custom model data: " + ChatColor.YELLOW + player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData());
            }
            Integer customModelData = Integer.parseInt(event.getArgs()[1]);
            ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
            meta.setCustomModelData(customModelData);
            player.getInventory().getItemInMainHand().setItemMeta(meta);
            Utils.sendPluginMessage(player, "Set custom model data to: " + ChatColor.GREEN + customModelData);
        } catch (Exception ex) {
            Utils.sendPluginMessage(player, "You must provide an integer value.");
        }
    }
}
