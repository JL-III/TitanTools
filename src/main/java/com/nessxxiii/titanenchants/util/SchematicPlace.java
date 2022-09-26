package com.nessxxiii.titanenchants.util;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.bukkit.*;

import java.util.UUID;

@AllArgsConstructor
public class SchematicPlace implements Workload{

    private final UUID WORLD_ID;
    private final int BLOCK_X;
    private final int BLOCK_Y;
    private final int BLOCK_Z;
    private final Material MATERIAL;


    @Override
    public void compute() {
        World world = Bukkit.getWorld(this.WORLD_ID);
        Preconditions.checkState(world != null);
        world.getBlockAt(this.BLOCK_X, this.BLOCK_Y, this.BLOCK_Z).setType(this.MATERIAL);
        world.getBlockAt(this.BLOCK_X, this.BLOCK_Y, this.BLOCK_Z).getState().update(true, false);

    }
}
