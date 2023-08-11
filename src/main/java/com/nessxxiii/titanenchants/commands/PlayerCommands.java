package com.nessxxiii.titanenchants.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;


public class PlayerCommands implements CommandExecutor{

    private final Plugin plugin;
    private FileConfiguration fileConfig;

    public PlayerCommands(Plugin plugin) {
        this.plugin = plugin;
        this.fileConfig = plugin.getConfig();
    };

    public void reload() {
        this.fileConfig = plugin.getConfig();
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) return false;
        if (!player.hasPermission("titan.enchants.playercommands")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if ("pack".equalsIgnoreCase(args[0]) && player.hasPermission("titan.enchants.playercommands.pack")) {
            try {
                if (fileConfig.getString("resource-pack") == null) {
                    throw new RuntimeException("File does not exist, please make sure the link is correct.");
                }

                player.setResourcePack(fileConfig.getString("resource-pack"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }
        if ("unpack".equalsIgnoreCase(args[0]) && player.hasPermission("titan.enchants.playercommands.unpack")) {
            try {
                if (fileConfig.getString("resource-pack") == null) {
                    throw new RuntimeException("File does not exist, please make sure the link is correct.");
                }

                player.setResourcePack("");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return true;
        }

    return true;
    }
}














