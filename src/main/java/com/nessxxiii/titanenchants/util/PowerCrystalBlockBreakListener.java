package com.nessxxiii.titanenchants.util;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PowerCrystalBlockBreakListener implements Listener {

    @EventHandler
    public static void diamondBreak(BlockBreakEvent event){
        Block block = event.getBlock();

        if (block.getType() != Material.DIAMOND_ORE && block.getType() != Material.DEEPSLATE_DIAMOND_ORE ) return;
        Player player = event.getPlayer();
        if(player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) return;
        if(!block.getLocation().getWorld().getName().equalsIgnoreCase("world")){
            player.sendMessage("You are not in the correct world for this!");
            return;
        }
        if (!player.hasPermission("benchants.powercrystaldrop")) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.DIAMOND_SHOVEL) return;
        if (player.getInventory().getItemInMainHand().getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        Location loc = block.getLocation();

        ItemStack powerCrystal = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add("§x§F§F§0§0§4§CAncient Charge");
        powerCrystal.setLore(lore);
        ItemMeta meta = powerCrystal.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Power Crystal");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        powerCrystal.setItemMeta(meta);
        powerCrystal.addUnsafeEnchantment(Enchantment.CHANNELING,1);
        int randomNumber = getRandomNumber(1,10);
        if (randomNumber < 5) {
            for (int i = 0; i < randomNumber; i++){
                player.getLocation().getWorld().dropItem(loc, powerCrystal);
            }

        } else
            player.sendMessage("Did not drop pcrystal");
    }
    private static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
