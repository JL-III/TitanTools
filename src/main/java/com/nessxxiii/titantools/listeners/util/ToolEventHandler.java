package com.nessxxiii.titantools.listeners.util;

import com.nessxxiii.titantools.events.tools.excavator.ExcavatorBlockBreakEvent;
import com.nessxxiii.titantools.events.tools.titan.enchants.AxeBlockBreakEvent;
import com.nessxxiii.titantools.events.tools.titan.enchants.PickBlockBreakEvent;
import com.nessxxiii.titantools.events.tools.titan.enchants.ShovelBlockBreakEvent;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ToolEventHandler implements Listener {

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event){
        // validation
        if (event.getBlock().getType() != Material.AMETHYST_BLOCK) return;
        if (!Utils.isValidExcavator(event.getPlayer().getInventory().getItemInMainHand())) return;
        if (!event.getPlayer().hasPermission("titan.enchants.powercrystaldrop")) return;
        // event
        event.setCancelled(true);
        Bukkit.getPluginManager().callEvent(new ExcavatorBlockBreakEvent(event.getPlayer(), event.getBlock().getLocation()));
    }

    @EventHandler
    public static void onBlockBreakDetectTool(BlockBreakEvent event) {
        // this section will determine the item a player is holding and then fire the appropriate event
        if (event.isCancelled()) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.DIAMOND_SHOVEL || itemInMainHand.getType() == Material.NETHERITE_SHOVEL) return;
        if (!Utils.isValidTitanTool(event.getPlayer())) return;
        switch (itemInMainHand.getType()) {
            case DIAMOND_PICKAXE, NETHERITE_PICKAXE -> {
                Bukkit.getPluginManager().callEvent(new PickBlockBreakEvent(event.getPlayer(), event.getBlock()));
            }
            case DIAMOND_AXE, NETHERITE_AXE -> {
                Bukkit.getPluginManager().callEvent(new AxeBlockBreakEvent(event.getPlayer(), event.getBlock()));
            }
        }
    }

    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction().isRightClick()) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (itemInMainHand.getType() != Material.DIAMOND_SHOVEL && itemInMainHand.getType() != Material.NETHERITE_SHOVEL) return;
        if (!Utils.isValidTitanTool(event.getPlayer())) return;
        switch (itemInMainHand.getType()) {
            case DIAMOND_SHOVEL, NETHERITE_SHOVEL -> {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new ShovelBlockBreakEvent(event.getPlayer(), event.getClickedBlock(), event.getBlockFace()));
            }
        }
    }
}
