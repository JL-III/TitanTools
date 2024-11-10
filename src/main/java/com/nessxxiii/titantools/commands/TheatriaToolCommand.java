package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.TheatriaTool;
import com.nessxxiii.titantools.utils.CustomLogger;
import com.nessxxiii.titantools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TheatriaToolCommand implements CommandExecutor, TabCompleter {

    private final CustomLogger logger;

    public TheatriaToolCommand(CustomLogger logger) {
        this.logger = logger;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        if (!sender.hasPermission(Utils.PERMISSION_PREFIX_ADMIN)) {
            Utils.sendPluginMessage(sender, Utils.NO_PERMISSION);
            return true;
        }
        if (!(sender instanceof Player player)) {
            return false;
        }
        if ("give".equalsIgnoreCase(args[0])) {
            if (!Utils.permissionCheck(player, "give")) {
                return true;
            }
            Inventory inv = player.getInventory();
            if (args.length == 3) {
                int amount;
                TheatriaTool theatriaTool;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException exception) {
                    Utils.sendPluginMessage(player, "You must provide an integer amount.");
                    Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide an integer amount for /theatriatool give <type> <amount>.");
                    return false;
                }
                try {
                    theatriaTool = Enum.valueOf(TheatriaTool.class, args[1].toUpperCase());
                    for (int i = 0; i < amount; i++) {
                        inv.addItem(theatriaTool.getItemStack());
                    }
                } catch (IllegalArgumentException ex) {
                    Utils.sendPluginMessage(player, "You must provide a correct TheatriaTool type.");
                    Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide a correct TheatriaTool type for /theatriatool give <type> <amount>.");
                    return false;
                }
                Utils.sendPluginMessage(player, "Added " + amount + " " + theatriaTool  + " " + args[2] + ".");
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        switch (args.length) {
            case 0 -> {
                return new ArrayList<>();
            }
            case 1 -> {
                return new ArrayList<>(){{
                    add("give");
                }};
            }
            case 2 -> {
                return Arrays.stream(TheatriaTool.values())
                        .map(Enum::name)
                        .map(String::toLowerCase)
                        .toList();
            }
            case 3 -> {
                return new ArrayList<>(){{
                    add("<amount>");
                }};
            }
        }
        return new ArrayList<>();
    }
}
