package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.PowerCrystal;
import com.nessxxiii.titantools.enums.TheatriaTool;
import com.nessxxiii.titantools.enums.TitanTool;
import com.nessxxiii.titantools.utils.CustomLogger;
import com.nessxxiii.titantools.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TitanCommands implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final CustomLogger logger;
    private FileConfiguration fileConfig;

    public TitanCommands(Plugin plugin, CustomLogger logger) {
        this.plugin = plugin;
        this.logger = logger;
        this.fileConfig = plugin.getConfig();
    };

    public void reload() {
        this.fileConfig = plugin.getConfig();
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission(Utils.PERMISSIONS_PREFIX + ".tab-complete")) {
            return new ArrayList<>();
        }
        String input = args[0].toLowerCase();
        switch (input) {
            case "pack", "unpack" -> {
                return new ArrayList<>();
            }
            case "kit" -> {
                if (!sender.hasPermission(Utils.ADMIN_PERMISSIONS_PREFIX + "." + input)) return new ArrayList<>();
                if (args.length == 2) {
                    return new ArrayList<>(){{
                        addAll(Arrays.stream(TitanTool.values())
                                .map(Enum::name)
                                .map(String::toLowerCase)
                                .toList());
                        addAll(Arrays.stream(TheatriaTool.values())
                                .map(Enum::name)
                                .map(String::toLowerCase)
                                .toList());
                        addAll(Arrays.stream(PowerCrystal.values())
                                .map(Enum::name)
                                .map(String::toLowerCase)
                                .toList());
                    }};
                }
                if (args.length == 3) {
                    return Bukkit.getOnlinePlayers().stream().collect(ArrayList::new, (list, p) -> list.add(p.getName()), ArrayList::addAll);
                }
                if (args.length == 4) {
                    return new ArrayList<>(){{
                        add("<amount>");
                    }};
                }
            }
            case "lore" -> {
                switch (args.length) {
                    case 2 -> {
                        return new ArrayList<>() {{
                            add("remove-all");
                            add("remove");
                            add("add");
                        }};
                    }
                    case 3, 4 -> {
                        if (args[1].equalsIgnoreCase("add")) {
                            return new ArrayList<>(){{
                                add("#hexcolor");
                            }};
                        }
                    }
                    case 5 -> {
                        if (args[1].equalsIgnoreCase("add")) {
                            return new ArrayList<>() {{
                                add("<message>");
                            }};
                        }
                    }
                }
                return new ArrayList<>();
            }
        }
        if (args.length == 1) {
            if (sender.hasPermission(Utils.ADMIN_PERMISSIONS_PREFIX + ".tab-complete")) {
                return new ArrayList<>(){{
                    add("check-pdc");
                    add("compare");
                    add("debug");
                    add("model");
                    add("reload");
                    add("kit");
                    add("display-name");
                    add("lore");
                    add("hide-enchants");
                }};
            } else {
                return new ArrayList<>(){{
                    add("pack");
                    add("unpack");
                }};
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (args.length == 0) return false;
        if (!Utils.permissionCheck(sender, Utils.PERMISSIONS_PREFIX, "use")) {
            return true;
        }
        String input = args[0].toLowerCase();
        switch (input) {
            case "pack" -> {
                CommandMethods.packCommand(sender, input, fileConfig);
                return true;
            }
            case "unpack" -> {
                CommandMethods.unpackCommand(sender, input, fileConfig);
                return true;
            }
            case "kit" -> {
                if (args.length >= 3) {
                    CommandMethods.kitCommand(sender, args, input, logger);
                    return true;
                }
                return false;
            }
            case "compare" -> {
                CommandMethods.compareCommand(sender, input);
                return true;
            }
            case "debug" -> {
                CommandMethods.debugCommand(sender, input);
                return true;
            }
            case "check-pdc" -> {
                if (args.length == 2) {
                    CommandMethods.checkPDCCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "hide-enchants" -> {
                CommandMethods.hideEnchants(sender, input);
                return true;
            }
            case "model" -> {
                if (args.length == 2) {
                    CommandMethods.setModelCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "reload" -> {
                CommandMethods.reloadCommand(sender, input, plugin);
                return true;
            }
            case "display-name" -> {
                if (args.length >= 5 && args[1].equalsIgnoreCase("add")) {
                    CommandMethods.gradientComponentCommand(sender, args, input, false);
                    return true;
                }
            }
            case "lore" -> {
                if (args.length >= 5 && args[1].equalsIgnoreCase("add")) {
                    CommandMethods.gradientComponentCommand(sender, args, input, true);
                    return true;
                }
                if (args.length == 2 && args[1].equalsIgnoreCase("remove")) {
                    CommandMethods.loreRemoveCommand(sender, input);
                    return true;
                }
                if (args.length == 2 && args[1].equalsIgnoreCase("remove-all")) {
                    CommandMethods.loreRemoveAllCommand(sender, input);
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}














