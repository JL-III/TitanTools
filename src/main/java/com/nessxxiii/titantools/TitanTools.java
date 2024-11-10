package com.nessxxiii.titantools;

import com.nessxxiii.titantools.commands.*;
import com.nessxxiii.titantools.utils.CustomLogger;
import com.nessxxiii.titantools.utils.Utils;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TitanTools extends JavaPlugin {


    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        CustomLogger customLogger = new CustomLogger(getName(), NamedTextColor.DARK_RED, NamedTextColor.WHITE);
        TitanCommands titanCommands = new TitanCommands(this, customLogger);
        Objects.requireNonNull(getCommand("titan")).setExecutor(titanCommands);
        Utils.printBanner(Bukkit.getConsoleSender());
    }
}
