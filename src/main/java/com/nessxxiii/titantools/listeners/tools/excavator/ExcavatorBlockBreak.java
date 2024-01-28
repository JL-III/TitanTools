package com.nessxxiii.titantools.listeners.tools.excavator;

import com.nessxxiii.titantools.generalutils.Utils;
import com.playtheatria.jliii.generalutils.events.crystals.PowerCrystalDropEvent;
import com.playtheatria.jliii.generalutils.events.tools.excavator.ExcavatorBlockBreakEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ExcavatorBlockBreak implements Listener {

    @EventHandler
    public void onExcavatorBlockBreak(ExcavatorBlockBreakEvent event) {
        Block block = event.getDropLocation().getBlock();
        block.setType(Material.AIR);
        int randomNumber = Utils.getRandomNumber(1,1000);
        event.getPlayerLocation().getWorld().dropItemNaturally(event.getDropLocation(), Utils.getPowerCrystalType(randomNumber));
        Bukkit.getPluginManager().callEvent(new PowerCrystalDropEvent());
    }
}
