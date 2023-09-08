package com.nessxxiii.titanenchants.config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final Plugin plugin;
    private final Set<Material> allowedPickBlocks = new HashSet<>();
    private final Set<Material> allowedAxeBlocks = new HashSet<>();
    private boolean debug;

    public static final HashMap<Material, Integer> blockConversionQuantity = new HashMap<>(){{
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

    public static final HashMap<Material, Material> blockConversionTypes = new HashMap<>() {{
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

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        setAllowedBlocks("titanPick", allowedPickBlocks);
        setAllowedBlocks("titanAxe", allowedAxeBlocks);
        setDebugValue();
    }

    public void setAllowedBlocks(String targetSection, Set<Material> targetList) {
        ConfigurationSection trench = plugin.getConfig().getConfigurationSection(targetSection);
        if (trench == null) {
            plugin.getLogger().warning(targetSection + " configuration section not found!");
            return;
        }

        List<String> items = trench.getStringList("destroyable-items");

        if (items.size() == 0) {
            plugin.getLogger().warning("No destroyable-items found in trench section of config.");
        }

        for (String item : items) {
            try {
                targetList.add(Material.valueOf(item));
            } catch (Exception e) {
                plugin.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
        Bukkit.getConsoleSender().sendMessage("Loaded " + targetList.size() + " items for " + targetSection);
    }

    public void setDebugValue() {
        debug = plugin.getConfig().getBoolean("debug");
        Bukkit.getConsoleSender().sendMessage("Debug mode is set to " + debug);
    }

    public Set<Material> getAllowedPickBlocks() {
        return allowedPickBlocks;
    }

    public Set<Material> getAllowedAxeBlocks() { return allowedAxeBlocks; }

    public boolean getDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }
}
