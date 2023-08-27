package com.nessxxiii.titanenchants.listeners.enchantments;

import com.nessxxiii.titanenchants.config.ConfigManager;
import com.nessxxiii.titanenchants.listeners.enchantmentManagement.ChargeManagement;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.nessxxiii.titanenchants.util.Utils.getSphereBlocks;
import static com.nessxxiii.titanenchants.util.Utils.titanToolBlockBreakValidation;

public class TitanAxe implements Listener {

    private final ConfigManager configManager;
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    public TitanAxe(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Response<List<String>> titanToolValidationCheckResponse = titanToolBlockBreakValidation(event, TitanItem.ALLOWED_AXE_TYPES);
        //Intentionally swallowing the error here since this is trigger on a block break validation, would result in tons of logging noise.
        if (titanToolValidationCheckResponse.error() != null) return;

        Block blockBroken = event.getBlock();
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

        boolean hasChargeLore = TitanItem.hasChargeLore(titanToolValidationCheckResponse.value(), true);

        if (hasChargeLore) {
            Response<Integer> getChargeResponse = TitanItem.getCharge(titanToolValidationCheckResponse.value(), true, hasChargeLore, 39);
            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return;
            }
            ChargeManagement.decreaseChargeLore(itemInMainHand, player, 2);
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
                    block.breakNaturally(itemInMainHand);
                }
            }
        }
    }

}
