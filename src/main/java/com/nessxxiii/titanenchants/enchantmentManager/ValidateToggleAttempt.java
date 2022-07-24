package com.nessxxiii.titanenchants.enchantmentManager;

import com.nessxxiii.titanenchants.util.ItemInfo;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ValidateToggleAttempt {

    private static PlayerInteractEvent event;

    public ValidateToggleAttempt(PlayerInteractEvent event) {
        this.event = event;
    };

    public boolean processValidation() {
        Player player = event.getPlayer();
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        if (!player.hasPermission("benchants.charge.toggle")) return false;
        return true;
    }

    public boolean processItemValidation(ItemStack item){
        if (!ItemInfo.isAllowedType(item)) return false;
        if (!item.getEnchantments().containsKey(Enchantment.LOOT_BONUS_BLOCKS)) return false;
        if (!item.hasItemMeta()) return false;
        if (!ItemInfo.isTitanTool(item)) return false;
        if (!ItemInfo.hasCharge(item) && !ItemInfo.isImbued(item)) return false;
        return true;
    }
}
