package com.nessxxiii.titanenchants.listeners.enchantments;

import com.nessxxiii.titanenchants.Items.ItemInfo;
import com.nessxxiii.titanenchants.enchantmentManager.ChargeManagement;
import com.nessxxiii.titanenchants.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TitanPicks implements Listener {
    public static final Set<Material> ALLOWED_ITEMS = new HashSet<>();
    public static final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    private final Plugin PLUGIN;

    public TitanPicks(Plugin plugin) {
        this.PLUGIN = plugin;
        loadConfig();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onBlockBreakEvent(BlockBreakEvent event) {
        //Initial validation, does this event even need to continue? if not return
        if (!ItemInfo.isChargedOrImbuedTitanPick(event.getPlayer().getInventory().getItemInMainHand())) return;
        if (event.isCancelled()) return;

        //assign all the variables that will be used throughout the enchantment
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        Block blockBroken = event.getBlock();
        Material blockBrokenMaterial = blockBroken.getType();
        Inventory inventory = player.getInventory();
        ItemStack drops = new ItemStack(blockBrokenMaterial);

        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        if (ItemInfo.isLevelOne(itemInMainHand)) {
            if (inventory.firstEmpty() == -1) {
                handleFullInventory(player, itemInMainHand);
            }
            if (ItemInfo.isCharged(itemInMainHand)) {
                ChargeManagement.decreaseChargeLore(itemInMainHand, player, 1);
                player.sendMessage("Debug: decreasing charge");
            }
            if (!itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                dropExperience(blockBroken);
            }
            blockBroken.setType(Material.AIR);
            event.setCancelled(true);
            inventory.addItem(drops);
            player.updateInventory();
        } else if (ItemInfo.isLevelTwo(itemInMainHand)) {
            if (ItemInfo.isCharged(itemInMainHand)) {
                player.sendMessage("decreasing charge");
                ChargeManagement.decreaseChargeLore(itemInMainHand, player, 2);
            }
            for (Block block : getNearbyBlocks(blockBroken.getLocation())) {
                if (block.getLocation().equals(blockBroken.getLocation())) {
                    continue;
                }
                if (ALLOWED_ITEMS.contains(block.getType())) {
                    IGNORE_LOCATIONS.add(block.getLocation());
                    BlockBreakEvent e = new BlockBreakEvent(block, player);
                    Bukkit.getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        block.breakNaturally(itemInMainHand);
                    }
                }
            }
        } else if (ItemInfo.isLevelThree(itemInMainHand)) {
            if (player.getInventory().firstEmpty() == -1) {
                handleFullInventory(player, itemInMainHand);
            }
            if (ItemInfo.isCharged(itemInMainHand)) {
                player.sendMessage("decreasing charge");
                ChargeManagement.decreaseChargeLore(itemInMainHand, player, 3);
            }
            if (!itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                dropExperience(blockBroken);
            }
            blockBroken.setType(Material.AIR);
            event.setCancelled(true);
            inventory.addItem(drops);
            player.updateInventory();
            for (Block block : getNearbyBlocks(event.getBlock().getLocation())) {
                if (block.getLocation().equals(event.getBlock().getLocation())) {
                    continue;
                }
                if (ALLOWED_ITEMS.contains(block.getType())) {
                    IGNORE_LOCATIONS.add(block.getLocation());
                    BlockBreakEvent e = new BlockBreakEvent(block, event.getPlayer());
                    Bukkit.getPluginManager().callEvent(e);
                    if (!e.isCancelled()) {
                        block.setType(Material.AIR);
                        inventory.addItem(drops);
                        player.updateInventory();
                    }
                }
            }
        }

    }

    private void handleFullInventory(Player player, ItemStack item) {
        if (ItemInfo.isActiveCharged(item)) {
            ToggleAncientPower.disableEnchant(item);
            new TitanEnchantEffects().depletedChargeEffect(player);
            player.sendMessage(ChatColor.RED + "AncientPower deactivated: your inventory is full");
        } else if (ItemInfo.isActiveImbued(item)) {
            ToggleAncientPower.disableImbuedEnchant(item);
            new TitanEnchantEffects().depletedChargeEffect(player);
            player.sendMessage(ChatColor.RED + "AncientPower deactivated: your inventory is full");
        }
    }

    public void loadConfig() {
        ENCHANTABLE_ITEMS.clear();
        ConfigurationSection trench = PLUGIN.getConfig().getConfigurationSection("trench");
        if (trench == null) {
            PLUGIN.getLogger().warning("Trench configuration not found!");
            return;
        }

        List<String> items = trench.getStringList("destroyable-items");

        if (items.size() == 0) {
            PLUGIN.getLogger().warning("No destroyable-items found in trench section of config.");
        }

        for (String item : items) {
            try {
                ALLOWED_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                PLUGIN.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }

        List<String> enchantableItems = trench.getStringList("enchantable-items");

        if (items.size() == 0) {
            PLUGIN.getLogger().warning("No enchantable-items found in trench section of config.");
        }

        for (String item : enchantableItems) {
            try {
                ENCHANTABLE_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                PLUGIN.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }

    private void dropExperience(Block block) {
        int experience = switch (block.getType()) {
            case COAL_ORE -> getRandomNumber(0, 2);
            case NETHER_GOLD_ORE -> getRandomNumber(0, 1);
            case DIAMOND_ORE, EMERALD_ORE -> getRandomNumber(3, 7);
            case LAPIS_LAZULI, NETHER_QUARTZ_ORE -> getRandomNumber(2, 5);
            case REDSTONE_ORE -> getRandomNumber(1, 5);
            default -> 0;
        };

        if (experience > 0) {
            block.getWorld().spawn(block.getLocation(), ExperienceOrb.class).setExperience(5);
        }
    }

    private static List<Block> getNearbyBlocks(Location location) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    private int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
