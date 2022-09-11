package com.nessxxiii.titanenchants.util;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.util.ArrayList;
import java.util.List;

public class ListGenerators {

    public static List<Block> getSurroundingBlocks(Block blockPlaced) {
        List<Block> surroundingBlocks = new ArrayList<>();
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.DOWN,1));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.DOWN,2));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.UP,1));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.UP,2));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.EAST,1));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.EAST,2));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.WEST,1));
        surroundingBlocks.add(blockPlaced.getRelative(BlockFace.WEST,2));
        return surroundingBlocks;
    }

    public static List<Block> generateSphere (Location location, int radius, boolean hollow){
        List<Block> circleBlocks = new ArrayList<>();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
                        circleBlocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return circleBlocks;
    }

}
