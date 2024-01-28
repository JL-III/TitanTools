package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.generalutils.ConfigManager;
import com.nessxxiii.titantools.itemmanagement.ItemCreator;
import com.nessxxiii.titantools.itemmanagement.PowerCrystalInfo;
import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.nessxxiii.titantools.generalutils.Utils;
import com.playtheatria.jliii.generalutils.events.utils.AddCrystalEvent;
import com.playtheatria.jliii.generalutils.events.utils.SetModelDataEvent;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private final PlayerCommands playerCommands;
    private final ConfigManager configManager;

    public AdminCommands(Plugin plugin, PlayerCommands playerCommands, ConfigManager configManager) {
        this.plugin = plugin;
        this.playerCommands = playerCommands;
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
            Utils.sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "Begin-Debug");
            List<String> lore = loreResponse.value();
            boolean isTitanTool = ItemInfo.isTitanTool(lore);
            Utils.sendPluginMessage(sender, "isTitanTool: " + isTitanTool);
            Utils.sendPluginMessage(sender, "Contains charge lore: " + ItemInfo.hasChargeLore(lore, isTitanTool));
            Utils.sendPluginMessage(sender, "ToolColor: " + ItemInfo.getColor(lore));
            Utils.sendPluginMessage(sender, "ToolStatus: " + ItemInfo.getStatus(lore, isTitanTool));
            Utils.sendPluginMessage(sender, "isChargedTitanTool: " + ItemInfo.isChargedTitanTool(lore, isTitanTool));
            Utils.sendPluginMessage(sender, "chargeLoreIndex: " + ItemInfo.getTitanLoreIndex(lore, ItemInfo.CHARGE_PREFIX, isTitanTool));
            Utils.sendPluginMessage(sender, "statusLoreIndex: " + ItemInfo.getTitanLoreIndex(lore, ItemInfo.STATUS_PREFIX, isTitanTool));
            Utils.sendPluginMessage(sender, "Get charge amount: " + ItemInfo.getCharge(lore, isTitanTool, ItemInfo.hasChargeLore(lore, isTitanTool), 39));
            if (itemStack.getItemMeta().hasCustomModelData()) {
                Utils.sendPluginMessage(sender, "Current custom model data: " + itemStack.getItemMeta().getCustomModelData());
            } else {
                Utils.sendPluginMessage(sender, "This item does not have custom model data.");
            }
            Utils.sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "End-Debug");
            return true;
        }
        // all other commands require a player
        if (!(sender instanceof Player player)) {
            return false;
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
            Utils.sendPluginMessage(player, "Reloading config...");
            plugin.reloadConfig();
            playerCommands.reload();
            configManager.loadConfig();
            Utils.sendPluginMessage(player, ChatColor.GREEN + "Successfully reloaded config.");
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
