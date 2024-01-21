package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.events.tools.titan.enchants.HoeBlockBreakEvent;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HoeBlockBreak implements Listener {

    @EventHandler
    public void onHoeBlockBreak(HoeBlockBreakEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "HoeBlockBreakEvent");
    }
}
