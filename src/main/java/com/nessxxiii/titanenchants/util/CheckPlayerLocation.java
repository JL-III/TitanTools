package com.nessxxiii.titanenchants.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class CheckPlayerLocation implements Runnable {

    @Override
    public void run() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            //account for god mode or add permission

            if (player.getWorld().getName().equalsIgnoreCase("world")
                    && (player.getInventory().getHelmet() == null || player.getInventory().getHelmet().getType() != Material.GLASS)
            ) {
                if (player.getHealth() <= 10) {
                    player.addPotionEffect(PotionEffectType.DARKNESS.createEffect(500, 10));
                    player.addPotionEffect(PotionEffectType.CONFUSION.createEffect(500, 10));
                    player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You are going blind from suffocation!");
                } else {
                    player.sendActionBar(ChatColor.YELLOW + "" + ChatColor.ITALIC + "You cannot breathe on the moon without a space helmet!");
                }
                player.setFoodLevel(player.getFoodLevel() > 2 ? player.getFoodLevel() - 2 : 0);
                player.damage(player.getHealth() > 2 ? 2 : player.getHealth());
            }
        }

    }
}
