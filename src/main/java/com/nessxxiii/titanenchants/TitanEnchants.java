package com.nessxxiii.titanenchants;

import com.gmail.nossr50.mcMMO;
import com.nessxxiii.titanenchants.commands.AdminCommands;
import com.nessxxiii.titanenchants.commands.KitCommands;
import com.nessxxiii.titanenchants.items.ItemCreator;
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

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.logging.Logger;

public final class TitanEnchants extends JavaPlugin {

    private final Logger LOGGER;
    private final Plugin PLUGIN;
    private PlayerCommands playerCommands;


    public TitanEnchants() {
        PLUGIN = this;
        LOGGER = getLogger();
        playerCommands = new PlayerCommands(this);

    }

    @Override
    public void onEnable() {
        // Plugin startup logic
//        Bukkit.getScheduler().runTaskTimer(this, this.checkPlayerLocation, 100, 100);
        Plugin plugin = getServer().getPluginManager().getPlugin("mcMMO");

        if (!(plugin instanceof mcMMO)) {
            getLogger().severe("SomeDependencyPlugin not found, disabling TheatriaEnchants...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getLogger().warning("Found mcMMO plugin! Continuing...");
        this.saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new TitanPicks(this),this);
        Bukkit.getPluginManager().registerEvents(new TitanShovel(this), this);
        Bukkit.getPluginManager().registerEvents(new ToggleAncientPower(this),this);
        Bukkit.getPluginManager().registerEvents(new ChargeManagement(),this);
        Bukkit.getPluginManager().registerEvents(new PowerCrystalDrop(),this);
        Bukkit.getPluginManager().registerEvents(new McMMOManager(),this);
        Bukkit.getPluginManager().registerEvents(new ItemDamageEvent(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Objects.requireNonNull(getCommand("tkit")).setExecutor(new KitCommands(LOGGER));
        Objects.requireNonNull(getCommand("atitan")).setExecutor(new AdminCommands(this, playerCommands));

        Objects.requireNonNull(getCommand("titan")).setExecutor(playerCommands);
        Objects.requireNonNull(getCommand("titan")).setTabCompleter(new PlayerCommandsTabComplete());
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "   _____   _____   _        ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |    /_\\  |\\ |");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |   /   \\ | \\| enchants");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "               NessXXIII");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
        new ItemCreator(this);
//        ItemCreator.Init();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public PlayerCommands getPlayerCommands() { return this.playerCommands; }

}
