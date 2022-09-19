package com.nessxxiii.titanenchants.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.List;

public interface VolumeFiller {

    void fill(Location cornerA, Location cornerB, Material material);

    void fillSphereWithCheck(Player player, Block initialBlock, int radius, Material material, boolean hollow);

    boolean cubeCheck(Player player, Block block, int radius);

    boolean canRunSphere(Player player, Block initialBlock, int radius);

    void schemRun(Player player, Block block, File file);
}
