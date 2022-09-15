package com.nessxxiii.titanenchants.listeners.TitanSponges;

import com.nessxxiii.titanenchants.TitanEnchants;
import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.DistributedFiller;
import com.nessxxiii.titanenchants.util.ListGenerators;
import com.nessxxiii.titanenchants.util.WaterReplace;
import com.nessxxiii.titanenchants.util.WorkloadRunnable;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class SpongePlaceEvent implements Listener {

    private final TitanEnchants titanEnchants;
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    WorkloadRunnable workloadRunnable;

    public SpongePlaceEvent(TitanEnchants titanEnchants, WorkloadRunnable workloadRunnable) {
        this.titanEnchants = titanEnchants;
        this.workloadRunnable = workloadRunnable;
    }

    Material coolDown = Material.REDSTONE;

    @EventHandler
    public void onPlaceEvent(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block blockPlaced = event.getBlock();


        if (IGNORE_LOCATIONS.contains(blockPlaced.getLocation())) {
            IGNORE_LOCATIONS.remove(blockPlaced.getLocation());
            return;
        }

        //material is sponge
        if (blockPlaced.getType() != Material.SPONGE) return;
        //item is in main hand
        if (!event.getHand().equals(HAND)) return;
        //has enchantment (only titan sponges should have an enchantment)
        if (!event.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        //item placement event is cancelled to obtain the item in hand, it is then removed from players inv afterwards
        if (player.hasCooldown(coolDown)) {
            event.setCancelled(true);
            player.sendMessage(ChatColor.RED + "Please wait " + player.getCooldown(coolDown)/20 + " seconds before using another Titan Sponge.");
            return;
        }
        boolean hasLavaOrWater = false;
        for (Block blocks : ListGenerators.getSurroundingBlocks(blockPlaced)){
            if (blocks.getType() == Material.WATER || blocks.getType() == Material.LAVA){
                hasLavaOrWater = true;
                break;
            }
        }
        if (!hasLavaOrWater) {
            player.sendMessage("You must place a Titan Sponge near water or lava.");
            event.setCancelled(true);
            return;
        }
        if (new DistributedFiller(this.workloadRunnable).cubeCheck(player, blockPlaced, 12)) {
            new DistributedFiller(this.workloadRunnable).fillSphereWithCheck(player, blockPlaced, 12, Material.SPONGE,true);
            new DistributedFiller(this.workloadRunnable).fillSphereWithCheck(player, blockPlaced, 11, Material.AIR, false);
            new DistributedFiller(this.workloadRunnable).fillSphereWithCheck(player, blockPlaced, 12, Material.AIR, false);
        } else {
            player.sendMessage(ChatColor.DARK_RED + "You are too close to a claim you do not have build permissions on, please move far away from it and try again or try using a normal sponge.");
            event.setCancelled(true);
        }
        player.setCooldown(coolDown, 20);

    }
}
