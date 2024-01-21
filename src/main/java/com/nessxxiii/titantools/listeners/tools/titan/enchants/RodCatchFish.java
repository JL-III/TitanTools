package com.nessxxiii.titantools.listeners.tools.titan.enchants;

import com.nessxxiii.titantools.events.tools.titan.enchants.RodCatchFishEvent;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class RodCatchFish implements Listener {

    private final Debugger debugger;

    public RodCatchFish(Debugger debugger) {
        this.debugger = debugger;
    }

    @EventHandler
    public void onRodCatchFish(RodCatchFishEvent event) {
        Utils.sendPluginMessage(event.getPlayer(), "RodCatchFishEvent: " + event.getCaughtItem().getItemStack().getType().name());

        List<String> lore = event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getLore();

        Utils.processChargeManagement(event.getPlayer(), debugger, event.getPlayer().getInventory().getItemInMainHand(), lore);

        ItemStack caughtItem = event.getCaughtItem().getItemStack();

        switch (caughtItem.getType()) {
            case COD, SALMON, PUFFERFISH:
                caughtItem.add(2);
                break;
            case TROPICAL_FISH:
                caughtItem.add(1);
                break;
            default:
                for (Enchantment enchantment : caughtItem.getEnchantments().keySet()) {
                    caughtItem.removeEnchantment(enchantment);
                }
                caughtItem.setType(Material.TROPICAL_FISH);
                caughtItem.add(1);
                break;
        }

        event.getPlayer().getWorld().dropItem(event.getCaughtItem().getLocation(), caughtItem).setVelocity(event.getVelocity());
    }
}
