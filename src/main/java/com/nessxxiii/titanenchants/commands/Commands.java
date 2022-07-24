package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.TitanEnchants;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class Commands implements CommandExecutor {

    private Plugin plugin;

    public Commands(Plugin plugin){
        this.plugin = plugin;
    };

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!player.hasPermission("benchants.enchant")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length == 0) return false;
        if ("reload".equalsIgnoreCase(args[0])) {
            plugin.getLogger().info("Reloading config...");
            plugin.reloadConfig();
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }

        if (args.length != 2) return false;

        return true;
    }



}
