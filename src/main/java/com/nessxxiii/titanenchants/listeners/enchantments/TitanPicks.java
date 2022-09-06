package com.nessxxiii.titanenchants.listeners.enchantments;

import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ChargeManagement;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.util.*;
import org.bukkit.Bukkit;
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

import java.util.*;

public class TitanPicks implements Listener {
    public static final Set<Material> ALLOWED_ITEMS = new HashSet<>();
    public static final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();
    private static final Set<Location> IGNORE_LOCATIONS = new HashSet<>();
    private static final TitanEnchantEffects EFFECTS = new TitanEnchantEffects();
    private static final HashMap<Material, Integer> blockConversionQuantity = new HashMap<>(){{
        put(Material.EMERALD_ORE, 6);
        put(Material.DEEPSLATE_EMERALD_ORE, 6);
        put(Material.IRON_ORE, 6);
        put(Material.DEEPSLATE_IRON_ORE, 6);
        put(Material.COPPER_ORE, 20);
        put(Material.DEEPSLATE_COPPER_ORE, 20);
        put(Material.GOLD_ORE, 6);
        put(Material.DEEPSLATE_GOLD_ORE, 6);
        put(Material.NETHER_GOLD_ORE, 3);
    }};
    private static final HashMap<Material, Material> blockConversionTypes = new HashMap<>() {{
        put(Material.EMERALD_ORE, Material.EMERALD);
        put(Material.DEEPSLATE_EMERALD_ORE, Material.EMERALD);
        put(Material.IRON_ORE, Material.IRON_INGOT);
        put(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT);
        put(Material.COPPER_ORE, Material.COPPER_INGOT);
        put(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT);
        put(Material.GOLD_ORE, Material.GOLD_INGOT);
        put(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT);
        put(Material.NETHER_GOLD_ORE, Material.GOLD_INGOT);
    }};

    private final Plugin PLUGIN;

    public TitanPicks(Plugin plugin) {
        this.PLUGIN = plugin;
        loadConfig();
    }

