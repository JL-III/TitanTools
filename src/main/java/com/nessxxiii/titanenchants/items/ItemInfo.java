package com.nessxxiii.titanenchants.items;

import com.nessxxiii.titanenchants.enums.PowerCrystalType;

import net.kyori.adventure.text.Component;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


import java.util.*;


public class ItemInfo {

    public static final String ANCIENT_RED = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
    public static final String ANCIENT_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7 ♆";
    public static final String ANCIENT_BLUE = "§8Ancient Power§x§6§D§5§E§F§F ♆";

    public static final String ANCIENT_CHARGE = "§8Charge:§x§F§F§0§0§4§C";
    public static final String ANCIENT_CHARGE_RED = "§8Charge:§x§F§F§0§0§4§C";
    public static final String ANCIENT_CHARGE_YELLOW = "§8Charge:§x§F§F§E§C§2§7";
    public static final String ANCIENT_CHARGE_BLUE = "§8Charge:§x§6§D§5§E§F§F";

    public static final String CHARGE_STRING = "§8Charge: ";

    public static final String ANCIENT_CHARGE_STRING_LITERAL = "§x§c§1§0§0§8§bA§x§c§6§0§0§8§6n§x§c§b§0§0§8§1c§x§d§1§0§0§7§bi§x§d§6§0§0§7§6e§x§d§b§0§0§7§1n§x§e§0§0§0§6§ct §x§e§5§0§0§6§6C§x§e§a§0§0§6§1h§x§f§0§0§0§5§ca§x§f§5§0§0§5§7r§x§f§a§0§0§5§1g§x§f§f§0§0§4§ce ";
    public static final String POWER_CRYSTAL_TYPE_STRING_LITERAL = "§x§8§d§0§0§f§bT§x§a§8§0§0§f§cy§x§c§3§0§0§f§cp§x§d§d§0§0§f§de§x§f§8§0§0§f§d: ";
    public static final String POWER_CRYSTAL_COMMON_STRING_LITERAL = "§x§7§7§7§7§7§7C§x§8§1§8§1§8§1o§x§8§b§8§b§8§bm§x§9§6§9§6§9§6m§x§a§0§a§0§a§0o§x§a§a§a§a§a§an";
    public static final String POWER_CRYSTAL_COMMON_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "5";
    public static final String POWER_CRYSTAL_TYPE_COMMON = POWER_CRYSTAL_TYPE_STRING_LITERAL + POWER_CRYSTAL_COMMON_STRING_LITERAL;
    public static final String POWER_CRYSTAL_UNCOMMON_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "50";
    public static final String POWER_CRYSTAL_TYPE_UNCOMMON = ChatColor.DARK_PURPLE + POWER_CRYSTAL_TYPE_STRING_LITERAL + ChatColor.GREEN + "Uncommon";
    public static final String POWER_CRYSTAL_SUPER_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "100";
    public static final String POWER_CRYSTAL_TYPE_SUPER = ChatColor.DARK_PURPLE + POWER_CRYSTAL_TYPE_STRING_LITERAL + ChatColor.BLUE + "Super";
    public static final String POWER_CRYSTAL_EPIC_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "250";
    public static final String POWER_CRYSTAL_TYPE_EPIC = ChatColor.DARK_PURPLE + POWER_CRYSTAL_TYPE_STRING_LITERAL + ChatColor.LIGHT_PURPLE + "Epic";
    public static final String POWER_CRYSTAL_ULTRA_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "1000";
    public static final String POWER_CRYSTAL_TYPE_ULTRA = ChatColor.DARK_PURPLE + POWER_CRYSTAL_TYPE_STRING_LITERAL + "§x§f§b§6§9§0§0U§x§f§c§7§9§0§0l§x§f§c§8§8§0§0t§x§f§d§9§8§0§0r§x§f§d§a§7§0§0a";
    public static final String POWER_CRYSTAL_DISPLAY = "§x§8§0§0§0§f§f§lP§x§8§c§0§8§f§f§lo§x§9§7§0§f§f§f§lw§x§a§3§1§7§f§f§le§x§a§e§1§f§f§f§lr §x§b§a§2§7§f§f§lC§x§c§5§2§e§f§f§lr§x§d§1§3§6§f§f§ly§x§d§c§3§e§f§f§ls§x§e§8§4§6§f§f§lt§x§f§3§4§d§f§f§la§x§f§f§5§5§f§f§ll";
    public static final String ANCIENT_DEPLETED = " ";

