package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


public class PlayerCommands implements CommandExecutor, TabCompleter {

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
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        return new ArrayList<>() {{
            add("pack");
        }};
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if (args.length == 0) return false;
        if (!player.hasPermission("titan.enchants.playercommands")) {
            player.sendMessage(Utils.NO_PERMISSION);
            return true;
        }
        if ("pack".equalsIgnoreCase(args[0]) && player.hasPermission("titan.enchants.playercommands.pack")) {
            try {
                if (fileConfig.getString("resource-pack") == null || fileConfig.getString("resource-pack").length() < 3) {
                    Utils.sendPluginMessage(player, ChatColor.RED + "File does not exist, please contact an admin for this issue.");
                    throw new RuntimeException("File does not exist, please make sure the link is correct.");
                }
                Utils.sendPluginMessage(player, ChatColor.GREEN + "Downloading resource pack...");
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














