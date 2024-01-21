package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.generalutils.ConfigManager;
import com.nessxxiii.titantools.events.tools.titan.enchants.ShovelBlockBreakEvent;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ShovelBlockBreak implements Listener {

    private final ConfigManager configManager;
    private final Debugger debugger;

    public ShovelBlockBreak(ConfigManager configManager, Debugger debugger) {
        this.configManager = configManager;
        this.debugger = debugger;
    }

    @EventHandler
    public void titanShovelBreakBlock(ShovelBlockBreakEvent event) {
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        Block clickedBlock = event.getClickedBlock();
        List<String> lore = itemInMainHand.getLore();

        Utils.processChargeManagement(event.getPlayer(), debugger, itemInMainHand, lore);

        if (Utils.shouldBreakClickedBlockNaturally(clickedBlock)) {
            clickedBlock.breakNaturally(itemInMainHand);
        } else {
            if (configManager.getDisallowedShovelItems().contains(clickedBlock.getType())) return;
            if (Utils.canBreakBlock(clickedBlock.getLocation())) {
                clickedBlock.setType(Material.AIR);
            }
        }
        for (Block blockLoop : Utils.getNearbyBlocks(clickedBlock.getLocation(), event.getBlockFace())) {
            if (blockLoop.getLocation().equals(clickedBlock.getLocation())) {
                continue;
            }
            if (!configManager.getDisallowedShovelItems().contains(blockLoop.getType())) {
                BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
                Bukkit.getPluginManager().callEvent(e);

                if (!e.isCancelled()) {
                    if (Utils.canBreakBlock(blockLoop.getLocation())) {
                        e.setDropItems(false);
                        blockLoop.breakNaturally(itemInMainHand);
                    }
                }
                e.setCancelled(true);
            }
        }
    }
}
