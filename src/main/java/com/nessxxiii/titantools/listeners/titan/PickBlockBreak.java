package com.nessxxiii.titantools.listeners.titan;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.events.titan.PickBlockBreakEvent;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.TitanEnchantEffects;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.nessxxiii.titantools.config.ConfigManager.blockConversionQuantity;
import static com.nessxxiii.titantools.config.ConfigManager.blockConversionTypes;

public class PickBlockBreak implements Listener {
    private final ConfigManager configManager;
    private final Debugger debugger;
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    private final Material cooldownMaterial = Material.BARRIER;

    public PickBlockBreak(ConfigManager configManager, Debugger debugger) {
        this.configManager = configManager;
        this.debugger = debugger;
        configManager.loadConfig();
    }

    // our main goals - remove blocks from the world and either drop them on the ground or add them to the players inventory
    // we need to gather the drops from the block break event based on silk or not then
    // check if the pickup is cancelled and if so, break the block naturally - otherwise add the item to the players inventory

    @EventHandler
    public void onBlockBreakEvent(PickBlockBreakEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "Titan Pick Break Event fired");
//        if (event.isCancelled()) return;
//        if (!ItemInfo.isAllowedType(event.getPlayer().getInventory().getItemInMainHand(), ItemInfo.ALLOWED_PICK_TYPES)) {
//            debugger.sendDebugIfEnabled("Not a Titan Pick");
//            return;
//        }
//        Response<List<String>> loreListResponse = ItemInfo.getLore(event.getPlayer().getInventory().getItemInMainHand());
//        if (loreListResponse.error() != null) {
//            debugger.sendDebugIfEnabled(loreListResponse.error());
//            return;
//        }
//        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
//        // Return an optional snapshot of the itemstack- if not a titan tool return.
//        if (!isTitanTool) return;
//        Response<ToolStatus> toolStatusResponse = ItemInfo.getStatus(loreListResponse.value(), isTitanTool);
//        if (toolStatusResponse.error() != null) {
//            debugger.sendDebugIfEnabled(toolStatusResponse.error());
//            return;
//        }
//        if (toolStatusResponse.value() == ToolStatus.OFF) return;
//        Block blockBroken = event.getBlock();
//        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
//            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
//            return;
//        }
//        Player player = event.getPlayer();
//        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
//        boolean hasChargeLore = ItemInfo.hasChargeLore(loreListResponse.value(), isTitanTool);
//
//        if (hasChargeLore) {
//            Response<Integer> getChargeResponse = ItemInfo.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);
//            if (getChargeResponse.error() != null) {
//                debugger.sendDebugIfEnabled(getChargeResponse.error());
//                return;
//            }
//            ChargeManagement.decreaseChargeLore(debugger, itemInMainHand, loreListResponse.value(), isTitanTool, hasChargeLore, player);
//        }
//
//        int aggregateAmount = 0;
//        for (Block currentBlock : getCubeBlocks(blockBroken.getLocation())) {
//            IGNORE_LOCATIONS.add(currentBlock.getLocation());
//            if (!configManager.getAllowedPickBlocks().contains(currentBlock.getType())) continue;
//            if (simulateBlockBreakEventIsCancelled(currentBlock, player)) continue;
//            //iterate through drops of current block
//            for (ItemStack drop : getDropsProcessed(currentBlock, player)) {
//                Item itemEntity = currentBlock.getWorld().dropItemNaturally(currentBlock.getLocation(), drop);
//                if (simulateItemPickupIsCancelled(itemEntity, player)) continue;
//                if (player.getInventory().firstEmpty() == -1) {
//                    if (!player.hasCooldown(cooldownMaterial)) {
//                        player.sendMessage(ChatColor.RED + "Your inventory is full, items are not getting placed into inventory!");
//                        player.setCooldown(cooldownMaterial, 500);
//                    }
//                    continue;
//                };
//                itemEntity.remove();
//                player.getInventory().addItem(drop);
//            }
//            currentBlock.setType(Material.AIR);
//            aggregateAmount = aggregateAmount + calculateExperienceAmount(currentBlock.getType());
//        }
//        if (aggregateAmount > 0) {
//            blockBroken.getLocation().getWorld().spawn(blockBroken.getLocation(), ExperienceOrb.class).setExperience(aggregateAmount);
//        }

    }

    private List<ItemStack> getDropsProcessed(Block currentBlock, Player player) {
        if (blockConversionTypes.containsKey(currentBlock.getType()) && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            TitanEnchantEffects.playSmeltVisualAndSoundEffect(player, currentBlock.getLocation());
            return new ArrayList<>(){{
                add(new ItemStack(blockConversionTypes.get(currentBlock.getType()), blockConversionQuantity.get(currentBlock.getType())));
            }};
        } else {
            return currentBlock.getDrops(player.getInventory().getItemInMainHand()).stream().toList();
        }
    }

    private boolean simulateBlockBreakEventIsCancelled(Block block, Player player) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(blockBreakEvent);
        return blockBreakEvent.isCancelled();
    }

    private boolean simulateItemPickupIsCancelled(Item item, Player player) {
        EntityPickupItemEvent playerPickupItemEvent = new EntityPickupItemEvent(player, item, (Math.max(item.getItemStack().getAmount() - 1, 0)));
        Bukkit.getPluginManager().callEvent(playerPickupItemEvent);
        return playerPickupItemEvent.isCancelled();
    }
}