    @EventHandler
    @SuppressWarnings("unused")
    public void onBlockBreakEvent(BlockBreakEvent event) {
        if (!ItemInfo.isChargedOrImbuedTitanPick(event.getPlayer().getInventory().getItemInMainHand())) return;
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
        Block blockBroken = event.getBlock();
        Material blockBrokenMaterial = blockBroken.getType();
        Inventory inventory = player.getInventory();
        ItemStack drops = new ItemStack(blockBrokenMaterial);
//        Collection<ItemStack> dropsCollection1 = blockBroken.getDrops(itemInMainHand);
//        if (!dropsCollection1.isEmpty()) {
//            player.sendMessage("Amount: " + dropsCollection1);
//        }
        if (IGNORE_LOCATIONS.contains(blockBroken.getLocation())) {
            IGNORE_LOCATIONS.remove(blockBroken.getLocation());
            return;
        }
        int itemLevel = ItemInfo.getItemLevel(itemInMainHand);

        switch (itemLevel) {
            case 1 -> {
                if (inventory.firstEmpty() == -1) {
                    ToggleAncientPower.handleFullInventory(itemInMainHand, player, ItemInfo.isImbued(itemInMainHand), 1);
                }
                if (ItemInfo.isCharged(itemInMainHand)) {
                    ChargeManagement.decreaseChargeLore(itemInMainHand, player, 1);
                }

                if (!itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                    if(blockConversionTypes.containsKey(blockBrokenMaterial)) {
                        blockBroken.setType(Material.AIR);
                        event.setCancelled(true);
                        EFFECTS.playSmeltVisualAndSoundEffect(player, blockBroken.getLocation());
                        drops = new ItemStack(blockConversionTypes.get(blockBrokenMaterial), blockConversionQuantity.get(blockBrokenMaterial));
                        inventory.addItem(drops);
                        player.updateInventory();
                    } else {
                        Collection<ItemStack> itemDrops = blockBroken.getDrops(itemInMainHand);
                        blockBroken.setType(Material.AIR);
                        player.sendMessage("Number of drops in blockBroken: " + itemDrops.size());
                        for (ItemStack itemStack : itemDrops) {
                            inventory.addItem(itemStack);
                            player.updateInventory();
                        }
                    }
                    dropExperience(blockBroken);
                } else {
                    blockBroken.setType(Material.AIR);
                    event.setCancelled(true);
                    inventory.addItem(drops);
                    player.updateInventory();
                }

            }
            case 2 -> {
                if (ItemInfo.isCharged(itemInMainHand)) {
                    ChargeManagement.decreaseChargeLore(itemInMainHand, player, 2);
                }
                if (!itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                    for (Block block : getNearbyBlocks(blockBroken.getLocation())) {
                        if (ALLOWED_ITEMS.contains(block.getType())) {
                            IGNORE_LOCATIONS.add(block.getLocation());
                            BlockBreakEvent e = new BlockBreakEvent(block, player);
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                if(blockConversionTypes.containsKey(block.getType())) {
                                    EFFECTS.playSmeltVisualAndSoundEffect(player, block.getLocation());
                                    drops = new ItemStack(blockConversionTypes.get(block.getType()), blockConversionQuantity.get(block.getType()));
                                    block.setType(Material.AIR);
                                    player.getLocation().getWorld().dropItemNaturally(block.getLocation(), drops);
                                } else {
                                    block.breakNaturally(player.getInventory().getItemInMainHand());
                                }
                            }
                        }
                    }
                } else {
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
                }

            }
            case 3 -> {
                if (player.getInventory().firstEmpty() == -1) {
                    ToggleAncientPower.handleFullInventory(itemInMainHand, player, ItemInfo.isImbued(itemInMainHand), 3);
                }
                if (ItemInfo.isCharged(itemInMainHand)) {
                    ChargeManagement.decreaseChargeLore(itemInMainHand, player, 3);
                }
                if (!itemInMainHand.containsEnchantment(Enchantment.SILK_TOUCH)) {
                    for (Block block : getNearbyBlocks(blockBroken.getLocation())) {
                        if (ALLOWED_ITEMS.contains(block.getType())) {
                            IGNORE_LOCATIONS.add(block.getLocation());
                            BlockBreakEvent e = new BlockBreakEvent(block, player);
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                if(blockConversionTypes.containsKey(block.getType())) {
                                    drops = new ItemStack(blockConversionTypes.get(block.getType()), blockConversionQuantity.get(block.getType()));
                                    EFFECTS.playSmeltVisualAndSoundEffect(player, block.getLocation());
                                    inventory.addItem(drops);
                                    player.updateInventory();
                                } else {
                                    Collection<ItemStack> dropsCollection = block.getDrops(itemInMainHand);
                                    for (ItemStack itemStack : dropsCollection) {
                                        inventory.addItem(itemStack);
                                        player.updateInventory();
                                    }
                                }
                                block.setType(Material.AIR);
                            }
                        }
                    }
                } else {
                    for (Block block : getNearbyBlocks(blockBroken.getLocation())) {
                        if (ALLOWED_ITEMS.contains(block.getType())) {
                            IGNORE_LOCATIONS.add(block.getLocation());
                            BlockBreakEvent e = new BlockBreakEvent(block, player);
                            Bukkit.getPluginManager().callEvent(e);
                            if (!e.isCancelled()) {
                                Collection<ItemStack> dropsCollection = block.getDrops(itemInMainHand);
                                for (ItemStack itemStack : dropsCollection) {
                                    inventory.addItem(itemStack);
                                    player.updateInventory();
                                }
                            }
                            block.setType(Material.AIR);
                        }
                    }
                }
            }
            default -> {
            }
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

    private double getFortuneAmount(double fortuneLevel) {
        return 1/(fortuneLevel + 2) + (fortuneLevel + 1)/2;
    }

}
