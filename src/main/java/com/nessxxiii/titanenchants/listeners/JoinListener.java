package com.nessxxiii.titanenchants.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    FileConfiguration fileConfiguration;

    public JoinListener(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            try {
                event.getPlayer().setResourcePack(fileConfiguration.getString("resource-pack"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
