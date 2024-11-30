package com.nessxxiii.titantools.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Utils {

    private static final ChatColor BRACKET_COLOR = ChatColor.DARK_RED;

    private static final ChatColor PLUGIN_COLOR = ChatColor.WHITE;

    private static final String PLUGIN_PREFIX = BRACKET_COLOR + "[" + PLUGIN_COLOR + "TitanTools" + BRACKET_COLOR + "] " + ChatColor.RESET;

    public static final String PERMISSIONS_PREFIX = "titan.tools.commands";

    public static final String ADMIN_PERMISSIONS_PREFIX = PERMISSIONS_PREFIX + ".admin";

    public static final String NO_PERMISSION = ChatColor.RED + "You cannot use permission ";

    public static void printBanner(CommandSender sender) {
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "################################################################################");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "#####   ###   #####    #    #   #        #####   ###    ###   #       ###   " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #      #      #     # #   ##  #          #    #   #  #   #  #      #      " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #      #      #    #####  # # #          #    #   #  #   #  #       ###   " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #      #      #    #   #  #  ##          #    #   #  #   #  #          #  " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #     ###     #    #   #  #   #          #     ###    ###   #####   ###   " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.LIGHT_PURPLE + "##############################################################################");
        sendPluginMessage(sender, ChatColor.GOLD + "By: NessXXIII");
    }

    public static void sendPluginMessage(CommandSender sender, String message) {
        sender.sendMessage(PLUGIN_PREFIX + message);
    }

    public static void addItemAndReportResult(CustomLogger logger, String toolName, ItemStack itemStack, HashMap<Integer, ItemStack> droppedItems, String player_name) {
        if (droppedItems.isEmpty()) {
            logger.sendLog(player_name + " received their kit " + toolName);
            logger.sendLog("Type: " + itemStack.getType() + " Amount: " + itemStack.getAmount());
        } else {
            logger.sendLog(player_name + " did not receive their " + itemStack + " due to a full inventory.");
            for (Map.Entry<Integer,ItemStack> itemStackEntry : droppedItems.entrySet()) {
                logger.sendLog("Type: " + itemStackEntry.getValue().getType() + " Amount: " + itemStackEntry.getKey());
            }
        }
    }

    public static Component gradientText(String text, String startHex, String endHex) {
        if (!isValidHexColor(startHex) || !isValidHexColor(endHex)) {
            throw new IllegalArgumentException("Invalid hex color string provided.");
        }

        String miniMessageString = "<gradient:" + startHex + ":" + endHex + ">" + text + "</gradient>";
        return MiniMessage.miniMessage().deserialize(miniMessageString);
    }

    private static boolean isValidHexColor(String hex) {
        return HEX_COLOR_PATTERN.matcher(hex).matches();
    }

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6})$");

    public static boolean permissionCheck(CommandSender sender, String prefix, String permission) {
        if (sender.hasPermission(prefix + "." + permission)) {
            return true;
        }
        Utils.sendPluginMessage(sender, Utils.NO_PERMISSION + ": " + prefix + "." + permission);
        return false;
    }
}
