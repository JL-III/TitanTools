package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.TitanEnchantEffects;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

import static com.nessxxiii.titantools.config.ConfigManager.blockConversionTypes;
import static com.nessxxiii.titantools.util.Utils.*;

public class TitanPicks implements Listener {
    private final ConfigManager configManager;

    public TitanPicks(ConfigManager configManager) {
        this.configManager = configManager;
        configManager.loadConfig();
    }
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    @EventHandler
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!ItemInfo.isAllowedType(event.getPlayer().getInventory().getItemInMainHand(), ItemInfo.ALLOWED_PICK_TYPES)) {
            if (configManager.getDebug()) {
                Bukkit.getConsoleSender().sendMessage("Not a Titan Pick");
            }
            return;
        }
        Response<List<String>> loreListResponse = ItemInfo.getLore(event.getPlayer().getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            if (configManager.getDebug()) {
                Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            }
            return;
        }
        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        // Return an optional snapshot of the itemstack- if not a titan tool return.
        if (!isTitanTool) return;
        Response<ToolStatus> toolStatusResponse = ItemInfo.getStatus(loreListResponse.value(), isTitanTool);
        if (toolStatusResponse.error() != null) {
            if (configManager.getDebug()) {
                Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
            }
            return;
        }
        if (toolStatusResponse.value() == ToolStatus.OFF) return;
        Block blockBroken = event.getBlock();
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        boolean hasChargeLore = ItemInfo.hasChargeLore(loreListResponse.value(), isTitanTool);

        if (hasChargeLore) {
            Response<Integer> getChargeResponse = ItemInfo.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);
            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return;
            }
            ChargeManagement.decreaseChargeLore(itemInMainHand, loreListResponse.value(), isTitanTool, hasChargeLore, player);
        }

        if (!itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
            int aggregateAmount = 0;
            for (Block currentBlock : getCubeBlocks(blockBroken.getLocation())) {
                if (configManager.getAllowedPickBlocks().contains(currentBlock.getType())) {
                    IGNORE_LOCATIONS.add(currentBlock.getLocation());
                    BlockBreakEvent e = new BlockBreakEvent(currentBlock, player);
                    Bukkit.getPluginManager().callEvent(e);
                    Material blockBrokenMaterial = currentBlock.getType();
                    if (!e.isCancelled()) {
                        if(blockConversionTypes.containsKey(blockBrokenMaterial)) {
                            TitanEnchantEffects.playSmeltVisualAndSoundEffect(player, currentBlock.getLocation());
                            currentBlock.setType(Material.AIR);
                            player.getLocation().getWorld().dropItemNaturally(currentBlock.getLocation(), getDropsFromConversionTable(blockBrokenMaterial));
                            aggregateAmount = aggregateAmount + calculateExperienceAmount(blockBrokenMaterial);
                        } else {
                            currentBlock.breakNaturally(itemInMainHand);
                        }
                        aggregateAmount = aggregateAmount + calculateExperienceAmount(blockBrokenMaterial);
                    }
                }
            }
            if (aggregateAmount > 0) {
                blockBroken.getLocation().getWorld().spawn(blockBroken.getLocation(), ExperienceOrb.class).setExperience(aggregateAmount);
            }
        } else {
            for (Block block : getCubeBlocks(blockBroken.getLocation())) {
                if (block.getLocation().equals(blockBroken.getLocation())) {
                    continue;
                }
                if (configManager.getAllowedPickBlocks().contains(block.getType())) {
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
}
