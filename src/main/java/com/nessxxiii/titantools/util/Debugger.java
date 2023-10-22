package com.nessxxiii.titantools.util;

import com.nessxxiii.titantools.config.ConfigManager;
import org.bukkit.Bukkit;

public class Debugger {
    private final ConfigManager configManager;
    public Debugger(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void sendDebugIfEnabled(String message) {
        if (configManager.getDebug()) {
            Bukkit.getConsoleSender().sendMessage(message);
        }
    }

}
