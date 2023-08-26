package com.nessxxiii.titanenchants.listeners.enchantments;

import com.nessxxiii.titanenchants.config.ConfigManager;
import com.nessxxiii.titanenchants.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titanenchants.listeners.enchantmentManagement.ToggleAncientPower;
import com.nessxxiii.titanenchants.util.Utils;
import com.playtheatria.jliii.generalutils.enums.ToolStatus;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class TitanAxe implements Listener {

    private final ConfigManager configManager;
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();

    public TitanAxe(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        if (!TitanItem.isAllowedType(event.getPlayer().getInventory().getItemInMainHand(), TitanItem.ALLOWED_AXE_TYPES)) return;
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

        Response<Boolean> hasChargeResponse = TitanItem.hasCharge(loreListResponse.value(), isTitanTool);
        if (hasChargeResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(hasChargeResponse.error());
            return;
        }

        Response<Boolean> isImbuedTitanToolResponse = TitanItem.isImbuedTitanTool(loreListResponse.value(), isTitanTool);
        if (isImbuedTitanToolResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(isImbuedTitanToolResponse.error());
            return;
        }

        if (hasChargeResponse.value()) {
            Response<Integer> getChargeResponse = TitanItem.getCharge(loreListResponse.value(), isTitanTool, hasChargeResponse, 39);
            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return;
            }

            ChargeManagement.decreaseChargeLore(itemInMainHand, player, 2);
        }

        for (Block block : getNearbyBlocks(blockBroken.getLocation(), 5, false)) {
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

    private void updateInventoryWithAllDropsFromBlockbreak(Player player, ItemStack itemInMainHand, Block block) {
        Collection<ItemStack> dropsCollection = block.getDrops(itemInMainHand);
        for (ItemStack itemStack : dropsCollection) {
            updatePlayerInventory(player, itemStack);
        }
    }


    private void updatePlayerInventory(Player player, ItemStack drops) {
        player.getInventory().addItem(drops);
        player.updateInventory();
    }

    private int calculateExperienceAmount(Material material) {

        int experience = switch (material) {
            case COAL_ORE, DEEPSLATE_COAL_ORE -> getRandomNumber(0, 2);
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> getRandomNumber(3, 7);
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE, NETHER_QUARTZ_ORE -> getRandomNumber(2, 5);
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> getRandomNumber(1, 5);
            case IRON_ORE, DEEPSLATE_IRON_ORE, GOLD_ORE, DEEPSLATE_GOLD_ORE, NETHER_GOLD_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE -> 6;
            default -> 0;
        };

        return experience;
    }

    public static List<Block> getNearbyBlocks(Location location, int radius, boolean hollow) {
        List<Block> circleBlocks = new ArrayList<>();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
                        circleBlocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return circleBlocks;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

}
