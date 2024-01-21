package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.events.tools.ExcavatorBlockBreakEvent;
import com.nessxxiii.titantools.items.ItemCreator;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import static com.nessxxiii.titantools.util.Utils.getRandomNumber;

public class ExcavatorBlockBreak implements Listener {

    @EventHandler
    public void onExcavatorBlockBreak(ExcavatorBlockBreakEvent event) {
        Block block = event.getDropLocation().getBlock();
        block.setType(Material.AIR);
        int randomNumber = getRandomNumber(1,1000);
        Utils.sendPluginMessage(event.getPlayer(), "RNG result: " + randomNumber);

        if (randomNumber > 995) {
            event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), ItemCreator.powerCrystalUltra);
            return;
        }
        if (randomNumber > 990) {
            event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), ItemCreator.powerCrystalEpic);
            return;
        }
        if (randomNumber > 985) {
            event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), ItemCreator.powerCrystalSuper);
            return;
        }
        if (randomNumber > 900) {
            event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), ItemCreator.powerCrystalUncommon);
            return;
        }
        if (randomNumber <= 10) {
            for (int i = 0; i < randomNumber; i++){
                event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), ItemCreator.powerCrystalCommon);
            }
        } else {
            event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), ItemCreator.powerCrystalCommon);
        }
    }
}
