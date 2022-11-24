package com.nessxxiii.titanenchants.listeners.blockbreak;

import com.nessxxiii.titanenchants.items.ItemCreator;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PowerCrystalDrop implements Listener {

    @EventHandler
    public static void diamondBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType() != Material.AMETHYST_BLOCK) return;
        Player player = event.getPlayer();
        //TODO break this out into a method called validateDiamondBreak
        if (player.getInventory().getItemInMainHand() == null || player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) return;
        if(player.getInventory().getItemInMainHand().getLore() == null || !player.getInventory().getItemInMainHand().getItemMeta().getLore().equals(ItemCreator.excavator.getItemMeta().getLore())
                || !player.getInventory().getItemInMainHand().getEnchantments().equals(ItemCreator.excavator.getEnchantments())) {
            return;
        }
        if (!player.hasPermission("titan.enchants.powercrystaldrop")) return;
        event.setCancelled(true);
        //TODO break this out into a method called handleDropPowerCrystal
        block.setType(Material.AIR);
        int randomNumber = getRandomNumber(1,10);
        if (randomNumber <= 3) {
            for (int i = 0; i < randomNumber; i++){
                player.getLocation().getWorld().dropItemNaturally(block.getLocation(), ItemCreator.powerCrystalCommon);
            }
        } else {
            player.getLocation().getWorld().dropItemNaturally(block.getLocation(), ItemCreator.powerCrystalCommon);
        }
    }



    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