    public static final String RED = "§x§F§F§0§0§0§0";
    public static final String YELLOW = "§x§F§F§E§C§2§7";
    public static final String BLUE = "§x§6§D§5§E§F§F";

    public static final String CHARGED = " ♆";

    public static final String CHARGED_INACTIVE = "§8Ancient Power ♆";
    public static final String IMBUED_INACTIVE = "§8Ancient Power Ω";

    public static final String ANCIENT_POWER_RED = "§8Ancient Power§x§F§F§0§0§0§0";
    public static final String ANCIENT_POWER_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7";
    public static final String ANCIENT_POWER_BLUE = "§8Ancient Power§x§6§D§5§E§F§F";

    public static final String ANCIENT_POWER_STRING = "§8Ancient Power";

    public static final String CHARGED_ONE = " ♆ I";
    public static final String CHARGED_TWO = " ♆ II";
    public static final String CHARGED_THREE = " ♆ III";
    public static final String IMBUED_ONE = " Ω I";
    public static final String IMBUED_TWO = " Ω II";
    public static final String IMBUED_THREE = " Ω III";

    public static final String CHARGED_RED_ONE = ANCIENT_POWER_RED + CHARGED_ONE;
    public static final String CHARGED_RED_TWO = ANCIENT_POWER_RED + CHARGED_TWO;
    public static final String CHARGED_RED_THREE = ANCIENT_POWER_RED + CHARGED_THREE;

    public static final String IMBUED_RED_ONE = ANCIENT_POWER_RED + IMBUED_ONE;
    public static final String IMBUED_RED_TWO = ANCIENT_POWER_RED + IMBUED_TWO;
    public static final String IMBUED_RED_THREE = ANCIENT_POWER_RED + IMBUED_THREE;

    public static final String CHARGED_YELLOW_ONE = ANCIENT_POWER_YELLOW + CHARGED_ONE;
    public static final String CHARGED_YELLOW_TWO = ANCIENT_POWER_YELLOW + CHARGED_TWO;
    public static final String CHARGED_YELLOW_THREE = ANCIENT_POWER_YELLOW + CHARGED_THREE;

    public static final String IMBUED_YELLOW_ONE = ANCIENT_POWER_YELLOW + IMBUED_ONE;
    public static final String IMBUED_YELLOW_TWO = ANCIENT_POWER_YELLOW + IMBUED_TWO;
    public static final String IMBUED_YELLOW_THREE = ANCIENT_POWER_YELLOW + IMBUED_THREE;

    public static final String CHARGED_BLUE_ONE = ANCIENT_POWER_BLUE+ CHARGED_ONE;
    public static final String CHARGED_BLUE_TWO = ANCIENT_POWER_BLUE + CHARGED_TWO;
    public static final String CHARGED_BLUE_THREE = ANCIENT_POWER_BLUE + CHARGED_THREE;

    public static final String IMBUED_BLUE_ONE = ANCIENT_POWER_BLUE + IMBUED_ONE;
    public static final String IMBUED_BLUE_TWO = ANCIENT_POWER_BLUE + IMBUED_TWO;
    public static final String IMBUED_BLUE_THREE = ANCIENT_POWER_BLUE + IMBUED_THREE;

//future conversion to component types
//    public static final Component ANCIENT_RED_AS_COMPONENT = Component.text("§8Ancient Power§x§F§F§0§0§0§0 ♆");
//    public static final Component ANCIENT_YELLOW_AS_COMPONENT = Component.text("§8Ancient Power§x§F§F§E§C§2§7 ♆");
//    public static final Component ANCIENT_BLUE_AS_COMPONENT = Component.text("§8Ancient Power§x§6§D§5§E§F§F ♆");

