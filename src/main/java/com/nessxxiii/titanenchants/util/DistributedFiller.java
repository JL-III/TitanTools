package com.nessxxiii.titanenchants.util;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This implementation does not change a Block instantly.
 * It defines a Workload (PlacableBlock in this case) and simply passes it
 * to the WorkloadRunnable. We then forget about it because the runnable
 * will decide when there is enough CPU time left for this Workload to be
 * computed.
 */
@AllArgsConstructor
public class DistributedFiller implements VolumeFiller {

    private final WorkloadRunnable workloadRunnable;
    private final Set<Material> MATERIALS_TO_REPLACE = new HashSet<>() {{

        add(Material.KELP);
        add(Material.KELP_PLANT);
        add(Material.TALL_SEAGRASS);
        add(Material.SEAGRASS);
        add(Material.WATER);
        add(Material.LAVA);

    }};

    @Override
    public void fill(Location cornerA, Location cornerB, Material material) {
        Preconditions.checkArgument(cornerA.getWorld() == cornerB.getWorld() && cornerA.getWorld() != null);
        BoundingBox box = BoundingBox.of(cornerA.getBlock(), cornerB.getBlock());
        Vector max = box.getMax();
        Vector min = box.getMin();

        World world = cornerA.getWorld();

        for (int x = min.getBlockX(); x <= max.getBlockX(); x++) {
            for (int y = min.getBlockY(); y <= max.getBlockY(); y++) {
                for (int z = min.getBlockZ(); z <= max.getBlockZ(); z++) {
                    WaterReplace waterReplace = new WaterReplace(world.getUID(), x, y, z, material);
                    this.workloadRunnable.addWorkload(waterReplace);
                }
            }
        }


    }

    @Override
    public void fillSphereWithCheck(Player player, Block initialBlock, int radius, Material material, boolean hollow) {

        Location location = initialBlock.getLocation();
        Preconditions.checkArgument( location.getWorld() != null);

        World world = location.getWorld();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1))) &&
                            (MATERIALS_TO_REPLACE.contains(location.getWorld().getBlockAt(x, y, z).getType()))
                    ) {
                        WaterReplace waterReplace = new WaterReplace(world.getUID(), x, y, z, material);
                        this.workloadRunnable.addWorkload(waterReplace);
                    }
                }
            }
        }
    }

    @Override
    public boolean canRunSphere(Player player, Block initialBlock, int radius) {

        Location location = initialBlock.getLocation();
        Preconditions.checkArgument( location.getWorld() != null);

        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if (distance < radius * radius && !(distance < (radius - 1) * (radius - 1))) {
                        BlockBreakEvent e = new BlockBreakEvent(location.getWorld().getBlockAt(x, y, z), player);
                        Bukkit.getPluginManager().callEvent(e);
                        if (e.isCancelled()) {
                            return false;
                        }

                    }
                }
            }
        }
        return true;
    }

    @Override
    public void fillCube(Location location, int radius, Material material, boolean hollow) {
        Preconditions.checkArgument( location.getWorld() != null);

        World world = location.getWorld();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for (int x = location.getBlockX() - radius; x <= location.getBlockX() + radius; x++) {
            for (int y = location.getBlockY() - radius; y <= location.getBlockY() + radius; y++) {
                for (int z = location.getBlockZ() - radius; z <= location.getBlockZ() + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    //                  (100 - (100 - 15)) * (100 - (100 - 15))
                    //                  (100 - 85) * (100 - 85)
                    //                  (15 * 15)
                    //                  255
                    if (hollow && (((x == location.getBlockX() - radius || x == location.getBlockX() + radius)
                            && (MATERIALS_TO_REPLACE.contains(location.getWorld().getBlockAt(x, y, z).getType())))
                            || ((y == location.getBlockY() - radius || y == location.getBlockY() + radius)
                            && (MATERIALS_TO_REPLACE.contains(location.getWorld().getBlockAt(x, y, z).getType())))
                            || ((z == location.getBlockZ() - radius || z == location.getBlockZ() + radius)
                            && (MATERIALS_TO_REPLACE.contains(location.getWorld().getBlockAt(x, y, z).getType()))))
                    ) {
                        WaterReplace waterReplace = new WaterReplace(world.getUID(), x, y, z, material);
                        this.workloadRunnable.addWorkload(waterReplace);
                    }else {
                        if (MATERIALS_TO_REPLACE.contains(location.getWorld().getBlockAt(x, y, z).getType())) {
                            WaterReplace waterReplace = new WaterReplace(world.getUID(), x, y, z, material);
                            this.workloadRunnable.addWorkload(waterReplace);
                        }
                    }

                }
            }
        }
    }


}
