package com.nessxxiii.titantools.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class Utils {

    private static final ChatColor BRACKET_COLOR = ChatColor.DARK_RED;

    private static final ChatColor PLUGIN_COLOR = ChatColor.WHITE;

    private static final String PLUGIN_PREFIX = BRACKET_COLOR + "[" + PLUGIN_COLOR + "TitanTools" + BRACKET_COLOR + "] " + ChatColor.RESET;

    public static final String PERMISSION_PREFIX_ADMIN = "titan.tools.admincommands";

    public static final String NO_PERMISSION = ChatColor.RED + "No Permission.";

    public static final HashMap<Integer, String> romanNumeralMap = new HashMap<>(){{
       put(1, "I");
       put(2, "II");
       put(3, "III");
       put(4, "IV");
       put(5, "V");
       put(6, "VI");
       put(7, "VII");
       put(8, "VIII");
       put(9, "IX");
       put(10, "X");
       put(11, "XI");
       put(12, "XII");
       put(13, "XIII");
       put(14, "XIV");
       put(15, "XV");
    }};

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

    public static boolean permissionCheck(Player player, String permission) {
        if (!player.hasPermission(Utils.PERMISSION_PREFIX_ADMIN + "." + permission)) {
            Utils.sendPluginMessage(player, Utils.NO_PERMISSION);
            return false;
        }
        return true;
    }
}
