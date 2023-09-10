package com.nessxxiii.titanenchants.config;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final Plugin plugin;
    private FileConfiguration fileConfiguration;
    private ItemStack titanPickRedFortune;
    private ItemStack titanPickRedSilk;
    private ItemStack titanPickYellowFortune;
    private ItemStack titanPickYellowSilk;
    private ItemStack titanPickBlueFortune;
    private ItemStack titanPickBlueSilk;
    private ItemStack titanShovelRed;
    private ItemStack titanAxeRed;
    private ItemStack titanAxeYellow;
    private ItemStack titanAxeBlue;
    private ItemStack titanSwordRed;
    private ItemStack titanSwordYellow;
    private ItemStack titanSwordBlue;
    private ItemStack titanRodRed;
    private ItemStack testTool;
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

    public void reload() {
        plugin.reloadConfig();
        loadConfig();
    }

    public void loadConfig() {
        fileConfiguration = plugin.getConfig();
        titanPickRedFortune = loadItemStack("titanpickredfortune");
        titanPickRedSilk = loadItemStack("titanpickredsilk");
        titanPickYellowFortune = loadItemStack("titanpickyellowfortune");
        titanPickYellowSilk = loadItemStack("titanpickyellowsilk");
        titanPickBlueFortune = loadItemStack("titanpickbluefortune");
        titanPickBlueSilk = loadItemStack("titanpickbluesilk");
        titanShovelRed = loadItemStack("titanshovelred");
        titanAxeRed = loadItemStack("titanaxered");
        titanAxeYellow = loadItemStack("titanaxeyellow");
        titanAxeBlue = loadItemStack("titanaxeblue");
        titanSwordRed = loadItemStack("titanswordred");
        titanSwordYellow = loadItemStack("titanswordyellow");
        titanSwordBlue = loadItemStack("titanswordblue");
        titanRodRed = loadItemStack("titanrodred");
        testTool = loadItemStack("testTool");
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

    private ItemStack loadItemStack(String target) {
        ItemStack targetItemstack = (ItemStack) fileConfiguration.get(target);
        if (targetItemstack != null) {
            Bukkit.getConsoleSender().sendMessage("Loaded " + target + " from the config.");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error loading " + target + " from the config. Check to ensure an entry exists!");
        }
        return targetItemstack;
    }

    public ItemStack getTitanPickRedFortune() {
        return titanPickRedFortune;
    }

    public ItemStack getTitanPickRedSilk() {
        return titanPickRedSilk;
    }

    public ItemStack getTitanPickYellowFortune() {
        return titanPickYellowFortune;
    }

    public ItemStack getTitanPickYellowSilk() {
        return titanPickYellowSilk;
    }

    public ItemStack getTitanPickBlueFortune() {
        return titanPickBlueFortune;
    }

    public ItemStack getTitanPickBlueSilk() {
        return titanPickBlueSilk;
    }

    public ItemStack getTitanShovelRed() {
        return titanShovelRed;
    }

    public ItemStack getTitanAxeRed() {
        return titanAxeRed;
    }

    public ItemStack getTitanAxeYellow() {
        return titanAxeYellow;
    }

    public ItemStack getTitanAxeBlue() {
        return titanAxeBlue;
    }

    public ItemStack getTitanSwordRed() {
        return titanSwordRed;
    }

    public ItemStack getTitanSwordYellow() {
        return titanSwordYellow;
    }

    public ItemStack getTitanSwordBlue() {
        return titanSwordBlue;
    }

    public ItemStack getTitanRodRed() {
        return titanRodRed;
    }

    public ItemStack getTestTool() { return testTool; }
}
