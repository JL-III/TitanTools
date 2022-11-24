package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.items.ItemCreator;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerCommands implements CommandExecutor{

    private Plugin plugin;

    public PlayerCommands(Plugin plugin) {
        this.plugin = plugin;
    };

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) return false;
        if (!player.hasPermission("titan.enchants")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length == 0) return false;
        if ("imbue".equalsIgnoreCase(args[0]) ){
            Material coolDown = Material.SQUID_SPAWN_EGG;
            ItemStack item = player.getInventory().getItemInMainHand();
            if (!ItemInfo.isTitanTool(item)) return false;
            if (!player.hasPermission("titan.enchants.imbue")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return false;
            }
            if (ItemInfo.isImbued(item)) {
                player.sendMessage(ChatColor.GREEN + "That item is already imbued!");
                return false;
            }
            if (!player.hasCooldown(coolDown)) {
                player.sendMessage(ChatColor.GREEN + "Are you sure you want to imbue this tool?");
                player.sendMessage(ChatColor.GREEN + "Retype the command to confirm");
                player.setCooldown(coolDown, 200);
                return false;
            }
            List<String> loreList = ItemInfo.getLore(item);
            for (String lore : loreList) {
                if (ItemInfo.UNIMBUED_LORE.contains(lore)) {
                    ToggleAncientPower.imbue(item);
                    TitanEnchantEffects.enableEffect(player);
                    plugin.getLogger().info(player.getName() + " has imbued a titan tool...");
                    return true;
                }
            }
            return false;
        }


        if ("check".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.check")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return false;
            }
            if(ItemInfo.isChargedOrImbuedTitanPick(player.getInventory().getItemInMainHand())) {
                player.sendMessage("This is a titan pick and is imbued or charged");
            }
            else {
                player.sendMessage("This is not a titan pick or is not imbued or charged");
            }
        }
        if ("reload".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.reload")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            plugin.getLogger().info("Reloading config...");
            plugin.reloadConfig();
            player.sendMessage(ChatColor.GREEN + "Successfully reloaded config.");
            return true;
        }
        if ("crystal".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.powercrystal.add")) {
                player.sendMessage(ChatColor.RED + "No permission.");
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
            if (!player.hasPermission("titan.enchants.excavator.add")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            Inventory inv = player.getInventory();
            inv.addItem(ItemCreator.excavator);
            player.updateInventory();
            return true;
        }

        if ("crystalcheck".equalsIgnoreCase(args[0]) && player.hasPermission("titan.enchants.crystalcheck")) {
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();

            player.sendMessage("Item is powercrystal: " + ItemInfo.isPowerCrystal(itemInMainHand));
            player.sendMessage("PowerCrystal type: " + ItemInfo.getPowerCrystalType(itemInMainHand));
            return true;
        }

//        if ("message".equalsIgnoreCase(args[0])) {
//            var mm = MiniMessage.miniMessage();
//
//            Component parsed = mm.deserialize("Hello <rainbow>world</rainbow>, isn't <blue><u><click:open_url:'https://docs.adventure.kyori.net/minimessage'>MiniMessage</click></u></blue> fun?");
//
//            player.sendMessage(parsed);
//            return true;
//        }

        //TODO move this method to TheatriaUtils
//        if ("lastseen".equalsIgnoreCase(args[0])) {
//            OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
//
//            Arrays.sort(allPlayers, Comparator.comparing(OfflinePlayer::getLastLogin));
//
//            for (OfflinePlayer offlinePlayer : allPlayers) {
//                Calendar calendar = Calendar.getInstance();
//                calendar.setTimeInMillis(offlinePlayer.getLastLogin());
//                Bukkit.getConsoleSender().sendMessage(calendar.get(Calendar.MONTH) + "/" +
//                        calendar.get(Calendar.DAY_OF_MONTH) + "/" + calendar.get(Calendar.YEAR) + "  " + offlinePlayer.getName());
//            }
//        }

        if ("getInfo".equalsIgnoreCase(args[0])) {
            NBTItem item = new NBTItem(player.getInventory().getItemInMainHand());
            player.sendMessage("hasCharge: " + item.getBoolean("hasCharge"));
            player.sendMessage("isImbued: " + item.getBoolean("isImbued"));
            player.sendMessage("isActive: " + item.getBoolean("isActive"));
            player.sendMessage("itemLevel: " + item.getInteger("itemLevel"));
        }
        return true;
    }
}














