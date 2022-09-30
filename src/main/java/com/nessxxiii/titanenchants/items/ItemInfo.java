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

    public static final String EXCAVATION_TOOL_DISPLAY_NAME = "§x§f§b§b§5§0§0§lE§x§f§6§a§7§1§3§lx§x§f§1§9§9§2§7§lc§x§e§c§8§b§3§a§la§x§e§7§7§d§4§e§lv§x§e§2§6§f§6§1§la§x§d§d§6§1§7§5§lt§x§d§9§5§4§8§8§li§x§d§4§4§6§9§c§lo§x§c§f§3§8§a§f§ln §x§c§a§2§a§c§3§lT§x§c§5§1§c§d§6§lo§x§c§0§0§e§e§a§lo§x§b§b§0§0§f§d§ll   ";

    public static final String SPONGE_DISPLAY_NAME = "§x§7§F§0§0§0§0§k %§x§8§8§0§0§0§1§l T§x§9§2§0§0§0§2§lh§x§9§B§0§0§0§3§le§x§A§5§0§0§0§4§l T§x§A§E§0§0§0§4§li§x§B§8§0§0§0§4§lt§x§C§2§0§0§0§4§la§x§C§C§0§0§0§3§ln§x§D§6§0§0§0§3§l S§x§E§0§0§0§0§2§lp§x§E§A§0§0§0§2§lo§x§F§5§0§0§0§1§ln§x§F§5§0§0§0§1§lg§x§F§5§0§0§0§1§le§x§F§5§0§0§0§1§l§k %";

    public static final String TITAN_SPONGE_LORE = "§8Ancient Power §x§F§F§0§0§4§C♆";

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
                return true;
            }
        }
        return false;
    }

    public static boolean isActiveCharged(ItemStack item){
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (ACTIVE_CHARGED_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isDormantCharged(ItemStack item){
        List<String> loreList = item.getItemMeta().getLore();
        for (String lore : loreList) {
            if (INACTIVE_LORE.contains(lore)) {
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

    public static boolean isTitanSponge(ItemStack item){

        if (item.getItemMeta() == null) return false;
        List<String> loreList = item.getItemMeta().getLore();
        if (loreList == null) return false;
        for (String lore : loreList) {
            if (TITAN_SPONGE_LORE.contains(lore)) {
                return true;
            }
        }
        return false;
    }
}
