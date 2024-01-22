package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.events.tools.titan.enchants.TitanToolEntityDeathEvent;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class TitanToolEntityDeath implements Listener {

    private final Debugger debugger;

    public TitanToolEntityDeath(Debugger debugger) {
        this.debugger = debugger;
    }

    @EventHandler
    public void onTitanToolEntityDeath(TitanToolEntityDeathEvent event) {
        List<String> lore = event.getPlayer().getInventory().getItemInMainHand().getLore();
        Utils.processChargeManagement(event.getPlayer(), debugger, event.getPlayer().getInventory().getItemInMainHand(), lore);
        event.getPlayer().giveExp(event.getDroppedExp() * 2);
    }
}
