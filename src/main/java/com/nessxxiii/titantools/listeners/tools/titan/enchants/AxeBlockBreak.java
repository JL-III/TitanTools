package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.generalutils.ConfigManager;
import com.nessxxiii.titantools.events.tools.titan.enchants.AxeBlockBreakEvent;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class AxeBlockBreak implements Listener {

    private final ConfigManager configManager;

    private final Debugger debugger;

    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    public AxeBlockBreak(ConfigManager configManager, Debugger debugger) {
        this.configManager = configManager;
        this.debugger = debugger;
    }

    @EventHandler
    public void onAxeBlockBreak(AxeBlockBreakEvent event) {
        Block blockBroken = event.getBlock();
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        List<String> lore = itemInMainHand.getLore();

        Utils.processChargeManagement(player, debugger, itemInMainHand, lore);

        for (Block block : Utils.getSphereBlocks(blockBroken.getLocation(), 5, false)) {
            if (block.getLocation().equals(blockBroken.getLocation())) {
                continue;
            }
            if (configManager.getAllowedAxeBlocks().contains(block.getType())) {
                IGNORE_LOCATIONS.add(block.getLocation());
                if (Utils.simulateBlockBreakEventIsCancelled(block, player)) continue;
                Block blockBelow = block.getLocation().subtract(0, 1, 0).getBlock();
                if (Utils.REPLANT_MATERIAL_LIST.contains(blockBelow.getType())) {
                    if (Utils.REPLANT_MAP.containsKey(block.getType())) {
                        block.setType(Utils.REPLANT_MAP.get(block.getType()));
                    }
                    continue;
                }
                block.breakNaturally(itemInMainHand);
            }
        }
    }
}
