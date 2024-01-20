package com.nessxxiii.titantools.listeners.admin;

import com.nessxxiii.titantools.TitanTools;
import com.nessxxiii.titantools.commands.PlayerCommands;
import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.events.admin.ReloadConfigEvent;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ReloadConfig implements Listener {

    private final TitanTools plugin;

    private final PlayerCommands playerCommands;

    private final ConfigManager configManager;

    public ReloadConfig(TitanTools plugin, PlayerCommands playerCommands, ConfigManager configManager) {
        this.plugin = plugin;
        this.playerCommands = playerCommands;
        this.configManager = configManager;
    }

    @EventHandler
    public void onReloadConfig(ReloadConfigEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "Reloading config...");
        plugin.reloadConfig();
        playerCommands.reload();
        configManager.loadConfig();
        Utils.sendPluginMessage(event.getPlayer(), ChatColor.GREEN + "Successfully reloaded config.");
    }
}
