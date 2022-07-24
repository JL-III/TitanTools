package com.nessxxiii.titanenchants;

import com.nessxxiii.titanenchants.Items.ItemManager;
import com.nessxxiii.titanenchants.commands.PlayerCommands;
import com.nessxxiii.titanenchants.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.enchantments.TitanPicks;
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

    private final Logger LOGGER;
    private final FileConfiguration CONFIG;
    private final Plugin PLUGIN;

    public TitanEnchants() {
        PLUGIN = this;
        LOGGER = getLogger();
        CONFIG = getConfig();
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        ItemManager.Init();
        Bukkit.getPluginManager().registerEvents(new TitanPicks(this),this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(this),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalBlockBreakListener(),this);
        Bukkit.getPluginManager().registerEvents(new McMMOManager(),this);

        Objects.requireNonNull(getCommand("titan")).setExecutor(new PlayerCommands(this));

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
