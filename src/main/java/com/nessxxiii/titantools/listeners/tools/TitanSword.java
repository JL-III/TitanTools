package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

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
        Player player = event.getEntity().getKiller();
        ChargeManagement.decreaseChargeLore(debugger, player.getInventory().getItemInMainHand(), listResponse.value(), true, true, player);
    }

}
