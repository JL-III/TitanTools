package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.PowerCrystal;
import com.nessxxiii.titantools.enums.TitanTool;
import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.nessxxiii.titantools.utils.Utils;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminCommands implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final PlayerCommands playerCommands;

    public AdminCommands(Plugin plugin, PlayerCommands playerCommands) {
        this.plugin = plugin;
        this.playerCommands = playerCommands;
    };

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && player.hasPermission("titan.tools.admin.tabcomplete")) {
            return new ArrayList<>() {{
                add("check-pdc");
                add("compare");
                add("debug");
                add("excavator");
                add("model");
                add("reload");
                add("save");
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
        if ("debug".equalsIgnoreCase(args[0])) {
            ItemStack itemStack = !(sender instanceof Player player) ? TitanTool.PICK_RED_FORTUNE.getItemStack() : player.getInventory().getItemInMainHand();
            Response<List<String>> loreResponse = ItemInfo.getLore(itemStack);
            if (!loreResponse.isSuccess()) return false;
            Utils.sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "Begin-Debug");
            List<String> lore = loreResponse.value();
            boolean isTitanTool = ItemInfo.isTitanTool(lore);
            Utils.sendPluginMessage(sender, "PowerCrystal type: " + PowerCrystal.getPowerCrystalType(itemStack));
            Utils.sendPluginMessage(sender, "isTitanTool: " + isTitanTool);
            Utils.sendPluginMessage(sender, "isImmortalDiadem: " + ItemInfo.isImmortalDiadem(itemStack));
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

        if ("check-pdc".equalsIgnoreCase(args[0]) && args.length == 2) {
            if (!Utils.permissionCheck(player, "check-pdc")) {
                return true;
            }
            if (player.getInventory().getItemInMainHand().getType() != Material.AIR) {
                NamespacedKey key = new NamespacedKey("titan-tools", args[1]);
                ItemStack item = player.getInventory().getItemInMainHand();
                ItemMeta meta = item.getItemMeta();
                if (meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN)) {
                    Utils.sendPluginMessage(player, "This item has the " + args[1] + " key.");
                } else {
                    Utils.sendPluginMessage(player, "This item does not have the " + args[1] + " key.");
                }
            }
        }

        if ("save".equalsIgnoreCase(args[0]) && args.length == 2) {
            if (!Utils.permissionCheck(player, "save")) {
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
            if (!Utils.permissionCheck(player, "model")) {
                return true;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
                Utils.sendPluginMessage(player, "You must be holding an item.");
                return true;
            }
            try {
                if (player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    Utils.sendPluginMessage(player, "Previous custom model data: " + ChatColor.YELLOW + player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData());
                }
                Integer customModelData = Integer.parseInt(args[1]);
                ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                meta.setCustomModelData(customModelData);
                player.getInventory().getItemInMainHand().setItemMeta(meta);
                Utils.sendPluginMessage(player, "Set custom model data to: " + ChatColor.GREEN + customModelData);
            } catch (Exception ex) {
                Utils.sendPluginMessage(player, "You must provide an integer value.");
            }
            return true;
        }

        if ("reload".equalsIgnoreCase(args[0])) {
            if (!Utils.permissionCheck(player, "reload")) {
                return true;
            }
            Utils.sendPluginMessage(player, "Reloading config...");
            plugin.reloadConfig();
            playerCommands.reload();
            Utils.sendPluginMessage(player, ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }

        if ("compare".equalsIgnoreCase(args[0])) {
            if (!Utils.permissionCheck(player, "compare")) {
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
}
