package com.nessxxiii.titanenchants.listeners;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

public class JoinListener implements Listener {

    private Plugin plugin;
    private FileConfiguration fileConfiguration;

    public JoinListener(Plugin plugin) {
        this.plugin = plugin;
        this.fileConfiguration = plugin.getConfig();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            try {
                if (fileConfiguration.getString("resource-pack") == null) {
                    throw new RuntimeException("File does not exist, please make sure the link is correct.");
                }
                event.getPlayer().setResourcePack(fileConfiguration.getString("resource-pack"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
