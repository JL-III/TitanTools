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
    private ItemStack pick_red_fortune;
    private ItemStack pick_red_silk;
    private ItemStack pick_yellow_fortune;
    private ItemStack pick_yellow_silk;
    private ItemStack pick_blue_fortune;
    private ItemStack pick_blue_silk;
    private ItemStack shovel_red;
    private ItemStack axe_red;
    private ItemStack axe_yellow;
    private ItemStack axe_blue;
    private ItemStack sword_red;
    private ItemStack sword_yellow;
    private ItemStack sword_blue;
    private ItemStack rod_red;
    private ItemStack test_tool;
    private ItemStack immortal_diadem;

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
        pick_red_fortune = loadItemStack("titan_pick_red_fortune", pickaxesConfig);
        pick_red_silk = loadItemStack("titan_pick_red_silk", pickaxesConfig);
        pick_yellow_fortune = loadItemStack("titan_pick_yellow_fortune", pickaxesConfig);
        pick_yellow_silk = loadItemStack("titan_pick_yellow_silk", pickaxesConfig);
        pick_blue_fortune = loadItemStack("titan_pick_blue_fortune", pickaxesConfig);
        pick_blue_silk = loadItemStack("titan_pick_blue_silk", pickaxesConfig);
        shovel_red = loadItemStack("titan_shovel_red", shovelsConfig);
        axe_red = loadItemStack("titan_axe_red", axesConfig);
        axe_yellow = loadItemStack("titan_axe_yellow", axesConfig);
        axe_blue = loadItemStack("titan_axe_blue", axesConfig);
        sword_red = loadItemStack("titan_sword_red", swordsConfig);
        sword_yellow = loadItemStack("titan_sword_yellow", swordsConfig);
        sword_blue = loadItemStack("titan_sword_blue", swordsConfig);
        rod_red = loadItemStack("titan_rod_red", rodsConfig);
        test_tool = loadItemStack("test_tool", testToolConfig);
        immortal_diadem = loadItemStack("immortal_diadem", immortalConfig);
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

    public ItemStack getPickRedFortune() {
        return pick_red_fortune;
    }

    public ItemStack getPickRedSilk() {
        return pick_red_silk;
    }

    public ItemStack getPickYellowFortune() {
        return pick_yellow_fortune;
    }

    public ItemStack getPickYellowSilk() {
        return pick_yellow_silk;
    }

    public ItemStack getPickBlueFortune() {
        return pick_blue_fortune;
    }

    public ItemStack getPickBlueSilk() {
        return pick_blue_silk;
    }

    public ItemStack getShovelRed() {
        return shovel_red;
    }

    public ItemStack getAxeRed() {
        return axe_red;
    }

    public ItemStack getAxeYellow() {
        return axe_yellow;
    }

    public ItemStack getAxeBlue() {
        return axe_blue;
    }

    public ItemStack getSwordRed() {
        return sword_red;
    }

    public ItemStack getSwordYellow() {
        return sword_yellow;
    }

    public ItemStack getSwordBlue() {
        return sword_blue;
    }

    public ItemStack getRodRed() {
        return rod_red;
    }

    public ItemStack getTestTool() { return test_tool; }

    public ItemStack getImmortalDiadem() { return immortal_diadem; }

}
