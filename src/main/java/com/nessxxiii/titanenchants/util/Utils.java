package com.nessxxiii.titanenchants.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class Utils {

    public static void printBanner() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "   _____   _____   _        ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |    /_\\  |\\ |");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |   /   \\ | \\| enchants");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "               NessXXIII");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
    }

}
