package com.nessxxiii.titanenchants.enchantments;

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
        if (event.isCancelled()) return;
        if (!ItemInfo.isAllowedType(event.getPlayer().getInventory().getItemInMainHand())) return;
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)) {
            Block blockBroken = event.getBlock();
            Location initialBlockBrokenLocation = blockBroken.getLocation();
            if (IGNORE_LOCATIONS.contains(event.getBlock().getLocation())) {
                IGNORE_LOCATIONS.remove(event.getBlock().getLocation());
                return;
            }
            if (!ItemInfo.isTitanTool(item)) return;
            if (!ItemInfo.isActiveImbued(item) && !ItemInfo.isActiveCharged(item)) return;
            if (ItemInfo.isLevelOne(item)) {
                if (player.getInventory().firstEmpty() == -1) {
                    if (!ItemInfo.isImbued(item)) {
                        ChargeManagement.decreaseChargeLore(item, player);
                        player.sendMessage("decreasing charge");
                    }
                    handleFullInventory(player, item);
                    Material material = blockBroken.getType();
                    ItemStack drops = new ItemStack(material);
                    Inventory inv = player.getInventory();
                    blockBroken.setType(Material.AIR);
                    event.setCancelled(true);
                    inv.addItem(drops);
                    player.updateInventory();
                }
            } else if (ItemInfo.isLevelTwo(item)) {
                player.sendMessage("decreasing charge");
                ChargeManagement.decreaseChargeLore2(item, player);
                for (Block block : getNearbyBlocks(initialBlockBrokenLocation)) {
                    if (block.getLocation().equals(initialBlockBrokenLocation)) {
                        continue;
                    }
                    if (ALLOWED_ITEMS.contains(block.getType())) {
                        IGNORE_LOCATIONS.add(block.getLocation());
                        BlockBreakEvent e = new BlockBreakEvent(block, player);
                        Bukkit.getPluginManager().callEvent(e);
                        if (!e.isCancelled()) {
                            block.breakNaturally(item);
                        }
                    }
                }
            } else if (ItemInfo.isLevelThree(item)) {
                if (player.getInventory().firstEmpty() == -1) {
                    handleFullInventory(player, item);
                }
                player.sendMessage("decreasing charge");
                ChargeManagement.decreaseChargeLore3(item, player);
                Material material = blockBroken.getType();
                ItemStack drops = new ItemStack(material);
                Inventory inv = player.getInventory();
                blockBroken.setType(Material.AIR);
                event.setCancelled(true);
                inv.addItem(drops);
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
                            material = block.getType();
                            drops = new ItemStack(material);
                            block.setType(Material.AIR);
                            inv.addItem(drops);
                            player.updateInventory();
                        }
                    }
                }
            }
        } else if (!item.containsEnchantment(Enchantment.SILK_TOUCH)) {

            if (event.isCancelled()) return;
            Block blockBroken = event.getBlock();
            Location initBlockLoc = blockBroken.getLocation();
            if (IGNORE_LOCATIONS.contains(event.getBlock().getLocation())) {
                IGNORE_LOCATIONS.remove(event.getBlock().getLocation());
                return;
            }
            if (!ItemInfo.isTitanTool(item)) return;
            if (!ItemInfo.isActiveImbued(item) && !ItemInfo.isActiveCharged(item)) return;
            if (ItemInfo.isLevelOne(item)) {
                if (player.getInventory().firstEmpty() == -1) {
                    handleFullInventory(player, item);
                }
                /*            player.sendMessage("decreasing charge");*/
                ChargeManagement.decreaseChargeLore(item, player);
                Material material = blockBroken.getType();
                ItemStack drops = new ItemStack(material);
                Inventory inv = player.getInventory();
                blockBroken.setType(Material.AIR);
                event.setCancelled(true);
                inv.addItem(drops);
                player.updateInventory();
            } else if (ItemInfo.isLevelTwo(item)) {
                /*            player.sendMessage("decreasing charge");*/
                ChargeManagement.decreaseChargeLore2(item, player);
                for (Block block : getNearbyBlocks(initBlockLoc)) {
                    if (block.getLocation().equals(initBlockLoc)) {
                        continue;
                    }
                    if (ALLOWED_ITEMS.contains(block.getType())) {
                        IGNORE_LOCATIONS.add(block.getLocation());
                        BlockBreakEvent e = new BlockBreakEvent(block, player);
                        Bukkit.getPluginManager().callEvent(e);
                        if (!e.isCancelled()) {
                            block.breakNaturally(item);
                        }
                    }
                }
            } else if (ItemInfo.isLevelThree(item)) {

                if (player.getInventory().firstEmpty() == -1) {
                    handleFullInventory(player, item);
                }

                /*            player.sendMessage("decreasing charge");*/
                ChargeManagement.decreaseChargeLore3(item, player);
                Material material = blockBroken.getType();
                ItemStack drops = new ItemStack(material);
                Inventory inv = player.getInventory();
                blockBroken.setType(Material.AIR);
                event.setCancelled(true);
                inv.addItem(drops);
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
                            material = block.getType();
                            drops = new ItemStack(material);
                            block.setType(Material.AIR);
                            inv.addItem(drops);
                            player.updateInventory();
                        }
                    }
                }
            }
        }
    }

    private void handleFullInventory(Player player, ItemStack item) {
        if (ItemInfo.isActiveCharged(item)) {
            ToggleAncientPower.disableEnchant(item);
            new TitanEnchantEffects().depletedChargeEffect(player);
            player.sendMessage(ChatColor.RED + "Enchantment deactivated: your inv is full");
        } else if (ItemInfo.isActiveImbued(item)) {
            ToggleAncientPower.disableImbuedEnchant(item);
            new TitanEnchantEffects().depletedChargeEffect(player);
            player.sendMessage(ChatColor.RED + "Enchantment deactivated: your inv is full");
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
