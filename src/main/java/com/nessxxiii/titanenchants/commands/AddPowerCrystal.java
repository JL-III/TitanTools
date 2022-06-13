package com.nessxxiii.titanenchants.commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddPowerCrystal implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        if (!player.hasPermission("PowerCrystal.add")) return false;
        ItemStack powerCrystal = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add("§x§F§F§0§0§4§CAncient Charge");
        powerCrystal.setLore(lore);
        ItemMeta meta = powerCrystal.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Power Crystal");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        powerCrystal.setItemMeta(meta);
        powerCrystal.addUnsafeEnchantment(Enchantment.CHANNELING,1);
        Inventory inv = player.getInventory();
        inv.addItem(powerCrystal);
        player.updateInventory();
        return true;

    }
}
