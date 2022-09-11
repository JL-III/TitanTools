package com.nessxxiii.titanenchants.listeners.TitanSponges;

import com.nessxxiii.titanenchants.TitanEnchants;
import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.ListGenerators;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;

import static org.bukkit.inventory.EquipmentSlot.HAND;

public class SpongePlaceEvent implements Listener {

    private final TitanEnchants titanEnchants;

    public SpongePlaceEvent(TitanEnchants titanEnchants) {
        this.titanEnchants = titanEnchants;
    }

    Material coolDown = Material.REDSTONE;

    @EventHandler
    public void onPlaceEvent(BlockPlaceEvent event) {

        Player player = event.getPlayer();
        Block blockPlaced = event.getBlock();

        //material is sponge
        if (blockPlaced.getType() != Material.SPONGE) return;
        //item is in main hand
        if (!event.getHand().equals(HAND)) return;
        //has enchantment (only titan sponges should have an enchantment)
        if (!event.getItemInHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        //item placement event is cancelled to obtain the item in hand, it is then removed from players inv afterwards
        if (player.hasCooldown(coolDown)) {
            event.setCancelled(true);
            player.sendMessage("Please wait " + player.getCooldown(coolDown)/20 + " seconds before using another Titan Sponge.");
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
        player.sendMessage("The item you are holding has water lore and you have : " + player.getInventory().getItemInMainHand().getAmount() + " in your hand");
        drainLavaOrWater(ListGenerators.generateSphere(blockPlaced.getLocation(),15,false), titanEnchants);
        //Cooldown to reduce frequency of usage to once per second
        player.setCooldown(coolDown, 20);

    }

    public void drainLavaOrWater(List<Block> blockList, TitanEnchants titanEnchants) {
        for (Block blocks : blockList) {
            if (blocks.getType() == Material.KELP_PLANT ||
                    blocks.getType() == Material.TALL_SEAGRASS ||
                    blocks.getType() == Material.SEAGRASS) {
                blocks.breakNaturally();
            }
        }
        BukkitTask runTaskLater = new BukkitRunnable() {
            @Override
            public void run() {
                for (Block currentBlockInList : blockList) {
                    if (currentBlockInList.getType() == Material.WATER || currentBlockInList.getType() == Material.LAVA) {
                        currentBlockInList.setType(Material.AIR);
                    }
                }
            }
        }.runTaskLater(titanEnchants,5);

    }
}
