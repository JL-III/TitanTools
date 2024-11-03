package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.PowerCrystal;
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

public class CrystalCommand implements CommandExecutor, TabCompleter {

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
            if (args.length == 1) {
                inv.addItem(PowerCrystal.COMMON.getItemStack());
                inv.addItem(PowerCrystal.UNCOMMON.getItemStack());
                inv.addItem(PowerCrystal.SUPER.getItemStack());
                inv.addItem(PowerCrystal.EPIC.getItemStack());
                inv.addItem(PowerCrystal.ULTRA.getItemStack());
                Utils.sendPluginMessage(player, "Added 1 of each crystal.");
            } else if (args.length == 3) {
                int amount;
                PowerCrystal powerCrystal;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException exception) {
                    Utils.sendPluginMessage(player, "You must provide an integer amount.");
                    Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide an integer amount for /crystal give <type> <amount>.");
                    return false;
                }
                try {
                    powerCrystal = Enum.valueOf(PowerCrystal.class, args[1]);
                    for (int i = 0; i < amount; i++) {
                        inv.addItem(powerCrystal.getItemStack());
                    }
                } catch (IllegalArgumentException ex) {
                    Utils.sendPluginMessage(player, "You must provide a correct power crystal type.");
                    Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide a correct power crystal type for /crystal give <type> <amount>.");
                    return false;
                }
                Utils.sendPluginMessage(player, "Added " + amount + " " + powerCrystal  + " crystals.");
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
                return Arrays.stream(PowerCrystal.values()).map(Enum::name).toList();
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
