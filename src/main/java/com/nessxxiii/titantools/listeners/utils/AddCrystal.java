package com.nessxxiii.titantools.listeners.utils;

import com.nessxxiii.titantools.events.utils.AddCrystalEvent;
import com.nessxxiii.titantools.itemmanagement.ItemCreator;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class AddCrystal implements Listener {

    @EventHandler
    public void onAddCrystal(AddCrystalEvent event) {
        Player player = event.getPlayer();
        Inventory inv = event.getPlayer().getInventory();
        String[] args = event.getArgs();

        if (args.length == 1) {
            inv.addItem(ItemCreator.powerCrystalCommon);
            inv.addItem(ItemCreator.powerCrystalUncommon);
            inv.addItem(ItemCreator.powerCrystalSuper);
            inv.addItem(ItemCreator.powerCrystalEpic);
            inv.addItem(ItemCreator.powerCrystalUltra);
            Utils.sendPluginMessage(player, "Added 1 of each crystal.");
        } else if (args.length == 2) {
            try {
                int amount = Integer.parseInt(args[1]);
                for (int i = 0; i < amount; i++) {
                    inv.addItem(ItemCreator.powerCrystalCommon);
                }
            } catch (Exception ex) {
                Utils.sendPluginMessage(player, "You must provide an integer amount.");
                Utils.sendPluginMessage(Bukkit.getConsoleSender(), "Error: " + "Player " + player.getName() + " Failed to provide an integer amount for /atitan crystal <number>.");
                return;
            }
            Utils.sendPluginMessage(player, "Added " + args.length + " common crystals.");
        }
    }
}
