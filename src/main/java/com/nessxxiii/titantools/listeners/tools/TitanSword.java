package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

import java.util.List;

public class TitanSword implements Listener {
    private final Debugger debugger;

    public TitanSword(Debugger debugger) {
        this.debugger = debugger;
    }

    @EventHandler
    public void onMobKill(EntityDeathEvent event) {
        Response<List<String>> listResponse = Utils.titanSwordValidationDeathEvent(event);
        if (listResponse.error() != null) {
            debugger.sendDebugIfEnabled(listResponse.error());
            return;
        }
        debugger.sendDebugIfEnabled("EntityDeathEvent: This is a titan sword with a status of ON");
        debugger.sendDebugIfEnabled("Exp before modifier: " + event.getDroppedExp());
        event.setDroppedExp(event.getDroppedExp() * 2);
        debugger.sendDebugIfEnabled("Exp after modifier: " + event.getDroppedExp());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMobDamage(EntityDamageByEntityEvent event) {
        Response<List<String>> listResponse = Utils.titanSwordValidationDamageEvent(event);
        if (listResponse.error() != null) {
            debugger.sendDebugIfEnabled(listResponse.error());
            return;
        }
        debugger.sendDebugIfEnabled("EntityDamageByEntityEvent: this item is a titan sword with the status of ON");
        debugger.sendDebugIfEnabled("Damage before modifier: " + event.getDamage());
        event.setDamage(event.getDamage() * 2);
        debugger.sendDebugIfEnabled("Damage after modifier: " + event.getDamage());
    }
}
