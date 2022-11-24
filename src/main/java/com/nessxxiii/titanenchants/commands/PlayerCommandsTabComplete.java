package com.nessxxiii.titanenchants.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class PlayerCommandsTabComplete implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        List<String> commands = new ArrayList<>();
        commands.add("reload");
        commands.add("crystal");
        commands.add("check");
        commands.add("imbue");
        commands.add("excavator");
        if (sender instanceof Player player && player.hasPermission("titan.enchants.tabcomplete")) {
            return commands;
        } else {
            return  new ArrayList<>();
        }
    }
}
