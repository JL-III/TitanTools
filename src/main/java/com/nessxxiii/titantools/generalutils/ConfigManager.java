package com.nessxxiii.titantools.generalutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

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
    private boolean debug;

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
        setDebugValue();
    }

    public void setDebugValue() {
        debug = plugin.getConfig().getBoolean("debug");
        Bukkit.getConsoleSender().sendMessage("Debug mode is set to " + debug);
    }

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
