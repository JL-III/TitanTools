package com.nessxxiii.titanenchants.commands;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;


public class PlayerCommands implements CommandExecutor{

    private Plugin plugin;
    private FileConfiguration fileConfig;

    public PlayerCommands(Plugin plugin) {
        this.plugin = plugin;
        this.fileConfig = plugin.getConfig();
    };

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

        //TODO move this method to TheatriaUtils
//        if ("lastseen".equalsIgnoreCase(args[0])) {
//            OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
//
//            Arrays.sort(allPlayers, Comparator.comparing(OfflinePlayer::getLastLogin));
//
//            for (OfflinePlayer offlinePlayer : allPlayers) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(offlinePlayer.getLastLogin());
//                Bukkit.getConsoleSender().sendMessage(calendar.get(Calendar.MONTH) + "/" +
//                        calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR) + "  " + offlinePlayer.getName());
//            }
//        }
    return true;
    }
}














