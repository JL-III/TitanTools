package com.nessxxiii.titanenchants.enchantments.TitanPickSilk;

import com.nessxxiii.titanenchants.util.ItemInfo;
import com.nessxxiii.titanenchants.util.ToggleChargedItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ToggleChargedPickSilk implements Listener {

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Material coolDown = Material.JIGSAW;

        if (!event.getAction().isRightClick()) return;
        if (!player.isSneaking()) return;
        ItemStack item = player.getInventory().getItemInMainHand();
        if (!ItemInfo.isAllowedType(item)) return;
        if (!item.getEnchantments().containsKey(Enchantment.SILK_TOUCH)) return;
        if (!item.hasItemMeta()) return;
        if (!ItemInfo.isTitanTool(item)) return;
        if (!ItemInfo.hasCharge(item)) return;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,25);
        if (!player.hasPermission("benchants.charge.toggle")) return;
        player.sendMessage("Passed activateClick checks");
        event.setCancelled(true);
        ToggleChargedItem.toggleChargedEnchant(item,player);
    }

}
