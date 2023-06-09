package com.nessxxiii.titanenchants.listeners.blockbreak;

import com.playtheatria.jliii.generalutils.items.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PowerCrystalDrop implements Listener {

    @EventHandler
    public static void diamondBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        if (block.getType() != Material.AMETHYST_BLOCK) return;
        Player player = event.getPlayer();
        if (!validateIsExcavator(player.getInventory().getItemInMainHand())) return;
        if (!player.hasPermission("titan.enchants.powercrystaldrop")) return;
        event.setCancelled(true);
        block.setType(Material.AIR);
        handleDropPowerCrystal(player.getLocation(), block.getLocation());
    }

    private static boolean validateIsExcavator(ItemStack itemStack) {
        if (itemStack == null || itemStack.equals(Material.AIR)) return false;
        if(itemStack.getLore() == null || !itemStack.getItemMeta().getLore().equals(ItemCreator.excavator.getItemMeta().getLore())
                || !itemStack.getEnchantments().equals(ItemCreator.excavator.getEnchantments())) {
            return false;
        }
        return true;
    }

    private static void handleDropPowerCrystal(Location playerLocation, Location blockLocation) {
        int randomNumber = getRandomNumber(1,100);
        if (randomNumber > 95) {
//            if (randomNumber > 99) {
//                playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalSuper);
//                Bukkit.getConsoleSender().sendMessage("Random number: " + randomNumber);
//                return;
//            }
            playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalUncommon);
            return;
        }
        if (randomNumber <= 3) {
            for (int i = 0; i < randomNumber; i++){
                playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalCommon);
            }
        } else {
            playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalCommon);
        }
    }

    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
