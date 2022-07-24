package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.Items.ItemManager;
import com.nessxxiii.titanenchants.enchantmentManager.ToggleAncientPower;
import com.nessxxiii.titanenchants.util.ItemInfo;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PlayerCommands implements CommandExecutor{

    private Plugin plugin;

    public PlayerCommands(Plugin plugin){
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
            Material coolDown = Material.JIGSAW;
            player.sendMessage(ChatColor.RED + "------Debug------");
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
                player.sendMessage(ChatColor.GREEN + "Are you sure you want to imbue this tool for $1,000,000?");
                player.sendMessage(ChatColor.GREEN + "Retype the command to confirm");
                player.setCooldown(coolDown, 200);
                return false;
            }
            List<String> loreList = item.getItemMeta().getLore();
            if (loreList == null) return false;
            for (String lore : loreList) {
                if (ItemInfo.UNIMBUED_LORE.contains(lore)) {
                    ToggleAncientPower.imbue(item);
                    new TitanEnchantEffects().enableEffect(player);
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
            if (!player.hasPermission("titan.echant.powercrystal.add")) {
                player.sendMessage(ChatColor.RED + "No permission.");
                return true;
            }
            Inventory inv = player.getInventory();
            inv.addItem(ItemManager.powerCrystal);
            player.updateInventory();
            return true;
        }
        return true;
    }
}
