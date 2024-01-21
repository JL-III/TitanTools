package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.events.tools.titan.imbue.ImbueToolAttemptEvent;
import com.nessxxiii.titantools.events.util.AddCrystalEvent;
import com.nessxxiii.titantools.events.util.DebugEvent;
import com.nessxxiii.titantools.events.util.ReloadConfigEvent;
import com.nessxxiii.titantools.events.util.SetModelDataEvent;
import com.nessxxiii.titantools.items.ItemCreator;
import com.nessxxiii.titantools.items.PowerCrystalInfo;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminCommands implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final ConfigManager configManager;

    public AdminCommands(Plugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    };

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && player.hasPermission("titan.enchants.admin.tabcomplete")) {
            return new ArrayList<>() {{
                add("check");
                add("crystal");
                add("debug");
                add("excavator");
                add("imbue");
                add("pack");
                add("reload");
            }};
        } else {
            return new ArrayList<>() {{
                add("pack");
            }};
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (args.length == 0) return false;
        if (!sender.hasPermission(Utils.PERMISSION_PREFIX_ADMIN)) {
            Utils.sendPluginMessage(sender, Utils.NO_PERMISSION);
            return true;
        }
        // this command is allowed to be used by the console
        if (args[0].equalsIgnoreCase("debug")) {
            ItemStack itemStack = !(sender instanceof Player player) ? configManager.getTestTool() : player.getInventory().getItemInMainHand();
            Response<List<String>> loreResponse = ItemInfo.getLore(itemStack);
            if (!loreResponse.isSuccess()) return false;
            Bukkit.getPluginManager().callEvent(new DebugEvent(loreResponse.value(), sender, itemStack));
            return true;
        }
        // all other commands require a player
        if (!(sender instanceof Player player)) {
            return false;
        }
        if ("imbue".equalsIgnoreCase(args[0])) {
            if (!permissionCheck(player, "imbue")) {
                return true;
            }
            Material coolDown = Material.SQUID_SPAWN_EGG;
            if (!player.hasCooldown(coolDown)) {
                Utils.sendPluginMessage(player, "Are you sure you want to imbue this tool?");
                Utils.sendPluginMessage(player, "Retype the command to confirm");
                player.setCooldown(coolDown, 200);
                return false;
            }
            Bukkit.getPluginManager().callEvent(new ImbueToolAttemptEvent(player));
            return true;
        }

        if ("save".equalsIgnoreCase(args[0]) && args.length == 2) {
            if (!permissionCheck(player, "save")) {
                return true;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                player.sendMessage("You cannot save air.");
                return true;
            }
            plugin.getConfig().set(args[1], player.getInventory().getItemInMainHand());
            plugin.saveConfig();
        }

        if ("model".equalsIgnoreCase(args[0])) {
            if (!permissionCheck(player, "model")) {
                return true;
            }
            Bukkit.getPluginManager().callEvent(new SetModelDataEvent(player, args));
            return true;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            if (!permissionCheck(player, "reload")) {
                return true;
            }
            Bukkit.getPluginManager().callEvent(new ReloadConfigEvent(player));
            return true;
        }

        if ("crystal".equalsIgnoreCase(args[0])) {
            if (!permissionCheck(player, "crystal")) {
                return true;
            }
            Bukkit.getPluginManager().callEvent(new AddCrystalEvent(player, args));
            return true;
        }

        if ("excavator".equalsIgnoreCase(args[0])) {
            if (!permissionCheck(player, "excavator")) {
                return true;
            }
            Inventory inv = player.getInventory();
            inv.addItem(ItemCreator.excavator);
            Utils.sendPluginMessage(player, "Excavator added to inventory");
            return true;
        }

        if ("crystalcheck".equalsIgnoreCase(args[0]) && permissionCheck(player, "crystalcheck")) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Utils.sendPluginMessage(player, "Item is powercrystal: " + PowerCrystalInfo.isPowerCrystal(itemInMainHand));
            Utils.sendPluginMessage(player, "PowerCrystal type: " + PowerCrystalInfo.getPowerCrystalType(itemInMainHand));
            return true;
        }

        if ("compare".equalsIgnoreCase(args[0])) {
            if (!permissionCheck(player, "compare")) {
                return true;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInOffHand().getType() == Material.AIR) {
                Utils.sendPluginMessage(player, "You cannot compare air");
                return true;
            } else {
                Utils.sendPluginMessage(player, "isSimilar result: " + player.getInventory().getItemInMainHand().isSimilar(player.getInventory().getItemInOffHand()));             }
        }
        return false;
    }

    public boolean permissionCheck(Player player, String permission) {
        if (!player.hasPermission(Utils.PERMISSION_PREFIX_ADMIN + "." + permission)) {
            Utils.sendPluginMessage(player, Utils.NO_PERMISSION);
            return false;
        }
        return true;
    }
}
