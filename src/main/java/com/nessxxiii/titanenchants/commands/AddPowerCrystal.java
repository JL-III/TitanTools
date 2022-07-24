package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.Items.ItemManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

public class AddPowerCrystal implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        Player player = (Player) sender;
        if (!player.hasPermission("PowerCrystal.add")) return false;
        Inventory inv = player.getInventory();
        inv.addItem(ItemManager.powerCrystal);
        player.updateInventory();
        return true;
    }

}
