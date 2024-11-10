package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.PowerCrystal;
import com.nessxxiii.titantools.enums.TheatriaTool;
import com.nessxxiii.titantools.enums.TitanTool;
import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.nessxxiii.titantools.utils.CustomLogger;
import com.nessxxiii.titantools.utils.Utils;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
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
        if (!permissionCheck(sender, Utils.PERMISSIONS_PREFIX, "tab-complete")) {
            return new ArrayList<>();
        }
        String input = args[0].toLowerCase();
        switch (input) {
            case "pack", "unpack" -> {
                return new ArrayList<>();
            }
            case "kit" -> {
                if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) return new ArrayList<>();
                if (args.length == 2) {
                    return new ArrayList<>(){{
                        addAll(Arrays.stream(TitanTool.values())
                                .map(Enum::name)
                                .map(String::toLowerCase)
                                .toList());
                    }};
                }
                if (args.length == 3) {
                    return Bukkit.getOnlinePlayers().stream().collect(ArrayList::new, (list, p) -> list.add(p.getName()), ArrayList::addAll);
                }
            }
            case "crystal" -> {
                if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) return new ArrayList<>();
                switch (args.length) {
                    case 1 -> {
                        return new ArrayList<>(){{
                            add("give");
                        }};
                    }
                    case 2 -> {
                        return Arrays.stream(PowerCrystal.values())
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
            }
            case "custom-item" -> {
                switch (args.length) {
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
            }
        }
        if (args.length == 1) {
            if (permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, "tab-complete")) {
                return new ArrayList<>(){{
                    add("check-pdc");
                    add("compare");
                    add("debug");
                    add("model");
                    add("reload");
                    add("save");
                    add("kit");
                    add("crystal");
                    add("custom-item");
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
        if (!permissionCheck(sender, Utils.PERMISSIONS_PREFIX, "use")) {
            return true;
        }
        String input = args[0].toLowerCase();
        switch (input) {
            case "pack" -> {
                packCommand(sender, input);
                return true;
            }
            case "unpack" -> {
                unpackCommand(sender, input);
                return true;
            }
            case "kit" -> {
                if (args.length == 3) {
                    kitCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "crystal" -> {
                if (args.length == 3) {
                    crystalCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "compare" -> {
                compareCommand(sender, input);
                return true;
            }
            case "custom-item" -> {
                if (args.length == 3) {
                    customItemCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "debug" -> {
                debugCommand(sender, input);
                return true;
            }
            case "check-pdc" -> {
                if (args.length == 2) {
                    checkPDCCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "model" -> {
                if (args.length == 2) {
                    setModelCommand(sender, args, input);
                    return true;
                }
                return false;
            }
            case "reload" -> {
                reloadCommand(sender, input);
            }
        }
        return false;
    }

    private void packCommand(CommandSender sender, String input) {
        if (!(sender instanceof Player player)) {
            return;
        }
        if (!permissionCheck(player, Utils.PERMISSIONS_PREFIX, input)) {
            return;
        }
        try {
            if (fileConfig.getString("resource-pack") == null || fileConfig.getString("resource-pack").length() < 3) {
                Utils.sendPluginMessage(player, ChatColor.RED + "File does not exist, please contact an admin for this issue.");
                throw new RuntimeException("File does not exist, please make sure the link is correct.");
            }
            Utils.sendPluginMessage(player, ChatColor.GREEN + "Downloading resource pack...");
            player.setResourcePack(fileConfig.getString("resource-pack"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void unpackCommand(CommandSender sender, String input) {
        if (!(sender instanceof Player player)) {
            return;
        }
        if (!permissionCheck(player, Utils.PERMISSIONS_PREFIX, input)) {
            return;
        }
        try {
            if (fileConfig.getString("resource-pack") == null) {
                throw new RuntimeException("File does not exist, please make sure the link is correct.");
            }
            Utils.sendPluginMessage(sender, "Unloading resource pack.");
            player.setResourcePack("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void kitCommand(CommandSender sender, String[] args, String input) {
        if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
        }
        if (Bukkit.getPlayer(args[2]) != null) {
            Player player = Bukkit.getPlayer(args[2]);
            if (player == null) return;
            String player_name = player.getName();
            Inventory inventory = player.getInventory();
            TitanTool titanTool;
            try {
                titanTool = Enum.valueOf(TitanTool.class, args[1].toUpperCase());
                Utils.reportResult(logger, args[1], inventory.addItem(titanTool.getItemStack()), player_name);
            } catch (IllegalArgumentException e) {
                Utils.sendPluginMessage(player, "You must provide a correct titan tool type.");
                Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide a correct titan tool type for /tkit <type> <username>.");
            }
        }
    }

    public void crystalCommand(CommandSender sender, String[] args, String input) {
        if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
        }
        if (!(sender instanceof Player player)) {
            return;
        }
        Inventory inv = player.getInventory();
        int amount;
        PowerCrystal powerCrystal;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            Utils.sendPluginMessage(player, "You must provide an integer amount.");
            Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide an integer amount for /titan crystal give <type> <amount>.");
            return;
        }
        try {
            powerCrystal = Enum.valueOf(PowerCrystal.class, args[1].toUpperCase());
            for (int i = 0; i < amount; i++) {
                inv.addItem(powerCrystal.getItemStack());
            }
        } catch (IllegalArgumentException ex) {
            Utils.sendPluginMessage(player, "You must provide a correct power crystal type.");
            Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide a correct power crystal type for /titan crystal give <type> <amount>.");
            return;
        }
        Utils.sendPluginMessage(player, "Added " + amount + " " + powerCrystal + " crystals.");
    }

    public void customItemCommand(CommandSender sender, String[] args, String input) {
        if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
        }
        if (!(sender instanceof Player player)) {
            return;
        }
        Inventory inv = player.getInventory();
        int amount;
        TheatriaTool theatriaTool;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException exception) {
            Utils.sendPluginMessage(player, "You must provide an integer amount.");
            Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide an integer amount for /titan custom-item <type> <amount>.");
            return;
        }
        try {
            theatriaTool = Enum.valueOf(TheatriaTool.class, args[1].toUpperCase());
            for (int i = 0; i < amount; i++) {
                inv.addItem(theatriaTool.getItemStack());
            }
        } catch (IllegalArgumentException ex) {
            Utils.sendPluginMessage(player, "You must provide a correct TheatriaTool type.");
            Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide a correct TheatriaTool type for /titan custom-item <type> <amount>.");
            return;
        }
        Utils.sendPluginMessage(player, "Added " + amount + " " + theatriaTool + " " + args[2] + ".");
    }

    private void debugCommand(CommandSender sender, String input) {
        if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) return;
        ItemStack itemStack = !(sender instanceof Player player) ? TitanTool.PICK_RED_FORTUNE.getItemStack() : player.getInventory().getItemInMainHand();
        Response<List<String>> loreResponse = ItemInfo.getLore(itemStack);
        if (!loreResponse.isSuccess()) return;
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
    }

    public void checkPDCCommand(CommandSender sender, String[] args, String input) {
        if (!(sender instanceof Player player)) return;
        if (!permissionCheck(player, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
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

    public void setModelCommand(CommandSender sender, String[] args, String input) {
        if (!(sender instanceof Player player)) return;
        if (!permissionCheck(player, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
        }
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR) {
            Utils.sendPluginMessage(player, "You must be holding an item.");
            return;
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
    }

    public void reloadCommand(CommandSender sender, String input) {
        if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
        }
        Utils.sendPluginMessage(sender, "Reloading config...");
        plugin.reloadConfig();
        Utils.sendPluginMessage(sender, ChatColor.GREEN + "Successfully reloaded config.");
    }

    public void compareCommand(CommandSender sender, String input) {
        if (!permissionCheck(sender, Utils.ADMIN_PERMISSIONS_PREFIX, input)) {
            return;
        }
        if (!(sender instanceof Player player)) return;
        if (player.getInventory().getItemInMainHand().getType() == Material.AIR || player.getInventory().getItemInOffHand().getType() == Material.AIR) {
            Utils.sendPluginMessage(player, "You cannot compare air");
        } else {
            Utils.sendPluginMessage(player, "isSimilar result: " + player.getInventory().getItemInMainHand().isSimilar(player.getInventory().getItemInOffHand()));
        }
    }

    public static boolean permissionCheck(CommandSender sender, String prefix, String permission) {
        if (sender.hasPermission(prefix + "." + permission)) {
            return true;
        }
        Utils.sendPluginMessage(sender, Utils.NO_PERMISSION + " " + prefix + permission);
        return false;
    }
}














