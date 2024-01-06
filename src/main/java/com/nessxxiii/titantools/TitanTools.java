package com.nessxxiii.titantools;

import com.nessxxiii.titantools.commands.AdminCommands;
import com.nessxxiii.titantools.commands.KitCommands;
import com.nessxxiii.titantools.commands.PlayerCommands;
import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.listeners.ItemDamageEvent;
import com.nessxxiii.titantools.listeners.blockbreak.PowerCrystalDrop;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ToggleAncientPower;
import com.nessxxiii.titantools.listeners.tools.TitanAxe;
import com.nessxxiii.titantools.listeners.tools.TitanPicks;
import com.nessxxiii.titantools.listeners.tools.TitanShovel;
import com.nessxxiii.titantools.util.CustomLogger;
import com.nessxxiii.titantools.util.Utils;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TitanTools extends JavaPlugin {

    private ConfigManager configManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        CustomLogger customLogger = new CustomLogger(getName(), NamedTextColor.DARK_RED, NamedTextColor.WHITE);
        registerEvents();
        registerCommands(configManager, customLogger);
        Utils.printBanner();
    }

    private void registerCommands(ConfigManager configManager, CustomLogger customLogger) {
        PlayerCommands playerCommands = new PlayerCommands(this);
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, configManager, playerCommands));
        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("tkit")).setExecutor(new KitCommands(configManager,  customLogger));
    }


    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new TitanPicks(configManager),this);
        Bukkit.getPluginManager().registerEvents(new TitanAxe(configManager), this);
        Bukkit.getPluginManager().registerEvents(new TitanShovel(this, configManager), this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(configManager),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalDrop(),this);
        Bukkit.getPluginManager().registerEvents(new ItemDamageEvent(), this);
    }
}
