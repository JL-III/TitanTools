package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titantools.util.Response;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.nessxxiii.titantools.util.Utils.getSphereBlocks;
import static com.nessxxiii.titantools.util.Utils.titanToolBlockBreakValidation;

public class TitanAxe implements Listener {

    private static final List<Material> REPLANT_MATERIAL_LIST = new ArrayList<>() {{
        add(Material.DIRT);
        add(Material.PODZOL);
        add(Material.GRASS_BLOCK);
    }};

    private static final HashMap<Material, Material> REPLANT_MAP = new HashMap<>() {{
        put(Material.OAK_LOG, Material.OAK_SAPLING);
        put(Material.SPRUCE_LOG, Material.SPRUCE_SAPLING);
        put(Material.BIRCH_LOG, Material.BIRCH_SAPLING);
        put(Material.JUNGLE_LOG, Material.JUNGLE_SAPLING);
        put(Material.ACACIA_LOG, Material.ACACIA_SAPLING);
        put(Material.DARK_OAK_LOG, Material.DARK_OAK_SAPLING);
        put(Material.CRIMSON_STEM, Material.CRIMSON_FUNGUS);
        put(Material.WARPED_STEM, Material.WARPED_FUNGUS);
        put(Material.CHERRY_LOG, Material.CHERRY_SAPLING);
        put(Material.MANGROVE_LOG, Material.MANGROVE_PROPAGULE);
    }};

    private final ConfigManager configManager;
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    public TitanAxe(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Response<List<String>> titanToolValidationCheckResponse = titanToolBlockBreakValidation(event, ItemInfo.ALLOWED_AXE_TYPES);
        //Intentionally swallowing the error here since this is trigger on a block break validation, would result in tons of logging noise.
        if (titanToolValidationCheckResponse.error() != null) {
            if (configManager.getDebug()) {
                Bukkit.getConsoleSender().sendMessage(titanToolValidationCheckResponse.error());
            }
            return;
        }

        Block blockBroken = event.getBlock();
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        boolean hasChargeLore = ItemInfo.hasChargeLore(titanToolValidationCheckResponse.value(), true);

        if (hasChargeLore) {
            Response<Integer> getChargeResponse = ItemInfo.getCharge(titanToolValidationCheckResponse.value(), true, hasChargeLore, 39);
            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return;
            }
            ChargeManagement.decreaseChargeLore(itemInMainHand, titanToolValidationCheckResponse.value(), true, hasChargeLore, player);
        }

        for (Block block : getSphereBlocks(blockBroken.getLocation(), 5, false)) {
            if (block.getLocation().equals(blockBroken.getLocation())) {
                continue;
            }
            if (configManager.getAllowedAxeBlocks().contains(block.getType())) {
                IGNORE_LOCATIONS.add(block.getLocation());
                BlockBreakEvent e = new BlockBreakEvent(block, player);
                Bukkit.getPluginManager().callEvent(e);
                if (!e.isCancelled()) {
                    Block blockBelow = block.getLocation().subtract(0, 1, 0).getBlock();
                    if (REPLANT_MATERIAL_LIST.contains(blockBelow.getType())) {
                        if (REPLANT_MAP.containsKey(block.getType())) {
                            block.setType(REPLANT_MAP.get(block.getType()));
                        }
                        continue;
                    }
                    block.breakNaturally(itemInMainHand);
                }
            }
        }
    }

}
