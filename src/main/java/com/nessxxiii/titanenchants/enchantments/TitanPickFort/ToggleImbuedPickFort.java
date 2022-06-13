package com.nessxxiii.titanenchants.enchantments.TitanPickFort;

import com.nessxxiii.titanenchants.util.ItemInfo;
import com.nessxxiii.titanenchants.util.ToggleImbuedItem;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ToggleImbuedPickFort implements Listener {

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
        if (!ItemInfo.isImbued(item)) return;
        if (player.hasCooldown(coolDown)) return;
        player.setCooldown(coolDown,25);
        if (!player.hasPermission("benchants.toggle")) return;
        event.setCancelled(true);
        ToggleImbuedItem.toggleImbuedEnchantment(item,player);
    }
}
