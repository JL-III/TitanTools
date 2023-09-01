package com.nessxxiii.titanenchants.listeners.enchantments;

import com.nessxxiii.titanenchants.config.ConfigManager;
import com.nessxxiii.titanenchants.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import com.playtheatria.jliii.generalutils.enums.ToolStatus;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
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

import static com.nessxxiii.titanenchants.config.ConfigManager.blockConversionTypes;
import static com.nessxxiii.titanenchants.util.Utils.*;

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
        if (!TitanItem.isAllowedType(event.getPlayer().getInventory().getItemInMainHand(), TitanItem.ALLOWED_PICK_TYPES)) return;
        Response<List<String>> loreListResponse = TitanItem.getLore(event.getPlayer().getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            return;
        }
        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        if (!isTitanTool) return;
        Response<ToolStatus> toolStatusResponse = TitanItem.getStatus(loreListResponse.value(), isTitanTool);
        if (toolStatusResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
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
        boolean hasChargeLore = TitanItem.hasChargeLore(loreListResponse.value(), isTitanTool);

        if (hasChargeLore) {
            Response<Integer> getChargeResponse = TitanItem.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);
            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return;
            }
            ChargeManagement.decreaseChargeLore(itemInMainHand, loreListResponse.value(), true, hasChargeLore, player);
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
