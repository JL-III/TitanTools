package com.nessxxiii.titantools.items;

import com.nessxxiii.titantools.enums.PowerCrystalType;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PowerCrystalInfo {

    public static final String ANCIENT_CHARGE_STRING_LITERAL = "§x§c§1§0§0§8§bA§x§c§6§0§0§8§6n§x§c§b§0§0§8§1c§x§d§1§0§0§7§bi§x§d§6§0§0§7§6e§x§d§b§0§0§7§1n§x§e§0§0§0§6§ct §x§e§5§0§0§6§6C§x§e§a§0§0§6§1h§x§f§0§0§0§5§ca§x§f§5§0§0§5§7r§x§f§a§0§0§5§1g§x§f§f§0§0§4§ce ";
    public static final String POWER_CRYSTAL_TYPE_STRING_LITERAL = "§x§8§d§0§0§f§bT§x§a§8§0§0§f§cy§x§c§3§0§0§f§cp§x§d§d§0§0§f§de§x§f§8§0§0§f§d: ";
    public static final String POWER_CRYSTAL_COMMON_STRING_LITERAL = "§x§7§7§7§7§7§7C§x§8§1§8§1§8§1o§x§8§b§8§b§8§bm§x§9§6§9§6§9§6m§x§a§0§a§0§a§0o§x§a§a§a§a§a§an";
    public static final String POWER_CRYSTAL_UNCOMMON_STRING_LITERAL = "§x§2§f§8§e§2§fU§x§3§4§9§e§3§4n§x§3§a§a§e§3§ac§x§3§f§b§e§3§fo§x§4§5§c§f§4§5m§x§4§a§d§f§4§am§x§5§0§e§f§5§0o§x§5§5§f§f§5§5n";
    public static final String POWER_CRYSTAL_SUPER_STRING_LITERAL = "§x§4§9§4§9§d§aS§x§4§c§4§c§e§3u§x§4§f§4§f§e§dp§x§5§2§5§2§f§6e§x§5§5§5§5§f§fr";
    public static final String POWER_CRYSTAL_EPIC_STRING_LITERAL = "§x§d§d§4§a§d§dE§x§e§8§4§e§e§8p§x§f§4§5§1§f§4i§x§f§f§5§5§f§fc";
    public static final String POWER_CRYSTAL_ULTRA_STRING_LITERAL = "§x§f§b§6§9§0§0U§x§f§c§7§9§0§0l§x§f§c§8§8§0§0t§x§f§d§9§8§0§0r§x§f§d§a§7§0§0a";
    public static final String POWER_CRYSTAL_COMMON_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "5";
    public static final String POWER_CRYSTAL_TYPE_COMMON = POWER_CRYSTAL_TYPE_STRING_LITERAL + POWER_CRYSTAL_COMMON_STRING_LITERAL;
    public static final String POWER_CRYSTAL_UNCOMMON_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "50";
    public static final String POWER_CRYSTAL_TYPE_UNCOMMON = POWER_CRYSTAL_TYPE_STRING_LITERAL + POWER_CRYSTAL_UNCOMMON_STRING_LITERAL;
    public static final String POWER_CRYSTAL_SUPER_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "100";
    public static final String POWER_CRYSTAL_TYPE_SUPER = POWER_CRYSTAL_TYPE_STRING_LITERAL + POWER_CRYSTAL_SUPER_STRING_LITERAL;
    public static final String POWER_CRYSTAL_EPIC_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "250";
    public static final String POWER_CRYSTAL_TYPE_EPIC = POWER_CRYSTAL_TYPE_STRING_LITERAL + POWER_CRYSTAL_EPIC_STRING_LITERAL;
    public static final String POWER_CRYSTAL_ULTRA_CHARGE = ANCIENT_CHARGE_STRING_LITERAL + "1000";
    public static final String POWER_CRYSTAL_TYPE_ULTRA = POWER_CRYSTAL_TYPE_STRING_LITERAL + POWER_CRYSTAL_ULTRA_STRING_LITERAL;
    public static final String POWER_CRYSTAL_DISPLAY = "§x§8§0§0§0§f§f§lP§x§8§c§0§8§f§f§lo§x§9§7§0§f§f§f§lw§x§a§3§1§7§f§f§le§x§a§e§1§f§f§f§lr §x§b§a§2§7§f§f§lC§x§c§5§2§e§f§f§lr§x§d§1§3§6§f§f§ly§x§d§c§3§e§f§f§ls§x§e§8§4§6§f§f§lt§x§f§3§4§d§f§f§la§x§f§f§5§5§f§f§ll";


    public static ItemStack powerCrystalCommon() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(POWER_CRYSTAL_COMMON_CHARGE);
        lore.add(POWER_CRYSTAL_TYPE_COMMON);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_COMMON);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,5);
        return item;
    }

    private static ItemStack powerCrystalUncommon() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(POWER_CRYSTAL_UNCOMMON_CHARGE);
        lore.add(POWER_CRYSTAL_TYPE_UNCOMMON);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_UNCOMMON);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,4);
        return item;
    }

    private static ItemStack powerCrystalSuper() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(POWER_CRYSTAL_SUPER_CHARGE);
        lore.add(POWER_CRYSTAL_TYPE_SUPER);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_SUPER);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,3);
        return item;
    }

    private static ItemStack powerCrystalEpic() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(POWER_CRYSTAL_EPIC_CHARGE);
        lore.add(POWER_CRYSTAL_TYPE_EPIC);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_EPIC);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,2);
        return item;
    }

    private static ItemStack powerCrystalUltra() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(POWER_CRYSTAL_ULTRA_CHARGE);
        lore.add(POWER_CRYSTAL_TYPE_ULTRA);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_ULTRA);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,1);
        return item;
    }

    public static boolean isPowerCrystal(ItemStack item) {
        if (item.isSimilar(powerCrystalCommon())) return true;
        if (item.isSimilar(powerCrystalUncommon())) return true;
        if (item.isSimilar(powerCrystalSuper())) return true;
        if (item.isSimilar(powerCrystalEpic())) return true;
        if (item.isSimilar(powerCrystalUltra())) return true;
        return false;
    }

    public static PowerCrystalType getPowerCrystalType(ItemStack item) {
        if (item.isSimilar(powerCrystalCommon())) return PowerCrystalType.COMMON;
        if (item.isSimilar(powerCrystalUncommon())) return PowerCrystalType.UNCOMMON;
        if (item.isSimilar(powerCrystalSuper())) return PowerCrystalType.SUPER;
        if (item.isSimilar(powerCrystalEpic())) return PowerCrystalType.EPIC;
        if (item.isSimilar(powerCrystalUltra())) return PowerCrystalType.ULTRA;
        return PowerCrystalType.NULL;
    }
}
