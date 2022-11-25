package com.nessxxiii.titanenchants.items;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


public class ItemCreator {

    public static ItemStack powerCrystalCommon;
    public static ItemStack powerCrystalUncommon;
    public static ItemStack powerCrystalSuper;
    public static ItemStack powerCrystalEpic;
    public static ItemStack powerCrystalUltra;
    public static ItemStack excavator;


    public static void Init(){
        createPowerCrystalCommon();
        createPowerCrystalUncommon();
        createPowerCrystalSuper();
        createPowerCrystalEpic();
        createPowerCrystalUltra();
        createExcavator();
        Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_PURPLE + "[TitanEnchants] " + ChatColor.GREEN + "items have been initialized.");
    }

    public static void createPowerCrystalCommon() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(ItemInfo.POWER_CRYSTAL_COMMON_CHARGE);
        lore.add(ItemInfo.POWER_CRYSTAL_TYPE_COMMON);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ItemInfo.POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,5);
        powerCrystalCommon = item;
    }

    private static void createPowerCrystalUncommon() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(ItemInfo.POWER_CRYSTAL_UNCOMMON_CHARGE);
        lore.add(ItemInfo.POWER_CRYSTAL_TYPE_UNCOMMON);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ItemInfo.POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,4);
        powerCrystalUncommon = item;
    }

    private static void createPowerCrystalSuper() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(ItemInfo.POWER_CRYSTAL_SUPER_CHARGE);
        lore.add(ItemInfo.POWER_CRYSTAL_TYPE_SUPER);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ItemInfo.POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,3);
        powerCrystalSuper = item;
    }

    private static void createPowerCrystalEpic() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(ItemInfo.POWER_CRYSTAL_EPIC_CHARGE);
        lore.add(ItemInfo.POWER_CRYSTAL_TYPE_EPIC);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ItemInfo.POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,2);
        powerCrystalEpic = item;
    }

    private static void createPowerCrystalUltra() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(ItemInfo.POWER_CRYSTAL_ULTRA_CHARGE);
        lore.add(ItemInfo.POWER_CRYSTAL_TYPE_ULTRA);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ItemInfo.POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,1);
        powerCrystalUltra = item;
    }

    private static void createExcavator() {
        ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
        List<String> lore = new ArrayList<>();
        lore.add("§x§f§b§b§5§0§0A tool used to extract");
        lore.add(ChatColor.DARK_PURPLE + "Power Crystals §x§f§b§b§5§0§0from");
        lore.add("§x§f§b§b§5§0§0amethyst blocks");
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ItemInfo.EXCAVATION_TOOL_DISPLAY_NAME);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        item.addUnsafeEnchantment(Enchantment.MULTISHOT, 3);
        excavator = item;
    }



}
