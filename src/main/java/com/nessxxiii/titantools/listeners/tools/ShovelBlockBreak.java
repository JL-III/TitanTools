package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.events.tools.ShovelBlockBreakEvent;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
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

        if (shouldBreakClickedBlockNaturally(clickedBlock)) {
            clickedBlock.breakNaturally(itemInMainHand);
        } else {
            if (configManager.getDisallowedShovelItems().contains(clickedBlock.getType())) return;
            if (canBreakBlock(clickedBlock.getLocation())) {
                clickedBlock.setType(Material.AIR);
            }
        }
        for (Block blockLoop : getNearbyBlocks(clickedBlock.getLocation(), event.getBlockFace())) {
            if (blockLoop.getLocation().equals(clickedBlock.getLocation())) {
                continue;
            }
            if (!configManager.getDisallowedShovelItems().contains(blockLoop.getType())) {
                BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
                Bukkit.getPluginManager().callEvent(e);

                if (!e.isCancelled()) {
                    if (canBreakBlock(blockLoop.getLocation())) {
                        e.setDropItems(false);
                        blockLoop.breakNaturally(itemInMainHand);
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    private boolean shouldBreakClickedBlockNaturally(Block clickedBlock) {
        return clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.SHULKER_BOX || clickedBlock.getType() == Material.BARREL;
    }

    private boolean canBreakBlock(Location blockLocation) {
        return ((blockLocation.getY() > -64 && !blockLocation.getWorld().getEnvironment().equals(World.Environment.NETHER))
                || ((blockLocation.getY() > 0 && blockLocation.getY() < 127) && blockLocation.getWorld().getEnvironment().equals(World.Environment.NETHER)));
    }

    private static List<Block> getNearbyBlocks(Location location, BlockFace blockFace) {
        List<Block> blocks = new ArrayList<>();

        if (blockFace.getModY() != 0){
            int y = location.getBlockY();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModX() !=0){
            int x = location.getBlockX();
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModZ() !=0){
            int z = location.getBlockZ();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }
}
