package com.nessxxiii.titanenchants.items;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class ItemInfo {

    public static final String ANCIENT_RED = "§8Ancient Power§x§F§F§0§0§0§0 ♆";
    public static final String ANCIENT_YELLOW = "§8Ancient Power§x§F§F§E§C§2§7 ♆";
    public static final String ANCIENT_BLUE = "§8Ancient Power§x§6§D§5§E§F§F ♆";

    public static final String ANCIENT_CHARGE = "§8Charge:§x§F§F§0§0§4§C";
    public static final String POWER_CRYSTAL = "§x§F§F§0§0§4§CAncient Charge";
    public static final String ANCIENT_DEPLETED = " ";

    public static final String RED = "§x§F§F§0§0§0§0";
    public static final String YELLOW = "§x§F§F§E§C§2§7";
    public static final String BLUE = "§x§6§D§5§E§F§F";

    public static final String CHARGED_INACTIVE = "§8Ancient Power ♆";
    public static final String CHARGED_ONE = "§8Ancient Power §x§F§F§0§0§4§C♆ I";
    public static final String CHARGED_TWO = "§8Ancient Power §x§F§F§0§0§4§C♆ II";
    public static final String CHARGED_THREE = "§8Ancient Power §x§F§F§0§0§4§C♆ III";

    public static final String IMBUED_INACTIVE = "§8Ancient Power Ω";
    public static final String IMBUED_ONE = "§8Ancient Power §x§F§F§0§0§4§CΩ I";
    public static final String IMBUED_TWO = "§8Ancient Power §x§F§F§0§0§4§CΩ II";
    public static final String IMBUED_THREE = "§8Ancient Power §x§F§F§0§0§4§CΩ III";

    public static final Component ANCIENT_RED_AS_COMPONENT = Component.text("§8Ancient Power§x§F§F§0§0§0§0 ♆");
    public static final Component ANCIENT_YELLOW_AS_COMPONENT = Component.text("§8Ancient Power§x§F§F§E§C§2§7 ♆");
    public static final Component ANCIENT_BLUE_AS_COMPONENT = Component.text("§8Ancient Power§x§6§D§5§E§F§F ♆");

    public static final Set<String> TITAN_LORE = new HashSet<>(){
        {
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);

            add(CHARGED_INACTIVE);
            add(CHARGED_ONE);
            add(CHARGED_TWO);
            add(CHARGED_THREE);

            add(IMBUED_INACTIVE);
            add(IMBUED_ONE);
            add(IMBUED_TWO);
            add(IMBUED_THREE);
        }
    };

    public static final Set<Component> TITAN_LORE_AS_COMPONENT = new HashSet<>(){
        {
            add(ANCIENT_RED_AS_COMPONENT);
            add(ANCIENT_YELLOW_AS_COMPONENT);
            add(ANCIENT_BLUE_AS_COMPONENT);
        }
    };

    public static final List<String> UNIMBUED_LORE = new ArrayList<>(){
        {
            add(ANCIENT_RED);
            add(ANCIENT_YELLOW);
            add(ANCIENT_BLUE);
        }
    };

    public static final List<String> ACTIVE_LORE = new ArrayList<>(){
        {
            add(CHARGED_ONE);
            add(CHARGED_TWO);
            add(CHARGED_THREE);

            add(IMBUED_ONE);
            add(IMBUED_TWO);
            add(IMBUED_THREE);
        }
    };

    public static final List<String> IMBUED_LORE = new ArrayList<>(){
        {
            add(IMBUED_ONE);
            add(IMBUED_TWO);
            add(IMBUED_THREE);
            add(IMBUED_INACTIVE);
        }
    };

    public static final List<String> ACTIVE_IMBUED_LORE = new ArrayList<>(){
        {
            add(IMBUED_ONE);
            add(IMBUED_TWO);
            add(IMBUED_THREE);

        }
    };

    public static final List<String> CHARGED_LORE = new ArrayList<>(){
        {
            add(CHARGED_ONE);
            add(CHARGED_TWO);
            add(CHARGED_THREE);
            add(CHARGED_INACTIVE);
        }
    };

    public static final List<String> ACTIVE_CHARGED_LORE = new ArrayList<>(){
        {
            add(CHARGED_ONE);
            add(CHARGED_TWO);
            add(CHARGED_THREE);

        }
    };

    public static final List<String> INACTIVE_LORE = new ArrayList<>(){
        {
            add(IMBUED_INACTIVE);
            add(CHARGED_INACTIVE);
        }
    };

    public static final List<String> LEVEL_ONE = new ArrayList<>(){
        {
            add(IMBUED_ONE);
            add(CHARGED_ONE);
        }
    };

    public static final List<String> LEVEL_TWO = new ArrayList<>(){
        {
            add(IMBUED_TWO);
            add(CHARGED_TWO);
        }
    };

    public static final List<String> LEVEL_THREE = new ArrayList<>(){
        {
            add(IMBUED_THREE);
            add(CHARGED_THREE);
        }
    };

    public static final List<Enum> ALLOWED_TYPES = new ArrayList<>(){
        {
            add(Material.DIAMOND_PICKAXE);
            add(Material.NETHERITE_PICKAXE);
        }
    };

    public static boolean isAllowedType(ItemStack item){
        if (item == null) return false;
        return (ALLOWED_TYPES.contains(item.getType()));
    }

    public static boolean isTitanTool(ItemStack item){
        if(!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (TITAN_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLevelOne(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (LEVEL_ONE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isLevelTwo(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (LEVEL_TWO.contains(lore)) {
                return true;
            }
        }
        return false;
    }

/*    public static boolean isLevelOneVerbose(Player player){
        ItemStack item = player.getInventory().getItemInMainHand();
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (LEVEL_ONE.contains(lore)) {
                player.sendMessage(lore);
                return true;
            }
        }
        return false;
    }

    public static boolean isLevelTwoVerbose(Player player){
        ItemStack item = player.getInventory().getItemInMainHand();
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (LEVEL_TWO.contains(lore)) {
                player.sendMessage(lore);
                return true;
            }
        }
        return false;
    }*/

    public static boolean isLevelThree(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (LEVEL_THREE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static int getItemLevel(ItemStack item){

        if (!item.hasItemMeta()) return -1;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return -1;
        for (String lore : loreList) {
            if (LEVEL_ONE.contains(lore)) {
                return 1;
            } else if (LEVEL_TWO.contains(lore)) {
                return 2;
            } else if (LEVEL_THREE.contains(lore)) {
                return 3;
            }
        }
        return -1;

    }

    public static boolean isActiveImbued(ItemStack item){

        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ACTIVE_IMBUED_LORE.contains(lore)) {
                /*Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientPowerActive check");*/
                return true;
            }
        }
        return false;
    }

    public static boolean isActiveCharged(ItemStack item){
        /*Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");*/
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ACTIVE_CHARGED_LORE.contains(lore)) {
                /*Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");*/
                return true;
            }
        }
        return false;
    }

    public static boolean isDormantCharged(ItemStack item){
        /*Bukkit.getServer().getConsoleSender().sendMessage("Inside AncientChargeActive check");*/
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (INACTIVE_LORE.contains(lore)) {
                /*Bukkit.getServer().getConsoleSender().sendMessage("Inside successful AncientChargeActive check");*/
                return true;
            }
        }
        return false;
    }

    public static boolean isCharged(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (CHARGED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isImbued(ItemStack item) {

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (IMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isUnImbued(ItemStack item) {

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (UNIMBUED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isPowerCrystal(ItemStack item) {

        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        Material type = item.getType();
        Material powerCrystal = Material.AMETHYST_SHARD;
        if (!item.hasItemFlag(ItemFlag.HIDE_ENCHANTS)) return false;
        if (!item.containsEnchantment(Enchantment.CHANNELING)) return false;
        /*Bukkit.getServer().getConsoleSender().sendMessage("inside isPowerCrystal");*/
        if (loreList.stream().anyMatch(lore -> lore.matches(POWER_CRYSTAL) && (type == powerCrystal))){
            /*Bukkit.getServer().getConsoleSender().sendMessage("passed isPowerCrystal check");*/
            return true;
        };

        return false;


    }

    public static boolean hasCharge(ItemStack item){
        if (!item.hasItemMeta()) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        Material type = item.getType();
        Material powerCrystal = Material.AMETHYST_SHARD;
        for (String lore : loreList) {
            if (lore.matches("(.*)" + ANCIENT_CHARGE + "(.*)") && (type != powerCrystal)) {
                return true;
            }
        }
        return false;
    }

    public static String getColor(ItemStack item){
        if (!item.hasItemMeta()) return null;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return null;
        for (String lore : loreList) {
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

    public static Integer getAncientPowerLoreIndex(List<String> loreList) {
  /*      Bukkit.getServer().getConsoleSender().sendMessage("inside of getAncientPowerLoreIndex");*/
        for (int i = 0; i < loreList.size(); i++){
            if (TITAN_LORE.contains(loreList.get(i))) return i;
        }
        return null;
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
                && isAllowedType(item);
    }

    public static boolean isChargedOrImbuedTitanPick(ItemStack item) {
        if(!isTitanPick(item)) return false;
        return (isImbued(item) || isCharged(item));
    }
}
