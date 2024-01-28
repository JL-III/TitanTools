package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.generalutils.ConfigManager;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Utils;
import com.playtheatria.jliii.generalutils.events.tools.titan.enchants.PickBlockBreakEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
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
    public void onPickBlockBreak(PickBlockBreakEvent event) {
        List<String> lore = event.getPlayer().getInventory().getItemInMainHand().getLore();
        Block blockBroken = event.getBlock();
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        Utils.processChargeManagement(player, debugger, itemInMainHand, lore);

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
            if (!itemInMainHand.getItemMeta().hasEnchant(Enchantment.SILK_TOUCH)) {
                aggregateAmount = aggregateAmount + Utils.calculateExperienceAmount(currentBlock.getType());
            }
            currentBlock.setType(Material.AIR);
        }
        if (aggregateAmount > 0) {
            blockBroken.getLocation().getWorld().spawn(blockBroken.getLocation(), ExperienceOrb.class).setExperience(aggregateAmount);
        }
    }
}
