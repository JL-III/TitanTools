package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.items.ItemCreator;
import com.nessxxiii.titanenchants.listeners.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Cat;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PlayerCommands implements CommandExecutor{

    private Plugin plugin;
    private FileConfiguration fileConfig;

    public PlayerCommands(Plugin plugin, FileConfiguration fileConfig) {
        this.plugin = plugin;
        this.fileConfig = fileConfig;
    };

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if (!(sender instanceof Player player)) {
            return false;
        }
        if ("pack".equalsIgnoreCase(args[0])) {
            try {
                player.setResourcePack(fileConfig.getString("resource-pack"));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (!player.hasPermission("titan.enchants")) {
            player.sendMessage(ChatColor.RED + "No permission.");
            return true;
        }
        if (args.length == 0) return false;
        if ("imbue".equalsIgnoreCase(args[0])) {
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
        if ("debug".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.debug")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return false;
            }
            player.sendMessage("Current RP link: " + fileConfig.getString("resource-pack") );
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return true;
            if (player.getInventory().getItemInMainHand().hasItemMeta() && player.getInventory().getItemInMainHand().getItemMeta().hasCustomModelData()) {
                player.sendMessage("Current custom model data: " + player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData());
            } else {
                player.sendMessage("This item does not have custom model data.");
            }
        }

        if ("save".equalsIgnoreCase(args[0]) && args.length == 2) {
            if (!player.hasPermission("titan.enchants.save")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return false;
            }
            if (player.getInventory().getItemInMainHand().getType() == Material.AIR) return true;
            plugin.getConfig().set(args[1], player.getInventory().getItemInMainHand());
            plugin.saveConfig();
            plugin.getLogger().info("Reloading config...");
            fileConfig = plugin.getConfig();
        }

        if ("model".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.setmodeldata")) {
                player.sendMessage(ChatColor.RED + "No permission.");
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

        if ("check".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.check")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return false;
            }
            player.sendMessage("TitanPick isChargedOrImbued: " + ItemInfo.isChargedOrImbuedTitanPick(player.getInventory().getItemInMainHand()));
            player.sendMessage("Current Custom model data: " + player.getInventory().getItemInMainHand().getItemMeta().getCustomModelData());

        }
        if ("reload".equalsIgnoreCase(args[0])) {
            if (!player.hasPermission("titan.enchants.reload")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            plugin.getLogger().info("Reloading config...");
            plugin.reloadConfig();
            fileConfig = plugin.getConfig();
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
//                inv.addItem(ItemCreator.powerCrystalTEST);
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

        if ("spawn".equalsIgnoreCase(args[0]) && player.hasPermission("titan.enchants.spawn")) {
            Cat entity = (Cat) player.getWorld().spawnEntity(player.getLocation(), EntityType.CAT);
            entity.setOwner(player);
            entity.setAge(-2);
            entity.setAgeLock(true);
            entity.setInvulnerable(true);
            entity.setMetadata("customModelData", new FixedMetadataValue(plugin, 1234567));
        }

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
    return true;
    }
}














