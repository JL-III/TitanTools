package com.nessxxiii.titantools;

import com.nessxxiii.titantools.commands.*;
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
        this.playerCommands = new PlayerCommands(this);
        CustomLogger customLogger = new CustomLogger(getName(), NamedTextColor.DARK_RED, NamedTextColor.WHITE);
        registerCommands(customLogger);
        Utils.printBanner(Bukkit.getConsoleSender());
    }

    private void registerCommands(CustomLogger customLogger) {
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, playerCommands));
        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("tkit")).setExecutor(new KitCommands(customLogger));
        Objects.requireNonNull(getCommand("crystal")).setExecutor(new CrystalCommand());
        Objects.requireNonNull(getCommand("theatriatool")).setExecutor(new TheatriaToolCommand(customLogger));
    }
}
