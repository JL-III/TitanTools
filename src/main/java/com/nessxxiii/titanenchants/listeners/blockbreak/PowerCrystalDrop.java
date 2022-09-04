package com.nessxxiii.titanenchants.listeners.blockbreak;

import com.nessxxiii.titanenchants.items.ItemManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PowerCrystalDrop implements Listener {

    @EventHandler
    public static void diamondBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        if (block.getType() != Material.DIAMOND_ORE && block.getType() != Material.DEEPSLATE_DIAMOND_ORE ) return;
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
//        if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if(!block.getLocation().getWorld().getName().equalsIgnoreCase("world")){
            player.sendMessage("You are not in the correct world for this!");
            return;
        }

        if(player.getInventory().getItemInMainHand().getLore() == null || !player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(ItemManager.excavator.getItemMeta().getLore())
            || !player.getInventory().getItemInMainHand().getEnchantments().equals(ItemManager.excavator.getEnchantments())) {
            return;
        }
        player.sendMessage("Diamond Break");
        if (!player.hasPermission("titan.enchants.powercrystaldrop")) return;
//        if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SHOVEL) return;
//        if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        Location loc = block.getLocation();

        int randomNumber = getRandomNumber(1,10);
        if (randomNumber <= 3) {
            event.setCancelled(true);
            event.getBlock().setType(Material.AIR);
            for (int i = 0; i < randomNumber; i++){
                player.getLocation().getWorld().dropItem(loc, ItemManager.powerCrystal);
            }

        } else
            player.getLocation().getWorld().dropItem(loc, ItemManager.powerCrystal);
    }
    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
