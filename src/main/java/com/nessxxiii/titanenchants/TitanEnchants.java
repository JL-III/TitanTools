package com.nessxxiii.titanenchants;

import com.gmail.nossr50.mcMMO;
import com.nessxxiii.titanenchants.commands.AdminCommands;
import com.nessxxiii.titanenchants.commands.PlayerCommands;
import com.nessxxiii.titanenchants.commands.PlayerCommandsTabComplete;
import com.nessxxiii.titanenchants.config.ConfigManager;
import com.nessxxiii.titanenchants.listeners.ItemDamageEvent;
import com.nessxxiii.titanenchants.listeners.blockbreak.PowerCrystalDrop;
import com.nessxxiii.titanenchants.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titanenchants.listeners.enchantmentManagement.ToggleAncientPower;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanAxe;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanPicks;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanShovel;
import com.nessxxiii.titanenchants.listeners.mcMMOManagement.McMMO;
import com.nessxxiii.titanenchants.util.Utils;
import com.playtheatria.jliii.generalutils.GeneralUtils;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TitanEnchants extends JavaPlugin {

    private ConfigManager configManager;
    private com.playtheatria.jliii.generalutils.managers.ConfigManager generalUtilsConfigManager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.configManager = new ConfigManager(this);
        checkMcMMODependency();

        registerEvents();
        generalUtilsConfigManager = GeneralUtils.getConfigManager();
        if (generalUtilsConfigManager != null) {
            registerCommands(generalUtilsConfigManager);
            Bukkit.getConsoleSender().sendMessage("Registering commands.");
        }
        Utils.printBanner();
    }

    private void registerCommands(com.playtheatria.jliii.generalutils.managers.ConfigManager configManager) {
        PlayerCommands playerCommands = new PlayerCommands(this);
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, configManager, playerCommands));
        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("titan")).setTabCompleter(new PlayerCommandsTabComplete());
    }


    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new TitanPicks(configManager),this);
        Bukkit.getPluginManager().registerEvents(new TitanAxe(configManager), this);
        Bukkit.getPluginManager().registerEvents(new TitanShovel(this), this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalDrop(),this);
        Bukkit.getPluginManager().registerEvents(new McMMO(),this);
        Bukkit.getPluginManager().registerEvents(new ItemDamageEvent(), this);
    }

    private void checkMcMMODependency() {
        Plugin plugin = getServer().getPluginManager().getPlugin("mcMMO");

        if (!(plugin instanceof mcMMO)) {
            getLogger().severe("mcMMO not found, disabling TheatriaEnchants...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getLogger().warning("Found mcMMO plugin! Continuing...");
    }

}
