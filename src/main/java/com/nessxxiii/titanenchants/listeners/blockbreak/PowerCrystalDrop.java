package com.nessxxiii.titanenchants.listeners.blockbreak;

import com.nessxxiii.titanenchants.items.ItemManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;

public class PowerCrystalDrop implements Listener {

    @EventHandler
    public static void diamondBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType() != Material.AMETHYST_BLOCK) return;
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if(player.getInventory().getItemInMainHand().getLore() == null || !player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(ItemManager.excavator.getItemMeta().getLore())
                || !player.getInventory().getItemInMainHand().getEnchantments().equals(ItemManager.excavator.getEnchantments())) {
            return;
        }
        if(!block.getLocation().getWorld().getName().equalsIgnoreCase("world")){
            player.sendMessage("You are not in the correct world for this! If you are trying to excavate power crystals go to /warp mining.");
            return;
        }
        if (!player.hasPermission("titan.enchants.powercrystaldrop")) return;
        event.setCancelled(true);
        block.setType(Material.AIR);
        int randomNumber = getRandomNumber(1,10);
        if (randomNumber <= 3) {
            for (int i = 0; i < randomNumber; i++){
                player.getLocation().getWorld().dropItemNaturally(block.getLocation(), ItemManager.powerCrystal);
            }
        } else {
            player.getLocation().getWorld().dropItemNaturally(block.getLocation(), ItemManager.powerCrystal);
        }
    }
    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
