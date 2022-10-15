package com.nessxxiii.titanenchants;

import com.nessxxiii.titanenchants.items.ItemManager;
import com.nessxxiii.titanenchants.commands.PlayerCommands;
import com.nessxxiii.titanenchants.commands.PlayerCommandsTabComplete;
import com.nessxxiii.titanenchants.listeners.ItemDamageEvent;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanPicks;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ChargeManagement;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanShovel;
import com.nessxxiii.titanenchants.listeners.mcMMO.McMMOManager;
import com.nessxxiii.titanenchants.listeners.blockbreak.PowerCrystalDrop;
import com.nessxxiii.titanenchants.util.CheckPlayerLocation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class TitanEnchants extends JavaPlugin {

    private final Logger LOGGER;
    private final FileConfiguration CONFIG;
    private final Plugin PLUGIN;

    private final CheckPlayerLocation checkPlayerLocation = new CheckPlayerLocation();

    public TitanEnchants() {
        PLUGIN = this;
        LOGGER = getLogger();
        CONFIG = getConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        ItemManager.Init();

//        Bukkit.getScheduler().runTaskTimer(this, this.checkPlayerLocation, 100, 100);
        Bukkit.getPluginManager().registerEvents(new TitanPicks(this),this);
        Bukkit.getPluginManager().registerEvents(new TitanShovel(this), this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(this),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalDrop(),this);
        Bukkit.getPluginManager().registerEvents(new McMMOManager(),this);
        Bukkit.getPluginManager().registerEvents(new ItemDamageEvent(), this);
        Objects.requireNonNull(getCommand("titan")).setExecutor(new PlayerCommands(this));
        Objects.requireNonNull(getCommand("titan")).setTabCompleter(new PlayerCommandsTabComplete());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
