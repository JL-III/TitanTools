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
        //for testing
//        add(Material.AIR);

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
        int count = 1;
        for (int x = bx - radius; x <= bx + radius; x++) {
            for (int y = by - radius; y <= by + radius; y++) {
                for (int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx - x) * (bx - x) + ((bz - z) * (bz - z)) + ((by - y) * (by - y)));
                    if ((distance < radius * radius ) && !(distance < (radius - 1) * (radius - 1)) ) {
                        if  (
                                ((x == bx - (radius - 1) || x == bx + (radius + 1)) && (z == location.getBlockZ()) || y == location.getBlockY() || x == location.getBlockX()) ||
                                ((y == by - (radius - 1) || y == by + (radius - 1)) && ((x == location.getBlockX() || z == location.getBlockZ()) || y == location.getBlockY()) ||
                                        //diagonals
                                        (z - bz == x - bx ||  -(z - bz) == (x - bx) || (z - bz) == -(x - bx) )) ||
                                ((z == bz - (radius - 1) || z == bz + (radius - 1)) && (x == location.getBlockX() || y == location.getBlockY()) || z == location.getBlockZ())
                            ) {
                            BlockBreakEvent e = new BlockBreakEvent(location.getWorld().getBlockAt(x, y, z), player);
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                WaterReplace waterReplace = new WaterReplace(location.getWorld().getUID(), x, y, z, Material.AMETHYST_BLOCK);
                                this.workloadRunnable.addWorkload(waterReplace);
                                Bukkit.getConsoleSender().sendMessage("Count for sphere: " + count);
                                count++;
                            }
                            if (e.isCancelled()) {
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    @Override
    public boolean cubeCheck(Player player, Block block, int radius) {

        Location location = block.getLocation();
        radius = radius - 1;
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        int positiveXBorder = bx + radius;
        int negativeXBorder = bx - radius;
        int positiveYBorder = by + radius;
        int negativeYBorder = by - radius;
        int positiveZBorder = bz + radius;
        int negativeZBorder = bz - radius;

        int count = 1;
        for (int x = negativeXBorder; x <= positiveXBorder; x++) {
            for (int y = negativeYBorder; y <= positiveYBorder; y++) {
                for (int z = negativeZBorder; z <= positiveZBorder; z++) {


                    if (
                        //top conditional creates the top portion of the cube.
                            (((y == negativeYBorder) || (y == positiveYBorder)) && (((x == negativeXBorder) || (x == positiveXBorder)) && ((bz - z) % 5 == 0) || ((z == negativeZBorder) || (z == positiveZBorder)) && ((bx - x) % 5 == 0)))
                        //bottom conditional creates the pillars
                            || (((z == negativeZBorder) || (z == positiveZBorder)) && ((x == negativeXBorder) || (x == positiveXBorder)) && (by - y) % 5 == 0)
                    ) {
                        BlockBreakEvent e = new BlockBreakEvent(location.getWorld().getBlockAt(x, y, z), player);
                        Bukkit.getPluginManager().callEvent(e);
//                            if (!e.isCancelled()) {
//                                WaterReplace waterReplace = new WaterReplace(location.getWorld().getUID(), x, y, z, Material.DEEPSLATE);
//                                this.workloadRunnable.addWorkload(waterReplace);
//                                Bukkit.getConsoleSender().sendMessage("Count for cube: " + count);
//                                count++;
//                            }
                        if (e.isCancelled()) {
                            return false;
                        }
                    }

                }
            }
        }
        return true;
    }
}
