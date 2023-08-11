package com.nessxxiii.titanenchants;


import com.gmail.nossr50.mcMMO;
import com.nessxxiii.titanenchants.commands.AdminCommands;
import com.nessxxiii.titanenchants.commands.PlayerCommands;
import com.nessxxiii.titanenchants.commands.PlayerCommandsTabComplete;
import com.nessxxiii.titanenchants.listeners.ItemDamageEvent;
import com.nessxxiii.titanenchants.listeners.JoinListener;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanPicks;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ChargeManagement;
import com.nessxxiii.titanenchants.listeners.enchantments.TitanShovel;
import com.nessxxiii.titanenchants.listeners.mcMMO.McMMOManager;
import com.nessxxiii.titanenchants.listeners.blockbreak.PowerCrystalDrop;
import com.nessxxiii.titanenchants.util.Utils;
import com.playtheatria.jliii.generalutils.utils.CustomLogger;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class TitanEnchants extends JavaPlugin {

    @Override
    public void onEnable() {
        checkMcMMODependency();

        this.saveDefaultConfig();

        registerEvents();
        registerCommands();

        Utils.printBanner();
    }

    private void registerCommands() {
        PlayerCommands playerCommands = new PlayerCommands(this);
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, playerCommands));
        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("titan")).setTabCompleter(new PlayerCommandsTabComplete());
    }


    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new TitanPicks(this),this);
        Bukkit.getPluginManager().registerEvents(new TitanShovel(this), this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(this),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalDrop(),this);
        Bukkit.getPluginManager().registerEvents(new McMMOManager(),this);
        Bukkit.getPluginManager().registerEvents(new ItemDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
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
