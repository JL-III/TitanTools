package com.nessxxiii.titanenchants.util;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.List;

public class TitanEnchantEffects {

    public void enableEffect(Player player) {

        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,player.getEyeLocation(),100);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_ACTIVATE,10, 1);

    }
    public void disableEffect(Player player) {

        player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE,player.getEyeLocation(),100);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_BEACON_DEACTIVATE,10, 1);

    }

    public void depletedChargeEffect(Player player) {

        player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK, player.getEyeLocation(), 100);
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_DEACTIVATE, 10, 1);

    }

    public void smeltWhileCollecting(Player player, Location location) {
        int i = 0;
        for (Location locationIterate : getHollowCube(location, 0.2)) {
            player.getWorld().spawnParticle(Particle.ENCHANTMENT_TABLE, locationIterate, 1, 0.0,0.0,0.0, 0.01);
            player.sendMessage("Particle spawned: " + i);
            i++;
        }
        player.getWorld().playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_BREAK, 10, -1);

    }

    public List<Location> getHollowCube(Location loc, double particleDistance) {
        List<Location> result = Lists.newArrayList();
        World world = loc.getWorld();
        double minX = loc.getBlockX();
        double minY = loc.getBlockY();
        double minZ = loc.getBlockZ();
        double maxX = loc.getBlockX()+1;
        double maxY = loc.getBlockY()+1;
        double maxZ = loc.getBlockZ()+1;

        for (double x = minX; x <= maxX; x = Math.round((x + particleDistance) * 1e2) / 1e2) {
            for (double y = minY; y <= maxY; y = Math.round((y + particleDistance) * 1e2) / 1e2) {
                for (double z = minZ; z <= maxZ; z = Math.round((z + particleDistance) * 1e2) / 1e2) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }
        return result;
    }

}
