package com.nessxxiii.titantools.config;

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
    private final ItemStack titan_pick_red_fortune;
    private final ItemStack titan_pick_red_silk;
    private final ItemStack titan_pick_yellow_fortune;
    private final ItemStack titan_pick_yellow_silk;
    private final ItemStack titan_pick_blue_fortune;
    private final ItemStack titan_pick_blue_silk;
    private final ItemStack titan_shovel_red;
    private final ItemStack titan_axe_red;
    private final ItemStack titan_axe_yellow;
    private final ItemStack titan_axe_blue;
    private final ItemStack titan_sword_red;
    private final ItemStack titan_sword_yellow;
    private final ItemStack titan_sword_blue;
    private final ItemStack titan_rod_red;
    private ItemStack test_tool;
    private final Set<Material> allowedPickBlocks = new HashSet<>();
    private final Set<Material> allowedAxeBlocks = new HashSet<>();
    private final Set<Material> disallowedShovelItems = new HashSet<>();
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
        fileConfiguration = plugin.getConfig();
        titan_pick_red_fortune = loadItemStack("titan_pick_red_fortune");
        titan_pick_red_silk = loadItemStack("titan_pick_red_silk");
        titan_pick_yellow_fortune = loadItemStack("titan_pick_yellow_fortune");
        titan_pick_yellow_silk = loadItemStack("titan_pick_yellow_silk");
        titan_pick_blue_fortune = loadItemStack("titan_pick_blue_fortune");
        titan_pick_blue_silk = loadItemStack("titan_pick_blue_silk");
        titan_shovel_red = loadItemStack("titan_shovel_red");
        titan_axe_red = loadItemStack("titan_axe_red");
        titan_axe_yellow = loadItemStack("titan_axe_yellow");
        titan_axe_blue = loadItemStack("titan_axe_blue");
        titan_sword_red = loadItemStack("titan_sword_red");
        titan_sword_yellow = loadItemStack("titan_sword_yellow");
        titan_sword_blue = loadItemStack("titan_sword_blue");
        titan_rod_red = loadItemStack("titan_rod_red");
        loadConfig();
    }

    public void loadConfig() {
        fileConfiguration = plugin.getConfig();
        test_tool = loadItemStack("test_tool");
        setMaterialList("titanPick", allowedPickBlocks, "destroyable-items");
        setMaterialList("titanAxe", allowedAxeBlocks, "destroyable-items");
        setMaterialList("titanShovel", disallowedShovelItems, "non-destroyable-items");
        setDebugValue();
    }

    public void setMaterialList(String targetSection, Set<Material> targetList, String path) {
        ConfigurationSection tool = plugin.getConfig().getConfigurationSection(targetSection);
        if (tool == null) {
            plugin.getLogger().warning(targetSection + " configuration section not found!");
            return;
        }

        List<String> items = tool.getStringList(path);
        if (items.size() == 0) {
            plugin.getLogger().warning("No " + path + " found in " + targetSection + " section of config.");
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

    public Set<Material> getDisallowedShovelItems() { return disallowedShovelItems; }

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
        return titan_pick_red_fortune;
    }

    public ItemStack getTitanPickRedSilk() {
        return titan_pick_red_silk;
    }

    public ItemStack getTitanPickYellowFortune() {
        return titan_pick_yellow_fortune;
    }

    public ItemStack getTitanPickYellowSilk() {
        return titan_pick_yellow_silk;
    }

    public ItemStack getTitanPickBlueFortune() {
        return titan_pick_blue_fortune;
    }

    public ItemStack getTitanPickBlueSilk() {
        return titan_pick_blue_silk;
    }

    public ItemStack getTitanShovelRed() {
        return titan_shovel_red;
    }

    public ItemStack getTitanAxeRed() {
        return titan_axe_red;
    }

    public ItemStack getTitanAxeYellow() {
        return titan_axe_yellow;
    }

    public ItemStack getTitanAxeBlue() {
        return titan_axe_blue;
    }

    public ItemStack getTitanSwordRed() {
        return titan_sword_red;
    }

    public ItemStack getTitanSwordYellow() {
        return titan_sword_yellow;
    }

    public ItemStack getTitanSwordBlue() {
        return titan_sword_blue;
    }

    public ItemStack getTitanRodRed() {
        return titan_rod_red;
    }

    public ItemStack getTestTool() { return test_tool; }
}
