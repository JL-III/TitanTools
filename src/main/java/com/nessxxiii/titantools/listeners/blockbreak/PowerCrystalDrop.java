package com.nessxxiii.titantools.listeners.blockbreak;

import com.nessxxiii.titantools.events.PowerCrystalDropEvent;
import com.nessxxiii.titantools.items.ItemCreator;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.nessxxiii.titantools.util.Utils.getRandomNumber;

public class PowerCrystalDrop implements Listener {

    @EventHandler
    public void onPowerCrystalDrop(PowerCrystalDropEvent event) {
        Block block = event.getDropLocation().getBlock();
        block.setType(Material.AIR);
        handleDropPowerCrystal(event.getPlayerLocation(), event.getDropLocation());
    }

    private static void handleDropPowerCrystal(Location playerLocation, Location blockLocation) {
        int randomNumber = getRandomNumber(1,100);
        if (randomNumber > 95) {
            playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalUncommon);
            return;
        }
        if (randomNumber <= 3) {
            for (int i = 0; i < randomNumber; i++){
                playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalCommon);
            }
        } else {
            playerLocation.getWorld().dropItemNaturally(blockLocation, ItemCreator.powerCrystalCommon);
        }
    }
}
