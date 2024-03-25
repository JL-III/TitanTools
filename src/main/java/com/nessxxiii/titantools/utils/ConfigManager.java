package com.nessxxiii.titantools.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class ConfigManager {

    private final Plugin plugin;
    private ItemStack titan_pick_red_fortune;
    private ItemStack titan_pick_red_silk;
    private ItemStack titan_pick_yellow_fortune;
    private ItemStack titan_pick_yellow_silk;
    private ItemStack titan_pick_blue_fortune;
    private ItemStack titan_pick_blue_silk;
    private ItemStack titan_shovel_red;
    private ItemStack titan_axe_red;
    private ItemStack titan_axe_yellow;
    private ItemStack titan_axe_blue;
    private ItemStack titan_sword_red;
    private ItemStack titan_sword_yellow;
    private ItemStack titan_sword_blue;
    private ItemStack titan_rod_red;
    private ItemStack test_tool;
    private ItemStack immortal_helm;
    private ItemStack immortal_chestplate;
    private ItemStack immortal_leggings;
    private ItemStack immortal_boots;

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration axesConfig = loadConfig("tools/axes.yml");
        FileConfiguration swordsConfig = loadConfig("tools/swords.yml");
        FileConfiguration pickaxesConfig = loadConfig("tools/pickaxes.yml");
        FileConfiguration shovelsConfig = loadConfig("tools/shovels.yml");
        FileConfiguration rodsConfig = loadConfig("tools/rods.yml");
        FileConfiguration testToolConfig = loadConfig("tools/test_tool.yml");
        FileConfiguration immortalConfig = loadConfig("tools/immortal.yml");
        titan_pick_red_fortune = loadItemStack("titan_pick_red_fortune", pickaxesConfig);
        titan_pick_red_silk = loadItemStack("titan_pick_red_silk", pickaxesConfig);
        titan_pick_yellow_fortune = loadItemStack("titan_pick_yellow_fortune", pickaxesConfig);
        titan_pick_yellow_silk = loadItemStack("titan_pick_yellow_silk", pickaxesConfig);
        titan_pick_blue_fortune = loadItemStack("titan_pick_blue_fortune", pickaxesConfig);
        titan_pick_blue_silk = loadItemStack("titan_pick_blue_silk", pickaxesConfig);
        titan_shovel_red = loadItemStack("titan_shovel_red", shovelsConfig);
        titan_axe_red = loadItemStack("titan_axe_red", axesConfig);
        titan_axe_yellow = loadItemStack("titan_axe_yellow", axesConfig);
        titan_axe_blue = loadItemStack("titan_axe_blue", axesConfig);
        titan_sword_red = loadItemStack("titan_sword_red", swordsConfig);
        titan_sword_yellow = loadItemStack("titan_sword_yellow", swordsConfig);
        titan_sword_blue = loadItemStack("titan_sword_blue", swordsConfig);
        titan_rod_red = loadItemStack("titan_rod_red", rodsConfig);
        test_tool = loadItemStack("test_tool", testToolConfig);
        immortal_helm = loadItemStack("immortal_helm", immortalConfig);
        immortal_chestplate = loadItemStack("immortal_chestplate", immortalConfig);
        immortal_leggings = loadItemStack("immortal_leggings", immortalConfig);
        immortal_boots = loadItemStack("immortal_boots", immortalConfig);
    }

    private ItemStack loadItemStack(String target, FileConfiguration fileConfiguration) {
        ItemStack targetItemstack = (ItemStack) fileConfiguration.get(target);
        if (targetItemstack != null) {
            Bukkit.getConsoleSender().sendMessage("Loaded " + target + " from the config.");
        } else {
            Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "Error loading " + target + " from the config. Check to ensure an entry exists!");
        }
        return targetItemstack;
    }

    public FileConfiguration loadConfig(String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            try {
                plugin.saveResource(fileName, false);
                Bukkit.getConsoleSender().sendMessage(fileName + " created!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Bukkit.getConsoleSender().sendMessage(fileName + " found, loading!");
        }
        return YamlConfiguration.loadConfiguration(file);
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

    public ItemStack getImmortalHelm() { return immortal_helm; }

    public ItemStack getImmortalChestplate() { return immortal_chestplate; }

    public ItemStack getImmortalLeggings() { return immortal_leggings; }

    public ItemStack getImmortalBoots() { return immortal_boots; }
}
