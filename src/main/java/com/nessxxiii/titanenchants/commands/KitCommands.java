package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.items.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class KitCommands implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("titan.kit.give")) {
            if ("kit".equalsIgnoreCase(args[0]) && args.length == 3) {
                if (Bukkit.getPlayer(args[2]) != null) {

                    switch (args[1]) {
                        case "common" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.powerCrystalCommon);
                        case "uncommon" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.powerCrystalUncommon);
                        case "super" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.powerCrystalSuper);
                        case "epic" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.powerCrystalEpic);
                        case "ultra" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.powerCrystalUltra);
                        case "excavator" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.excavator);
                        case "spawnerkey" -> Bukkit.getPlayer(args[2]).getInventory().addItem(ItemCreator.spawnerkey);
                        default -> sender.sendMessage(ChatColor.DARK_RED + "This kit does not exist");
                    }

                }
            }
        }
        return false;
    }
}
