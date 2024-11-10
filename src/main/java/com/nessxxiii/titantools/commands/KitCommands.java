package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.TitanTool;
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

public class KitCommands implements CommandExecutor, TabCompleter {

    private final CustomLogger logger;

    public KitCommands(CustomLogger logger) {
        this.logger = logger;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && player.hasPermission("titan.admin.tabcomplete")) {
            if (args.length == 1) {
                return new ArrayList<>(){{
                    addAll(Arrays.stream(TitanTool.values())
                            .map(Enum::name)
                            .map(String::toLowerCase)
                            .toList());
                }};
            }
            if (args.length == 2) {
                return Bukkit.getOnlinePlayers().stream().collect(ArrayList::new, (list, p) -> list.add(p.getName()), ArrayList::addAll);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        if (!sender.hasPermission(Utils.PERMISSION_PREFIX_ADMIN)) {
            Utils.sendPluginMessage(sender, Utils.NO_PERMISSION);
            return true;
        }
        if (args.length >= 2) {
            if (Bukkit.getPlayer(args[1]) != null) {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) return false;
                String player_name = player.getName();
                Inventory inventory = player.getInventory();
                TitanTool titanTool;
                try {
                    titanTool = Enum.valueOf(TitanTool.class, args[0].toUpperCase());
                    Utils.reportResult(logger, args[0], inventory.addItem(titanTool.getItemStack()), player_name);
                    return true;
                } catch (IllegalArgumentException e) {
                    Utils.sendPluginMessage(player, "You must provide a correct titan tool type.");
                    Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide a correct titan tool type for /tkit <type> <username>.");
                    return false;
                }
            }
        }
        return false;
    }
}
