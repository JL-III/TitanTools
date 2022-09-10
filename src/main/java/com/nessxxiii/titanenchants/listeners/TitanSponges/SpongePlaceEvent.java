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
        Location location = blockPlaced.getLocation();
        //item placement event is cancelled to obtain the item in hand, it is then removed from players inv afterwards
        List<Block> surroundingBlocks = ListGenerators.getSurroundingBlocks(blockPlaced);
        if(player.isSneaking()) return;
        if (player.hasCooldown(coolDown)) return;
        boolean hasLavaOrWater = false;
        for (Block blocks : surroundingBlocks){
            if (blocks.getType() == Material.WATER || blocks.getType() == Material.LAVA){
                hasLavaOrWater = true;
                break;
            }
        }
        if (!hasLavaOrWater) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        Inventory inv = player.getInventory();

        if (ItemInfo.isWater(item) ) {
            List<Block> blockList = ListGenerators.generateSphere(location,15,false);
            waterPickUp(location,blockList,player,inv,item,titanEnchants);
            //Cooldown to reduce frequency of usage to once per second
            player.setCooldown(coolDown, 20);
        } else if (ItemInfo.isLava(item)) {
            List<Block> blockList = ListGenerators.generateSphere(location,10,false);
            lavaPickUp(location,blockList,player,inv,item);
            //Cooldown to reduce frequency of usage to once per second
            player.setCooldown(coolDown, 20);
        }
    }

    public void waterPickUp(Location location, List<Block> blockList, Player player, Inventory inv, ItemStack item, TitanEnchants titanEnchants) {

        int amountInStack = item.getAmount();
        player.sendMessage("The item you are holding has water lore and you have : " + item.getAmount() + " in your hand");
        List<Block> surroundingBlocks = ListGenerators.getSurroundingBlocks(location.getBlock());
        boolean hasWater = false;

        for (Block blocks : surroundingBlocks) {
            if (blocks.getType() == Material.WATER) {
                hasWater = true;
                break;
            }
        }
        if (!hasWater) {
            player.sendMessage(ChatColor.RED + "You cannot use a Titan Water Sponge here!");
            return;
        }

        for (Block blocks : blockList) {
            if (blocks.getType() == Material.KELP_PLANT ||
                    blocks.getType() == Material.TALL_SEAGRASS ||
                    blocks.getType() == Material.SEAGRASS) {
                blocks.breakNaturally();
            }
        }
        BukkitTask waterPickUp = new BukkitRunnable() {
            @Override
            public void run() {
                for (Block blocks : blockList) {
                    if (blocks.getType() == Material.WATER) {
                        blocks.setType(Material.AIR);
                    }
                }
            }
        }.runTaskLater(titanEnchants,3);
        item.setAmount(amountInStack - 1);
        player.updateInventory();
        location.getBlock().setType(Material.AIR);

    }

    public void lavaPickUp(Location location, List<Block> blockList, Player player, Inventory inv, ItemStack item) {

        player.sendMessage("The item you are holding has lava lore: " + item.getAmount() + " in your hand");
        blockList = ListGenerators.generateSphere(location, 10, false);
        boolean hasLava = false;
        for (Block blocks : blockList) {
            if (blocks.getType() == Material.LAVA) {
                hasLava = true;
                break;
            }
        }
        if (!hasLava) {
            player.sendMessage(ChatColor.RED + "You cannot use a Titan Lava Sponge here!");
            return;
        }

        for (Block blocks : blockList) {
            if (blocks.getType() == Material.LAVA) {
                blocks.setType(Material.AIR);
            }
        }

    }

}
