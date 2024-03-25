package com.nessxxiii.titantools;

import com.nessxxiii.titantools.commands.AdminCommands;
import com.nessxxiii.titantools.commands.KitCommands;
import com.nessxxiii.titantools.commands.PlayerCommands;
import com.nessxxiii.titantools.utils.ConfigManager;
import com.nessxxiii.titantools.utils.CustomLogger;
import com.nessxxiii.titantools.utils.Utils;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TitanTools extends JavaPlugin {

    private PlayerCommands playerCommands;

    @Override
    public void onEnable() {
        ConfigManager configManager = new ConfigManager(this);
        this.playerCommands = new PlayerCommands(this);
        CustomLogger customLogger = new CustomLogger(getName(), NamedTextColor.DARK_RED, NamedTextColor.WHITE);
        registerCommands(configManager, customLogger);
        Utils.printBanner(Bukkit.getConsoleSender());
    }

    private void registerCommands(ConfigManager configManager, CustomLogger customLogger) {
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, playerCommands, configManager));
        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("tkit")).setExecutor(new KitCommands(configManager,  customLogger));
    }
}
