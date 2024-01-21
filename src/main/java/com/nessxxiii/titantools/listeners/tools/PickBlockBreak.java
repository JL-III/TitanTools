package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.events.tools.PickBlockBreakEvent;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class PickBlockBreak implements Listener {
    private final ConfigManager configManager;
    private final Debugger debugger;
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    private final Material cooldownMaterial = Material.BARRIER;

    public PickBlockBreak(ConfigManager configManager, Debugger debugger) {
        this.configManager = configManager;
        this.debugger = debugger;
        configManager.loadConfig();
    }

    @EventHandler
    public void onBlockBreakEvent(PickBlockBreakEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "IgnoreLocations size: " + IGNORE_LOCATIONS.size());
        List<String> lore = event.getPlayer().getInventory().getItemInMainHand().getLore();
        Block blockBroken = event.getBlock();
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        boolean hasChargeLore = ItemInfo.hasChargeLore(lore, true);

        if (hasChargeLore) {
            Response<Integer> getChargeResponse = ItemInfo.getCharge(lore, true, true, 39);
            if (getChargeResponse.error() != null) {
                debugger.sendDebugIfEnabled(getChargeResponse.error());
                return;
            }
            ChargeManagement.decreaseChargeLore(debugger, itemInMainHand, lore, true, true, player);
        }

        int aggregateAmount = 0;
        for (Block currentBlock : Utils.getCubeBlocks(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.add(currentBlock.getLocation());
            if (!configManager.getAllowedPickBlocks().contains(currentBlock.getType())) continue;
            if (Utils.simulateBlockBreakEventIsCancelled(currentBlock, player)) continue;
            //iterate through drops of current block
            for (ItemStack drop : Utils.getDropsProcessed(currentBlock, player)) {
                Item itemEntity = currentBlock.getWorld().dropItemNaturally(currentBlock.getLocation(), drop);
                if (Utils.simulateItemPickupIsCancelled(itemEntity, player)) continue;
                if (player.getInventory().firstEmpty() == -1) {
                    if (!player.hasCooldown(cooldownMaterial)) {
                        player.sendMessage(ChatColor.RED + "Your inventory is full, items are not getting placed into inventory!");
                        player.setCooldown(cooldownMaterial, 500);
                    }
                    continue;
                };
                itemEntity.remove();
                player.getInventory().addItem(drop);
            }
            aggregateAmount = aggregateAmount + Utils.calculateExperienceAmount(currentBlock.getType());
            currentBlock.setType(Material.AIR);
        }
        if (aggregateAmount > 0) {
            blockBroken.getLocation().getWorld().spawn(blockBroken.getLocation(), ExperienceOrb.class).setExperience(aggregateAmount);
        }
    }
}
