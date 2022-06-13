package com.nessxxiii.titanenchants;

import com.nessxxiii.titanenchants.commands.AddPowerCrystal;
import com.nessxxiii.titanenchants.commands.PlayerCommands;
import com.nessxxiii.titanenchants.enchantments.TitanPickFort.PickFort;
import com.nessxxiii.titanenchants.enchantments.TitanPickFort.ToggleChargedPickFort;
import com.nessxxiii.titanenchants.enchantments.TitanPickFort.ToggleImbuedPickFort;
import com.nessxxiii.titanenchants.enchantments.TitanPickSilk.PickSilk;
import com.nessxxiii.titanenchants.enchantments.TitanPickSilk.ToggleChargedPickSilk;
import com.nessxxiii.titanenchants.enchantments.TitanPickSilk.ToggleImbuedPickSilk;
import com.nessxxiii.titanenchants.util.ChargeManagement;
import com.nessxxiii.titanenchants.util.McMMOManager;
import com.nessxxiii.titanenchants.util.PowerCrystalBlockBreakListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class TitanEnchants extends JavaPlugin {

    public static Logger LOGGER;
    public static FileConfiguration CONFIG;
    public static Plugin PLUGIN;

    public TitanEnchants() {
        PLUGIN = this;
        LOGGER = getLogger();
        CONFIG = getConfig();

    }
    public TitanEnchants(Plugin plugin){
        this.PLUGIN = plugin;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getPluginManager().registerEvents(new PickSilk(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleChargedPickSilk(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleImbuedPickSilk(), this);

        Bukkit.getPluginManager().registerEvents(new PickFort(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleChargedPickFort(), this);
        Bukkit.getPluginManager().registerEvents(new ToggleImbuedPickFort(), this);

        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalBlockBreakListener(),this);
        Bukkit.getPluginManager().registerEvents(new McMMOManager(),this);

        Objects.requireNonNull(getCommand("ancient")).setExecutor(new PlayerCommands());
        Objects.requireNonNull(getCommand("powercrystal")).setExecutor(new AddPowerCrystal());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
