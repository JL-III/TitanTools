package com.nessxxiii.titanenchants.items;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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
//    public static ItemStack powerCrystalTEST;
    public static ItemStack excavator;
    public static ItemStack spawnerkey;


    public static void Init(){
        createPowerCrystalCommon();
        createPowerCrystalUncommon();
        createPowerCrystalSuper();
        createPowerCrystalEpic();
        createPowerCrystalUltra();
//        createPowerCrystalTEST();
        createExcavator();
        createSpawnerKey();
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
        meta.setCustomModelData(1000000);
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
        meta.setCustomModelData(1000001);
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
        meta.setCustomModelData(1000002);
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
        meta.setCustomModelData(1000003);
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
        meta.setCustomModelData(1000004);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,1);
        powerCrystalUltra = item;
    }

//    private static void createPowerCrystalTEST() {
//        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
//        List<String> lore = new ArrayList<>();
//        lore.add(ItemInfo.POWER_CRYSTAL_ULTRA_CHARGE);
//        lore.add(ItemInfo.POWER_CRYSTAL_TYPE_ULTRA);
//        item.setLore(lore);
//        ItemMeta meta = item.getItemMeta();
//        meta.setDisplayName(ItemInfo.POWER_CRYSTAL_DISPLAY);
//        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
//        meta.setCustomModelData(1);
//        item.setItemMeta(meta);
//        item.addUnsafeEnchantment(Enchantment.CHANNELING,1);
//        powerCrystalTEST = item;
//    }

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
        meta.setCustomModelData(2000000);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        item.addUnsafeEnchantment(Enchantment.MULTISHOT, 3);
        excavator = item;
    }

    private static void createSpawnerKey() {

        ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.BOLD + "Spawner Crate " + ChatColor.RED + ChatColor.BOLD + "Key");
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A special Key");
        lore.add(ChatColor.DARK_RED + "" + ChatColor.MAGIC + "t " + ChatColor.DARK_RED + "Magic " + ChatColor.DARK_RED + ChatColor.MAGIC +  "t." );
        Enchantment ench = Enchantment.LUCK;
        ItemFlag hide = ItemFlag.HIDE_ENCHANTS;
        meta.addItemFlags(hide);
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(ench, 1);
        spawnerkey = item;
    }



}