    public static final String EXCAVATION_TOOL_DISPLAY_NAME = "§x§f§b§b§5§0§0§lE§x§f§6§a§7§1§3§lx§x§f§1§9§9§2§7§lc§x§e§c§8§b§3§a§la§x§e§7§7§d§4§e§lv§x§e§2§6§f§6§1§la§x§d§d§6§1§7§5§lt§x§d§9§5§4§8§8§li§x§d§4§4§6§9§c§lo§x§c§f§3§8§a§f§ln §x§c§a§2§a§c§3§lT§x§c§5§1§c§d§6§lo§x§c§0§0§e§e§a§lo§x§b§b§0§0§f§d§ll   ";

    public static final Map<String, String[]> CHARGED_LORE_MATRIX = new HashMap<>(){
        final String[] RED_ARRAY = {CHARGED_INACTIVE, CHARGED_RED_ONE, CHARGED_RED_TWO, CHARGED_RED_THREE};
        final String[] YELLOW_ARRAY = {CHARGED_INACTIVE, CHARGED_YELLOW_ONE, CHARGED_YELLOW_TWO, CHARGED_YELLOW_THREE};
        final String[] BLUE_ARRAY = {CHARGED_INACTIVE, CHARGED_BLUE_ONE, CHARGED_BLUE_TWO, CHARGED_BLUE_THREE};
        {
            put("RED", RED_ARRAY);
            put("YELLOW", YELLOW_ARRAY);
            put("BLUE", BLUE_ARRAY);

        }
    };

    public static final Map<String, String[]> IMBUED_LORE_MATRIX = new HashMap<>(){
        final String[] RED_ARRAY = {IMBUED_INACTIVE, IMBUED_RED_ONE, IMBUED_RED_TWO, IMBUED_RED_THREE};
        final String[] YELLOW_ARRAY = {IMBUED_INACTIVE, IMBUED_YELLOW_ONE, IMBUED_YELLOW_TWO, IMBUED_YELLOW_THREE};
        final String[] BLUE_ARRAY = {IMBUED_INACTIVE, IMBUED_BLUE_ONE, IMBUED_BLUE_TWO, IMBUED_BLUE_THREE};
        {
            put("RED", RED_ARRAY);
            put("YELLOW", YELLOW_ARRAY);
            put("BLUE", BLUE_ARRAY);

        }
    };

    public static final Set<String> TITAN_LORE = new HashSet<>(){
        {
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);

            add(CHARGED_INACTIVE);
            add(IMBUED_INACTIVE);
            //composites were created for flexibility with validation checks
            //and charge management.
            add(CHARGED_RED_ONE);
            add(CHARGED_RED_TWO);
            add(CHARGED_RED_THREE);

            add(CHARGED_YELLOW_ONE);
            add(CHARGED_YELLOW_TWO);
            add(CHARGED_YELLOW_THREE);

            add(CHARGED_BLUE_ONE);
            add(CHARGED_BLUE_TWO);
            add(CHARGED_BLUE_THREE);

            add(IMBUED_RED_ONE);
            add(IMBUED_RED_TWO);
            add(IMBUED_RED_THREE);

            add(IMBUED_YELLOW_ONE);
            add(IMBUED_YELLOW_TWO);
            add(IMBUED_YELLOW_THREE);

            add(IMBUED_BLUE_ONE);
            add(IMBUED_BLUE_TWO);
            add(IMBUED_BLUE_THREE);

        }
    };
