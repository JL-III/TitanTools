package com.nessxxiii.titantools.listeners.blockbreak;

import com.nessxxiii.titantools.events.PowerCrystalDropEvent;
import com.nessxxiii.titantools.items.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class BlockBreak implements Listener {

    // currently the thought is that this onBlockBreak method handles the validation
    // then calls the PowerCrystalDropEvent to trigger the PowerCrystalDrop listener

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event){
        // validation
        if (event.getBlock().getType() != Material.AMETHYST_BLOCK) return;
        if (!validateIsExcavator(event.getPlayer().getInventory().getItemInMainHand())) return;
        if (!event.getPlayer().hasPermission("titan.enchants.powercrystaldrop")) return;
        // event
        event.setCancelled(true);
        Bukkit.getPluginManager().callEvent(new PowerCrystalDropEvent(event.getPlayer(), event.getBlock().getLocation()));
    }

    private static boolean validateIsExcavator(ItemStack itemStack) {
        if (itemStack == null || itemStack.equals(Material.AIR)) return false;
        if (itemStack.getLore() == null || !itemStack.getItemMeta().getLore().equals(ItemCreator.excavator.getItemMeta().getLore())
                || !itemStack.getEnchantments().equals(ItemCreator.excavator.getEnchantments())) {
            return false;
        }
        return true;
    }
}
