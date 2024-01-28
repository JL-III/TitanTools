package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.generalutils.Utils;
import com.playtheatria.jliii.generalutils.events.tools.titan.enchants.HoeBlockBreakEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class HoeBlockBreak implements Listener {

    @EventHandler
    public void onHoeBlockBreak(HoeBlockBreakEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "HoeBlockBreakEvent");
    }
}
