package com.nessxxiii.titantools.utils;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Utils {

    private static final ChatColor BRACKET_COLOR = ChatColor.DARK_RED;

    private static final ChatColor PLUGIN_COLOR = ChatColor.WHITE;

    private static final String PLUGIN_PREFIX = BRACKET_COLOR + "[" + PLUGIN_COLOR + "TitanTools" + BRACKET_COLOR + "] " + ChatColor.RESET;

    public static final String PERMISSION_PREFIX_ADMIN = "titan.tools.admincommands";

    public static final String NO_PERMISSION = ChatColor.RED + "No Permission.";

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
}
