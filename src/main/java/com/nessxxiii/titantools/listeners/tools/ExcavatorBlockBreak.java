package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.events.PowerCrystalDropEvent;
import com.nessxxiii.titantools.events.tools.ExcavatorBlockBreakEvent;
import com.nessxxiii.titantools.items.ItemCreator;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ExcavatorBlockBreak implements Listener {

    @EventHandler
    public void onExcavatorBlockBreak(ExcavatorBlockBreakEvent event) {
        Block block = event.getDropLocation().getBlock();
        block.setType(Material.AIR);
        int randomNumber = Utils.getRandomNumber(1,1000);
        event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), getPowerCrystalType(randomNumber));
        Bukkit.getPluginManager().callEvent(new PowerCrystalDropEvent());
    }

    public ItemStack getPowerCrystalType(int randomNumber) {
        ItemStack powerCrystal = ItemCreator.powerCrystalCommon;
        if (randomNumber > 995) {
            powerCrystal = ItemCreator.powerCrystalUltra;
        } else if (randomNumber > 990) {
            powerCrystal = ItemCreator.powerCrystalEpic;
        } else if (randomNumber > 985) {
            powerCrystal = ItemCreator.powerCrystalSuper;
        } else if (randomNumber > 900) {
            powerCrystal = ItemCreator.powerCrystalUncommon;
        }
        return powerCrystal;
    }
}
