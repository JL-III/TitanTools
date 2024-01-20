package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Response;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement.decreaseChargeLore;
import static com.nessxxiii.titantools.util.Utils.titanShovelValidation;

public class TitanShovel implements Listener {

    public static final Set<Material> DISALLOWED_ITEMS = new HashSet<>();
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    private final Plugin PLUGIN;
    private final Debugger debugger;

    public TitanShovel(Plugin plugin, Debugger debugger) {
        this.PLUGIN = plugin;
        this.debugger = debugger;
        loadConfig();
    }

    @EventHandler
    public void titanShovelBreakBlock(PlayerInteractEvent event) {
        Response<List<String>> titanShovelValidationResponse = titanShovelValidation(event);
        if (titanShovelValidationResponse.error() != null) {
            debugger.sendDebugIfEnabled(titanShovelValidationResponse.error());
            return;
        }

        IGNORE_LOCATIONS.clear();
        if (IGNORE_LOCATIONS.contains(event.getClickedBlock().getLocation())) {
            IGNORE_LOCATIONS.remove(event.getClickedBlock().getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        BlockFace blockFace = event.getBlockFace();
        Block clickedBlock = event.getClickedBlock();
        boolean hasChargeLore = ItemInfo.hasChargeLore(titanShovelValidationResponse.value(), true);
        if (hasChargeLore) {
            Response<Integer> getChargeResponse = ItemInfo.getCharge(titanShovelValidationResponse.value(), true, true, 39);
            if (getChargeResponse.error() != null) {
                debugger.sendDebugIfEnabled(getChargeResponse.error());
                return;
            }
            decreaseChargeLore(debugger, itemInMainHand, titanShovelValidationResponse.value(), true, hasChargeLore, player);
        }

        if (clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.SHULKER_BOX || clickedBlock.getType() == Material.BARREL) {
            clickedBlock.breakNaturally(itemInMainHand);
        } else {
            if (DISALLOWED_ITEMS.contains(clickedBlock.getType())) return;
            clickedBlock.setType(Material.AIR);
        }

        for (Block blockLoop : getNearbyBlocks(clickedBlock.getLocation(), blockFace)) {
            if (blockLoop.getLocation().equals(clickedBlock.getLocation())) {
                continue;
            }
            if (!DISALLOWED_ITEMS.contains(blockLoop.getType())) {
                IGNORE_LOCATIONS.add(blockLoop.getLocation());
                BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
                Bukkit.getPluginManager().callEvent(e);

                if (!e.isCancelled()) {
                    if ((blockLoop.getLocation().getY() > -64 && !player.getWorld().getEnvironment().equals(World.Environment.NETHER))
                            || ((blockLoop.getLocation().getY() > 0 && blockLoop.getLocation().getY() < 127) && player.getWorld().getEnvironment().equals(World.Environment.NETHER))) {
                        e.setDropItems(false);
                        blockLoop.breakNaturally(itemInMainHand);
                    }
                }
                e.setCancelled(true);
            }
        }
    }

    public void loadConfig() {
        ConfigurationSection titanShovel = PLUGIN.getConfig().getConfigurationSection("titanShovel");
        if (titanShovel == null) {
            PLUGIN.getLogger().warning("TitanShovel configuration not found!");
            return;
        }
        List<String> items = titanShovel.getStringList("non-destroyable-items");
        if (items.size() == 0) {
            PLUGIN.getLogger().warning("No destroyable-items found in titanShovel section of config.");
        }
        for (String item : items) {
            try {
                DISALLOWED_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                PLUGIN.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }

    private static List<Block> getNearbyBlocks(Location location, BlockFace blockFace) {
        List<Block> blocks = new ArrayList<>();

        if (blockFace.getModY() != 0){
            int y = location.getBlockY();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModX() !=0){
            int x = location.getBlockX();
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModZ() !=0){
            int z = location.getBlockZ();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

}
