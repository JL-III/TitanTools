package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.enums.ToolColor;
import com.nessxxiii.titantools.items.CustomModelData;
import com.nessxxiii.titantools.items.ItemCreator;
import com.nessxxiii.titantools.items.PowerCrystalInfo;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Response;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AdminCommands implements CommandExecutor, TabCompleter {

    private final Plugin plugin;
    private final PlayerCommands playerCommands;
    private final ConfigManager configManager;
    private final String permissionPrefix = "titan.enchants.admincommands";
    private final String NO_PERMISSION = ChatColor.RED + "No Permission.";

    public AdminCommands(Plugin plugin, ConfigManager configManager, PlayerCommands playerCommands) {
        this.plugin = plugin;
        this.configManager = configManager;
        this.playerCommands = playerCommands;
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

        if (args[0].equalsIgnoreCase("debug")) {
            ItemStack itemStack = !(sender instanceof Player player) ? configManager.getTestTool() : player.getInventory().getItemInMainHand();
            sender.sendMessage(ChatColor.LIGHT_PURPLE + "--------------------Debug--------------------");
            Response<List<String>> loreResponse = ItemInfo.getLore(itemStack);
            if (!loreResponse.isSuccess()) return false;
            List<String> lore = loreResponse.value();
            boolean isTitanTool = ItemInfo.isTitanTool(lore);
            sender.sendMessage("isTitanTool: " + isTitanTool);
            sender.sendMessage("Contains charge lore: " + ItemInfo.hasChargeLore(lore, isTitanTool));
            sender.sendMessage("ToolColor: " + ItemInfo.getColor(lore));
            sender.sendMessage("ToolStatus: " + ItemInfo.getStatus(lore, isTitanTool));
            sender.sendMessage("isChargedTitanTool: " + ItemInfo.isChargedTitanTool(lore, isTitanTool));
            sender.sendMessage("chargeLoreIndex: " + ItemInfo.getTitanLoreIndex(lore, ItemInfo.CHARGE_PREFIX, isTitanTool));
            sender.sendMessage("statusLoreIndex: " + ItemInfo.getTitanLoreIndex(lore, ItemInfo.STATUS_PREFIX, isTitanTool));
            sender.sendMessage("Get charge amount: " + ItemInfo.getCharge(lore, isTitanTool, ItemInfo.hasChargeLore(lore, isTitanTool), 39));
            if (itemStack.getItemMeta().hasCustomModelData()) {
                sender.sendMessage("Current custom model data: " + itemStack.getItemMeta().getCustomModelData());
            } else {
                sender.sendMessage("This item does not have custom model data.");
            }
            return true;
        }

        if (!(sender instanceof Player player)) {
            return false;
        }
        if (!player.hasPermission(permissionPrefix)) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }

        if ("imbue".equalsIgnoreCase(args[0])) {
            Material coolDown = Material.SQUID_SPAWN_EGG;
            Response<List<String>> loreListResponse = ItemInfo.getLore(player.getInventory().getItemInMainHand());
            if (loreListResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
                return true;
            }
            ItemStack item = player.getInventory().getItemInMainHand();
            boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
            if (!isTitanTool) return false;
            if (!player.hasPermission(permissionStringMaker("imbue"))) {
                player.sendMessage(NO_PERMISSION);
                return false;
            }
            if (ItemInfo.isImbuedTitanTool(loreListResponse.value(), isTitanTool)) {
                player.sendMessage(ChatColor.GREEN + "That item is already imbued!");
                return false;
            }
            if (!player.hasCooldown(coolDown)) {
                player.sendMessage(ChatColor.GREEN + "Are you sure you want to imbue this tool?");
                player.sendMessage(ChatColor.GREEN + "Retype the command to confirm");
                player.setCooldown(coolDown, 200);
                return false;
            }

            boolean isAllowedType = ItemInfo.isAllowedType(item, ItemInfo.ALLOWED_TITAN_TYPES);
            if (!isAllowedType) {
                player.sendMessage("This is not an allowed type");
                return true;
            }
            boolean hasChargeLore = ItemInfo.hasChargeLore(loreListResponse.value(), isTitanTool);
            if (!hasChargeLore) {
                player.sendMessage("Cannot imbue an item that doesn't have charge lore.");
                return true;
            }
            Response<Integer> chargeAmountResponse = ItemInfo.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);
            if (chargeAmountResponse.error() != null) {
                player.sendMessage(chargeAmountResponse.error());
                return true;
            }
            if (chargeAmountResponse.value() != 0) {
                player.sendMessage("You cannot imbue a tool with a charge!");
                return true;
            }
            Response<Integer> chargeLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreListResponse.value(), ItemInfo.CHARGE_PREFIX, isTitanTool);
            if (chargeLoreIndexResponse.error() != null) {
                player.sendMessage(chargeLoreIndexResponse.error());
                return true;
            }
            Response<Integer> statusLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreListResponse.value(), ItemInfo.STATUS_PREFIX, isTitanTool);
            if (statusLoreIndexResponse.error() != null) {
                player.sendMessage(statusLoreIndexResponse.error());
                return true;
            }
            Response<ToolColor> toolColorResponse = ItemInfo.getColor(loreListResponse.value());
            if (toolColorResponse.error() != null) {
                player.sendMessage(toolColorResponse.error());
                return true;
            }
            ItemMeta meta = item.getItemMeta();
            List<String> loreListNoCharge = new ArrayList<>();
            for (int i = 0; i < loreListResponse.value().size(); i++) {
                if (i == chargeLoreIndexResponse.value()) continue;
                loreListNoCharge.add(loreListResponse.value().get(i));
            }

            meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
            meta.setLore(loreListNoCharge);
            item.setItemMeta(meta);
            return false;
        }

        if ("save".equalsIgnoreCase(args[0]) && args.length == 2) {
            if (!player.hasPermission(permissionStringMaker("save"))) {
                player.sendMessage(NO_PERMISSION);
                return false;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return true;
            plugin.getConfig().set(args[1], player.getInventory().getItemInMainHand());
            plugin.saveConfig();
        }

        if ("model".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission(permissionStringMaker("model"))) {
                player.sendMessage(NO_PERMISSION);
                return false;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return true;
            Integer customModelData = null;
            try {
                if (player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                    player.sendMessage("Previous custom model data: " + ChatColor.YELLOW + player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData());
                }
                customModelData = Integer.parseInt(args[1]);
                player.sendMessage("Setting custom model data to: " + ChatColor.GREEN + customModelData);
                ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                meta.setCustomModelData(customModelData);
                player.getInventory().getItemInMainHand().setItemMeta(meta);
            } catch (Exception ex) {
                player.sendMessage("Setting custom model data to: " + customModelData);
                ItemMeta meta = player.getInventory().getItemInMainHand().getItemMeta();
                meta.setCustomModelData(customModelData);
                player.getInventory().getItemInMainHand().setItemMeta(meta);
            }
        }
        if ("reload".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission(permissionStringMaker("reload"))) {
                player.sendMessage(NO_PERMISSION);
                return true;
            }
            plugin.getLogger().info("Reloading config...");
            plugin.reloadConfig();
            playerCommands.reload();
            configManager.loadConfig();
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }
        if ("crystal".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission(permissionStringMaker("crystal"))) {
                player.sendMessage(NO_PERMISSION);
                return true;
            }
            Inventory inv = player.getInventory();
            if (args.length == 1) {
                inv.addItem(ItemCreator.powerCrystalCommon);
                inv.addItem(ItemCreator.powerCrystalUncommon);
                inv.addItem(ItemCreator.powerCrystalSuper);
                inv.addItem(ItemCreator.powerCrystalEpic);
                inv.addItem(ItemCreator.powerCrystalUltra);
                player.updateInventory();
            } else if (args.length == 2) {
                try {
                    int amount = Integer.parseInt(args[1]);
                    for (int i = 0; i < amount; i++) {
                        inv.addItem(ItemCreator.powerCrystalCommon);
                    }
                } catch (Exception ex) {
                    player.sendMessage("You must provide an integer amount.");
                }
                player.updateInventory();
            }
            return true;
        }

        if ("excavator".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission(permissionStringMaker("excavator"))) {
                player.sendMessage(NO_PERMISSION);
                return true;
            }
            Inventory inv = player.getInventory();
            inv.addItem(ItemCreator.excavator);
            player.updateInventory();
            return true;
        }

        if ("crystalcheck".equalsIgnoreCase(args[0]) && player.hasPermission(permissionStringMaker("crystalcheck"))) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            player.sendMessage("Item is powercrystal: " + PowerCrystalInfo.isPowerCrystal(itemInMainHand));
            player.sendMessage("PowerCrystal type: " + PowerCrystalInfo.getPowerCrystalType(itemInMainHand));
            return true;
        }

        if ("compare".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission(permissionStringMaker("compare"))){
                player.sendMessage(NO_PERMISSION);
                return true;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInOffHand().getType() == Material.AIR) {
                player.sendMessage("You cannot compare air");
                return true;
            } else {
                player.sendMessage("isSimilar result: " + player.getInventory().getItemInMainHand().isSimilar(player.getInventory().getItemInOffHand()));             }
        }
        return false;
    }

    public String permissionStringMaker(String permission) {
        return permissionPrefix + "." + permission;
    }
}