//future conversion to component types
//    public static final Set<Component> TITAN_LORE_AS_COMPONENT = new HashSet<>(){
//        {
//            add(ANCIENT_RED_AS_COMPONENT);
//            add(ANCIENT_YELLOW_AS_COMPONENT);
//            add(ANCIENT_BLUE_AS_COMPONENT);
//        }
//    };

    public static final List<String> UNIMBUED_LORE = new ArrayList<>(){
        {
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);
        }
    };

    public static final List<String> ACTIVE_LORE = new ArrayList<>(){
        {
            add(CHARGED_RED_ONE);
            add(CHARGED_RED_TWO);
            add(CHARGED_RED_THREE);

            add(CHARGED_YELLOW_ONE);
            add(CHARGED_YELLOW_TWO);
            add(CHARGED_YELLOW_THREE);

            add(CHARGED_BLUE_ONE);
            add(CHARGED_BLUE_TWO);
            add(CHARGED_BLUE_THREE);

            add(IMBUED_RED_ONE);
            add(IMBUED_RED_TWO);
            add(IMBUED_RED_THREE);

            add(IMBUED_YELLOW_ONE);
            add(IMBUED_YELLOW_TWO);
            add(IMBUED_YELLOW_THREE);

            add(IMBUED_BLUE_ONE);
            add(IMBUED_BLUE_TWO);
            add(IMBUED_BLUE_THREE);
        }
    };

    public static final List<String> IMBUED_LORE = new ArrayList<>(){
        {
            add(IMBUED_INACTIVE);
            add(IMBUED_RED_ONE);
            add(IMBUED_RED_TWO);
            add(IMBUED_RED_THREE);

            add(IMBUED_YELLOW_ONE);
            add(IMBUED_YELLOW_TWO);
            add(IMBUED_YELLOW_THREE);

            add(IMBUED_BLUE_ONE);
            add(IMBUED_BLUE_TWO);
            add(IMBUED_BLUE_THREE);
        }
    };

    public static final List<String> ACTIVE_IMBUED_LORE = new ArrayList<>(){
        {
            add(IMBUED_RED_ONE);
            add(IMBUED_RED_TWO);
            add(IMBUED_RED_THREE);

            add(IMBUED_YELLOW_ONE);
            add(IMBUED_YELLOW_TWO);
            add(IMBUED_YELLOW_THREE);

            add(IMBUED_BLUE_ONE);
            add(IMBUED_BLUE_TWO);
            add(IMBUED_BLUE_THREE);
        }
    };

    public static final List<String> CHARGED_LORE = new ArrayList<>(){
        {
            add(CHARGED_INACTIVE);

            add(CHARGED_RED_ONE);
            add(CHARGED_RED_TWO);
            add(CHARGED_RED_THREE);

            add(CHARGED_YELLOW_ONE);
            add(CHARGED_YELLOW_TWO);
            add(CHARGED_YELLOW_THREE);

            add(CHARGED_BLUE_ONE);
            add(CHARGED_BLUE_TWO);
            add(CHARGED_BLUE_THREE);

        }
    };

    public static final List<String> ACTIVE_CHARGED_LORE = new ArrayList<>(){
        {
            add(CHARGED_RED_ONE);
            add(CHARGED_RED_TWO);
            add(CHARGED_RED_THREE);

            add(CHARGED_YELLOW_ONE);
            add(CHARGED_YELLOW_TWO);
            add(CHARGED_YELLOW_THREE);

            add(CHARGED_BLUE_ONE);
            add(CHARGED_BLUE_TWO);
            add(CHARGED_BLUE_THREE);

        }
    };

    public static final List<String> INACTIVE_LORE = new ArrayList<>(){
        {
            add(IMBUED_INACTIVE);
            add(CHARGED_INACTIVE);
        }
    };

    public static final Set<String> LEVEL_ONE = new HashSet<>(){
        {

            add(CHARGED_RED_ONE);
            add(CHARGED_YELLOW_ONE);
            add(CHARGED_BLUE_ONE);

            add(IMBUED_RED_ONE);
            add(IMBUED_YELLOW_ONE);
            add(IMBUED_BLUE_ONE);

        }
    };

    public static final Set<String> LEVEL_TWO = new HashSet<>(){
        {
            add(CHARGED_RED_TWO);
            add(CHARGED_YELLOW_TWO);
            add(CHARGED_BLUE_TWO);

            add(IMBUED_RED_TWO);
            add(IMBUED_YELLOW_TWO);
            add(IMBUED_BLUE_TWO);

        }
    };

    public static final Set<String> LEVEL_THREE = new HashSet<>(){
        {
            add(CHARGED_RED_THREE);
            add(CHARGED_YELLOW_THREE);
            add(CHARGED_BLUE_THREE);

            add(IMBUED_RED_THREE);
            add(IMBUED_YELLOW_THREE);
            add(IMBUED_BLUE_THREE);

        }
    };

    public static final List<Enum> ALLOWED_TITAN_TYPES = new ArrayList<>() {
        {
            add(Material.DIAMOND_PICKAXE);
            add(Material.NETHERITE_PICKAXE);
            add(Material.DIAMOND_SHOVEL);
            add(Material.NETHERITE_SHOVEL);
        }
    };

    public static final List<Enum> ALLOWED_PICK_TYPES = new ArrayList<>(){
        {
            add(Material.DIAMOND_PICKAXE);
            add(Material.NETHERITE_PICKAXE);
        }
    };

    public static final List<Enum> ALLOWED_SHOVEL_TYPES = new ArrayList<>(){
        {
            add(Material.DIAMOND_SHOVEL);
            add(Material.NETHERITE_SHOVEL);
        }
    };

    public static boolean isAllowedTitanType(ItemStack item){
        if (item == null) return false;
        return (ALLOWED_TITAN_TYPES.contains(item.getType()));
    }

    public static boolean isAllowedPickType(ItemStack item){
        if (item == null) return false;
        return (ALLOWED_PICK_TYPES.contains(item.getType()));
    }

    public static boolean isAllowedShovelType(ItemStack item){
        if (item == null) return false;
        return (ALLOWED_SHOVEL_TYPES.contains(item.getType()));
    }

    public static boolean isTitanTool(ItemStack item){
        for (String lore : getLore(item)) {
            if (TITAN_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean checkLore(ItemStack item, List<String> LORE){
        for (String toolLore : getLore(item)) {
            if (LORE.contains(toolLore)) {
                return true;
            }
        }
        return false;
    }

    public static List<String> getLore(ItemStack item) {
        if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
            return item.getItemMeta().getLore();
        }
        return new ArrayList<>();
    }

    public static boolean setLore(ItemStack item, List<String> loreList) {
        if (item.hasItemMeta()) {
            ItemMeta meta = item.getItemMeta();
            meta.setLore(loreList);
            item.setItemMeta(meta);
            return true;
        }
        return false;
    }


    public static boolean isLevelOne(ItemStack item){
        for (String lore : getLore(item)) {
            if (LEVEL_ONE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLevelTwo(ItemStack item){
        for (String lore : getLore(item)) {
            if (LEVEL_TWO.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLevelThree(ItemStack item){
        for (String lore : getLore(item)) {
            if (LEVEL_THREE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static int getItemLevel(ItemStack item){
        for (String lore : getLore(item)) {
            if (LEVEL_ONE.contains(lore)) {
                return 1;
            } else if (LEVEL_TWO.contains(lore)) {
                return 2;
            } else if (LEVEL_THREE.contains(lore)) {
                return 3;
            }
        }
        return 0;
    }

    public static boolean isActiveImbued(ItemStack item){
        for (String lore : getLore(item)) {
            if (ACTIVE_IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isActiveCharged(ItemStack item){
        for (String lore : getLore(item)) {
            if (ACTIVE_CHARGED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDormantCharged(ItemStack item){
        for (String lore : getLore(item)) {
            if (INACTIVE_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    //Checks if item has a charge already, this  is important in order to ensure the previous charge is obtained
    //before setting a charge to a new value.
    public static boolean isCharged(ItemStack item) {
        for (String lore : getLore(item)) {
            if (CHARGED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isImbued(ItemStack item) {
        for (String lore : getLore(item)) {
            if (IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUnImbued(ItemStack item) {
        for (String lore : getLore(item)) {
            if (UNIMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPowerCrystal(ItemStack item) {

        if (item.isSimilar(ItemCreator.powerCrystalCommon)) return true;
        if (item.isSimilar(ItemCreator.powerCrystalUncommon)) return true;
        if (item.isSimilar(ItemCreator.powerCrystalSuper)) return true;
        if (item.isSimilar(ItemCreator.powerCrystalEpic)) return true;
        if (item.isSimilar(ItemCreator.powerCrystalUltra)) return true;
        return false;
    }

    public static PowerCrystalType getPowerCrystalType(ItemStack item) {
        if (item.isSimilar(ItemCreator.powerCrystalCommon)) return PowerCrystalType.COMMON;
        if (item.isSimilar(ItemCreator.powerCrystalUncommon)) return PowerCrystalType.UNCOMMON;
        if (item.isSimilar(ItemCreator.powerCrystalSuper)) return PowerCrystalType.SUPER;
        if (item.isSimilar(ItemCreator.powerCrystalEpic)) return PowerCrystalType.EPIC;
        if (item.isSimilar(ItemCreator.powerCrystalUltra)) return PowerCrystalType.ULTRA;
        return PowerCrystalType.NULL;
    }

    public static boolean isChargedAndActive(ItemStack item){
        //TODO 11/21/2022 check to see if the type check is needed.
        if (item.getType() == Material.AMETHYST_SHARD) return false;
        for (String lore : getLore(item)) {
            if (ACTIVE_CHARGED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static String getColor(ItemStack item){
        for (String lore : getLore(item)) {
            if (lore.matches("(.*)" + RED + "(.*)")) {
                return RED;
            } else if (lore.matches("(.*)" + YELLOW + "(.*)")) {
                return YELLOW;
            } else if (lore.matches("(.*)" + BLUE + "(.*)")) {
                return BLUE;
            }
        }
        return null;
    }

    public static String getColorStringLiteral(ItemStack item){
        for (String lore : getLore(item)) {
            if (lore.matches("(.*)" + RED + "(.*)")) {
                return "RED";
            } else if (lore.matches("(.*)" + YELLOW + "(.*)")) {
                return "YELLOW";
            } else if (lore.matches("(.*)" + BLUE + "(.*)")) {
                return "BLUE";
            }
        }
        return null;
    }

    public static Map<String, Integer> getColorAndPowerLevel(ItemStack item) {
        Map<String, Integer> colorAndPower = new HashMap<>();
        colorAndPower.put(getColor(item), getItemLevel(item));
        return colorAndPower;
    }

    public static int getAncientPowerLoreIndex(List<String> loreList) {
//     Bukkit.getServer().getConsoleSender().sendMessage("inside of getAncientPowerLoreIndex");
        for (int i = 0; i < loreList.size(); i++){
            if (TITAN_LORE.contains(loreList.get(i))) return i;
        }
        return -1;
    }

    public static List<Component> getLore(Player player) {
        ItemStack item = player.getInventory().getItemInMainHand();
        List<Component> lore = new ArrayList<>();
        if (!item.hasItemMeta()) return lore;
        for (Component component : Objects.requireNonNull(item.lore())) {
            lore.add(component);
            player.sendMessage(component.asComponent() + ": added");
        }
        return lore;
    }

    public static boolean isTitanPick(ItemStack item) {
        return isTitanTool(item)
                && isAllowedPickType(item);
    }

    public static boolean isTitanShovel(ItemStack item) {
        return isTitanTool(item) && isAllowedShovelType(item);
    }

    public static boolean isChargedOrImbuedTitanPick(ItemStack item) {
        if(!isTitanPick(item)) return false;
        return (isImbued(item) || isCharged(item));
    }

    public static boolean isChargedOrImbuedTitanShovel(ItemStack item) {
        if(!isTitanShovel(item)) return false;
        return (isImbued(item) || isCharged(item));
    }

}
