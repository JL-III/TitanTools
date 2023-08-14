package com.nessxxiii.titanenchants.util;

import com.playtheatria.jliii.generalutils.items.TitanItemInfo;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static void printBanner() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "   _____   _____   _        ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |    /_\\  |\\ |");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |   /   \\ | \\| enchants");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "               NessXXIII");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
    }

    public record ItemRecord(
            boolean hasItemMeta,
            boolean isTitanTool,
            boolean isAllowedTitanType,
            boolean isCharged,
            boolean isImbued,
            boolean isActive
    ) {};

    public static ItemRecord retrieveItemRecord(ItemStack itemStack) {
        return new ItemRecord(
                itemStack.hasItemMeta(),
                TitanItemInfo.isTitanTool(itemStack),
                TitanItemInfo.isAllowedTitanType(itemStack),
                TitanItemInfo.hasCharge(itemStack),
                TitanItemInfo.isImbued(itemStack),
                (TitanItemInfo.isActiveCharged(itemStack) || TitanItemInfo.isActiveImbued(itemStack))
        );
    }

}
