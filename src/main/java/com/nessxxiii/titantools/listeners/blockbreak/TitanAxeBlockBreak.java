package com.nessxxiii.titantools.listeners.blockbreak;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.events.TitanAxeBlockBreakEvent;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.*;

public class TitanAxeBlockBreak implements Listener {

    private final ConfigManager configManager;

    private final Debugger debugger;

    public TitanAxeBlockBreak(ConfigManager configManager, Debugger debugger) {
        this.configManager = configManager;
        this.debugger = debugger;
    }

    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    // so by the time we have gotten here, we have already checked if the item is a titan axe

    @EventHandler
    public void onTitanAxeBreak(TitanAxeBlockBreakEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "Titan Axe Break Event fired");
//        Response<List<String>> titanToolValidationCheckResponse = titanToolBlockBreakValidation(event, ItemInfo.ALLOWED_AXE_TYPES);
//        //Intentionally swallowing the error here since this is trigger on a block break validation, would result in tons of logging noise.
//        if (titanToolValidationCheckResponse.error() != null) {
//            debugger.sendDebugIfEnabled("TitanAxe - onBlockBreak: " + titanToolValidationCheckResponse.error());
//            return;
//        }
//
//        Block blockBroken = event.getBlock();
//        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
//            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
//            return;
//        }
//        Player player = event.getPlayer();
//        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
//
//        boolean hasChargeLore = ItemInfo.hasChargeLore(titanToolValidationCheckResponse.value(), true);
//
//        if (hasChargeLore) {
//            Response<Integer> getChargeResponse = ItemInfo.getCharge(titanToolValidationCheckResponse.value(), true, hasChargeLore, 39);
//            if (getChargeResponse.error() != null) {
//                debugger.sendDebugIfEnabled("TitanAxe - getChargeResponse: " + getChargeResponse.error());
//                return;
//            }
//            ChargeManagement.decreaseChargeLore(debugger, itemInMainHand, titanToolValidationCheckResponse.value(), true, hasChargeLore, player);
//        }
//
//        for (Block block : getSphereBlocks(blockBroken.getLocation(), 5, false)) {
//            if (block.getLocation().equals(blockBroken.getLocation())) {
//                continue;
//            }
//            if (configManager.getAllowedAxeBlocks().contains(block.getType())) {
//                IGNORE_LOCATIONS.add(block.getLocation());
//                BlockBreakEvent e = new BlockBreakEvent(block, player);
//                if (!e.isCancelled()) {
//                    Block blockBelow = block.getLocation().subtract(0, 1, 0).getBlock();
//                    if (Utils.REPLANT_MATERIAL_LIST.contains(blockBelow.getType())) {
//                        if (Utils.REPLANT_MAP.containsKey(block.getType())) {
//                            block.setType(Utils.REPLANT_MAP.get(block.getType()));
//                        }
//                        continue;
//                    }
//                    block.breakNaturally(itemInMainHand);
//                }
//            }
//        }
    }
}
