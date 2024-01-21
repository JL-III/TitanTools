package com.nessxxiii.titantools;

import com.nessxxiii.titantools.commands.AdminCommands;
import com.nessxxiii.titantools.commands.KitCommands;
import com.nessxxiii.titantools.commands.PlayerCommands;
import com.nessxxiii.titantools.generalutils.ConfigManager;
import com.nessxxiii.titantools.listeners.tools.excavator.ExcavatorBlockBreak;
import com.nessxxiii.titantools.listeners.utils.ChargeManagement;
import com.nessxxiii.titantools.listeners.utils.ToggleAncientPower;
import com.nessxxiii.titantools.listeners.tools.titan.enchants.AxeBlockBreak;
import com.nessxxiii.titantools.listeners.tools.titan.enchants.PickBlockBreak;
import com.nessxxiii.titantools.listeners.tools.titan.enchants.ShovelBlockBreak;
import com.nessxxiii.titantools.listeners.tools.titan.imbue.ImbueTitanTool;
import com.nessxxiii.titantools.listeners.utils.*;
import com.nessxxiii.titantools.generalutils.CustomLogger;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Utils;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TitanTools extends JavaPlugin {

    private ConfigManager configManager;
    private PlayerCommands playerCommands;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        this.playerCommands = new PlayerCommands(this);
        CustomLogger customLogger = new CustomLogger(getName(), NamedTextColor.DARK_RED, NamedTextColor.WHITE);
        Debugger debugger = new Debugger(configManager);
        registerListeners(debugger);
        registerCommands(configManager, customLogger);
        Utils.printBanner(Bukkit.getConsoleSender());
    }

    private void registerCommands(ConfigManager configManager, CustomLogger customLogger) {
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, configManager));
        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("tkit")).setExecutor(new KitCommands(configManager,  customLogger));
    }


    private void registerListeners(Debugger debugger) {
        Bukkit.getPluginManager().registerEvents(new PickBlockBreak(configManager, debugger),this);
        Bukkit.getPluginManager().registerEvents(new AxeBlockBreak(configManager, debugger), this);
        Bukkit.getPluginManager().registerEvents(new ShovelBlockBreak(configManager, debugger), this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(debugger),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(debugger),this);
        Bukkit.getPluginManager().registerEvents(new ExcavatorBlockBreak(),this);
        Bukkit.getPluginManager().registerEvents(new ToolEventHandler(), this);
        Bukkit.getPluginManager().registerEvents(new ItemDamageEvent(debugger), this);
        Bukkit.getPluginManager().registerEvents(new Debug(), this);
        Bukkit.getPluginManager().registerEvents(new ImbueTitanTool(), this);
        Bukkit.getPluginManager().registerEvents(new AddCrystal(), this);
        Bukkit.getPluginManager().registerEvents(new SetModelData(), this);
        Bukkit.getPluginManager().registerEvents(new ReloadConfig(this, playerCommands, configManager), this);
    }
}
