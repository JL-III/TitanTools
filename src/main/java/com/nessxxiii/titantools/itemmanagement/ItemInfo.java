package com.nessxxiii.titantools.itemmanagement;

import com.nessxxiii.titantools.enums.ToolColor;
import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.generalutils.Response;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ItemInfo {

    public static final String ANCIENT_POWER_STRING = "Ancient Power";
    public static final String ANCIENT_POWER_SYMBOL = "Ω";
    public static final String EXCAVATION_TOOL_DISPLAY_NAME = "§x§f§b§b§5§0§0§lE§x§f§6§a§7§1§3§lx§x§f§1§9§9§2§7§lc§x§e§c§8§b§3§a§la§x§e§7§7§d§4§e§lv§x§e§2§6§f§6§1§la§x§d§d§6§1§7§5§lt§x§d§9§5§4§8§8§li§x§d§4§4§6§9§c§lo§x§c§f§3§8§a§f§ln §x§c§a§2§a§c§3§lT§x§c§5§1§c§d§6§lo§x§c§0§0§e§e§a§lo§x§b§b§0§0§f§d§ll   ";
    public static final String STATUS_PREFIX = "● Status";
    public static final String CHARGE_PREFIX = "● Charge";
    public static final String INVALID_TOOL_CHECK = "Invalid call, This method is meant only for TitanTools!";
    public static final List<Material> ALLOWED_TITAN_TYPES = new ArrayList<>() {
        {
            add(Material.DIAMOND_PICKAXE);
            add(Material.NETHERITE_PICKAXE);
            add(Material.DIAMOND_SHOVEL);
            add(Material.NETHERITE_SHOVEL);
            add(Material.DIAMOND_AXE);
            add(Material.NETHERITE_AXE);
//            add(Material.DIAMOND_HOE);
//            add(Material.NETHERITE_HOE);
            add(Material.DIAMOND_SWORD);
            add(Material.NETHERITE_SWORD);
            add(Material.FISHING_ROD);
        }
    };

    /**
     * Get the titan lore index.
     * <p>
     *     Iterates through a list of lore and checks if any of the strings contain the provided PREFIX. The PREFIX should be a static constant defined in this class.
     *     The method requires a Response Boolean object in order to ensure that an isTitanTool check was made before calling this method.
     * </p>
     * @param loreList A list of lore to check.
     * @param PREFIX a static constant defined in this class.
     * @param isTitanTool boolean returned from an isTitanTool check.
     * @return Response Integer Object. The integer is populated or an error is placed into the object.
     * */
    public static Response<Integer> getTitanLoreIndex(@NotNull List<String> loreList, String PREFIX, boolean isTitanTool) {
        if (!isTitanTool) return Response.failure(INVALID_TOOL_CHECK);
        for (int i = 0; i < loreList.size(); i++){
            if (loreList.get(i).contains(PREFIX)) return Response.success(i);
        }
        return Response.failure("No " + PREFIX + " lore index was found.");
    }

    /**
     * Generate the status lore - Used when toggling TitanTool status ON and OFF
     * <p>
     *     Returns a preformatted string with the new desired status and color.
     * </p>
     * @param color The ToolColor of the tool to be modified.
     * @param status The desired status of the tool to be modified.
     * @return The formatted string with the appropriate color and status.
     * @see ToolStatus ToolStatus for information on the ToolStatus
     * @see ToolColor ToolColor for information on the ToolColor.
     * */
    public static String generateStatusLore(@NotNull ToolColor color, @NotNull ToolStatus status) {
        return color.getDarkColorCode() + "  " + STATUS_PREFIX + color.getBrightColorCode() + " " + status.getString();
    }

    /**
     * Generate the charge lore - Used when increasing or decreasing charge lore.
     * <p>
     *     Returns a preformatted string with the new desired charge amount and color.
     * </p>
     * @param color The ToolColor of the tool to be modified.
     * @param amount The desired amount to be set for the charge.
     * @return The formatted string with the appropriate color and status.
     * @see ToolStatus ToolStatus for information on the ToolStatus
     * @see ToolColor ToolColor for information on the ToolColor.
     * */
    public static String generateChargeLore(@NotNull ToolColor color, int amount) {
        return color.getDarkColorCode() + "  " + CHARGE_PREFIX + color.getBrightColorCode() + " " + amount;
    }

    /**
     * Returns a Response Integer Object, if successful an Integer value is returned, otherwise the error is not null and can be parsed for more information.
     * <p></p>
     * <p>
     *     This method iterates through a list of strings and checks if any match the CHARGE_PREFIX constant found in the TitanItemInfo class.
     *     Once found the method returns a parsed integer to the caller.
     * </p>
     * @param lore list of strings retrieved from the Titan tool
     * @param isTitanTool boolean returned from an isTitanTool check.
     * @param hasChargeLore boolean result from hasChargeLore check.
     * @param offset The offset of the substring since we are parsing the integer at the end of the string, the caller must handle any exception that is thrown.
     * @return Returns the Response Integer Object on a Titan tool.
     * */

    public static Response<Integer> getCharge(@NotNull List<String> lore, boolean isTitanTool, boolean hasChargeLore, int offset) {
        if (!isTitanTool) return Response.failure(INVALID_TOOL_CHECK);
        if (!hasChargeLore) return Response.failure("This item is not a charged item, this is an error!");
        for (String string : lore) {
            if (string.contains(CHARGE_PREFIX)) {
                try {
                    return Response.success(Integer.parseInt(string.substring(offset)));
                } catch (NumberFormatException exception) {
                    return Response.failure("Could not parse integer from charge lore index");
                }
            }
        }
        return Response.failure("No charge lore was found in this set or lore.");
    }
    /**
     * Checks to see if a TitanTool has charge.
     * <p>
     *     This method validates if the isTitanToolResponse is successful and true, if so then it iterates over lore to check if the CHARGE_PREFIX exists on the lore.
     * </p>
     * @param loreList List of lore to iterate over.
     * @param isTitanTool boolean returned from an isTitanTool check.
     * @return Boolean true or false value.
     * */
    public static boolean hasChargeLore(List<String> loreList, boolean isTitanTool) {
        if (!isTitanTool) return false;
        for (String lore : loreList) {
            if (lore.contains(CHARGE_PREFIX)) return true;
        }
        return false;
    }

    public static boolean isTitanTool(List<String> loreList){
        for (String lore : loreList) {
            if (lore.contains(ANCIENT_POWER_STRING) && lore.contains(ANCIENT_POWER_SYMBOL)) return true;
        }
        return false;
    }

    public static boolean isChargedTitanTool(List<String> loreList, boolean isTitanTool) {
        if (!isTitanTool) return false;
        for (String lore : loreList) {
            if (lore.contains(CHARGE_PREFIX)) return true;
        }
        return false;
    }

    /**
     * Check to see if a tool is an imbued tool.
     * <p>
     *     This method checks if list of lore contains the charge prefix.
     *     It is assumed that if the charge prefix exists on a titan tool then it is not an imbued tool.
     *     The method requires an isTitanToolResponse and must be successful before being checked for the charge prefix.
     *     This is to ensure we are first checking if a tool is a titan tool before bothering with further checks.
     * </p>
     * @param loreList A list of lore taken from an item.
     * @param isTitanTool boolean returned from an isTitanTool check.
     * @return Returns a Response Boolean type.
     *
     * */
    public static boolean isImbuedTitanTool(List<String> loreList, boolean isTitanTool) {
        if (!isTitanTool) return false;
        for (String lore : loreList) {
            if (lore.contains(CHARGE_PREFIX)) return false;
        }
        return true;
    }

    /**
     * Checks to see if the item type matches the list of allowed types.
     * <p></p>
     * <p>
     *     This method checks if the ItemStack provided is a material that is within the List of Material Enum provided.
     *     Once found the method returns a parsed integer to the caller.
     * </p>
     * @param item The ItemStack being checked
     * @param ALLOWED_TYPES A list of Material Enums that are allowed.
     * @return Returns true if the ItemStack's material is within the ALLOWED_TYPES list.
     * */
    public static boolean isAllowedType(@NotNull ItemStack item, List<Material> ALLOWED_TYPES){
        return (ALLOWED_TYPES.contains(item.getType()));
    }

    /**
     * Retrieves a List of Strings if lore exists on an ItemStack
     * <p>
     *     This is a helper method to check if an item has meta data and lore, if so it will return the lore. Otherwise it returns an error in the Response Object.
     * </p>
     * @param item The item being checked for lore.
     * @return Response Object containing the List or an error.
     *
     * */
    public static Response<List<String>> getLore(@NotNull ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            return Response.success(item.getItemMeta().getLore());
        }
        return Response.failure("There is no lore on this item!");
    }

    public static boolean setLore(@NotNull ItemStack item, List<String> loreList) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            meta.setLore(loreList);
            item.setItemMeta(meta);
            return true;
        }
        return false;
    }

    /**
     * Retrieves the color from a String list of lore.
     * <p>
     * This method iterates through the provided list and returns the corresponding
     * ToolColor if found. If no color is found, a null value for ToolColor is returned along with a value for the Error message.
     * The caller should check the success of the method with Response.isSuccessful() before handling the object response since the ToolColor might be null.
     * </p>
     *
     * @param loreList A list of strings from which to retrieve the ToolColor Response
     * @return the corresponding Response wrapper value. If no color is found then an error message is provided in the error value.
     * @see Response Response<T> for the Response Object
     * @see ToolColor ToolColor for the enums
     */
    public static Response<ToolColor> getColor(List<String> loreList){
        for (String lore : loreList) {
            for (ToolColor toolColor : ToolColor.values()) {
                if (lore.contains(toolColor.getBrightColorCode())) {
                    return Response.success(toolColor);
                }
            }
        }
        return Response.failure("Could not match a color.");
    }

    /**
     * Retrieves the status of a Titan tool or ToolStatus.EMPTY
     * <p>
     *  This method iterates through the lore list and checks for a matching constant STATUS_PREFIX found in the TitanIteminfo class.
     *  If found, the method checks for a match against the string value of ToolStatus.OFF or ToolStatus.ON and returns the appropriate ToolStatus.
     *  If not found it returns ToolStatus.EMPTY
     * </p>
     * @param loreList A list of strings found on a TitanTool.
     * @param isTitanTool boolean returned from an isTitanTool check.
     * @return ToolStatus on the tool, the caller needs to account for the ToolStatus.EMPTY return case.
     * */
    public static Response<ToolStatus> getStatus(List<String> loreList, boolean isTitanTool) {
        if (!isTitanTool) Response.failure(INVALID_TOOL_CHECK);
        for (String lore : loreList) {
            if (lore.contains(STATUS_PREFIX)) {
                if (lore.contains(ToolStatus.OFF.getString())) {
                    return Response.success(ToolStatus.OFF);
                } else if (lore.contains(ToolStatus.ON.getString())) {
                    return Response.success(ToolStatus.ON);
                }
            }
        }
        return Response.failure("Could not retrieve a tool status.");
    }

    public static boolean isTitanType(ItemStack item, List<Material> ALLOWED_TYPES, boolean isTitanTool) {
        return isTitanTool && isAllowedType(item, ALLOWED_TYPES);
    }

}
