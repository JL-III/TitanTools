package com.nessxxiii.titantools.utils;

import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;


public class CustomLogger {
    private final TextComponent loggerPrefix;

    public CustomLogger(String pluginName, NamedTextColor brackets, NamedTextColor pluginNameColor) {
        loggerPrefix = Component.text()
                .build()
                .append(Component.text("[", brackets))
                .append(Component.text(pluginName, pluginNameColor)
                        .append(Component.text("] ", brackets)));
    }

    public void sendLog(String message) {
        Audience console = Bukkit.getConsoleSender();
        console.sendMessage(loggerPrefix.append(Component.text(message, NamedTextColor.YELLOW)));
    }
}
