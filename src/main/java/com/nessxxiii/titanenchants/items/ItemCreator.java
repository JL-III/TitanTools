package com.nessxxiii.titanenchants.items;

import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.*;


public class ItemCreator {

    public static ItemStack powerCrystalCommon;
    public static ItemStack powerCrystalUncommon;
    public static ItemStack powerCrystalSuper;
    public static ItemStack powerCrystalEpic;
    public static ItemStack powerCrystalUltra;
    public static ItemStack excavator;
    public static ItemStack sunFish;
    public static ItemStack nightFish;
    public static ItemStack etherealFragment;
    public static ItemStack christmasPick;
    private Plugin plugin;
    private FileConfiguration fileConfiguration;
    public static ItemStack titanPickRedFortune;
    public static ItemStack titanPickRedSilk;
    public static ItemStack titanPickYellowFortune;
    public static ItemStack titanPickYellowSilk;
    public static ItemStack titanPickBlueFortune;
    public static ItemStack titanPickBlueSilk;
    public static ItemStack titanShovelRed;

    public ItemCreator(Plugin plugin) {
        this.plugin = plugin;
        this.fileConfiguration = plugin.getConfig();
        titanPickRedFortune = (ItemStack) fileConfiguration.get("titanpickredfortune");
        titanPickRedSilk = (ItemStack) fileConfiguration.get("titanpickredsilk");
        titanPickYellowFortune = (ItemStack) fileConfiguration.get("titanpickyellowfortune");
        titanPickYellowSilk = (ItemStack) fileConfiguration.get("titanpickyellowsilk");
        titanPickBlueFortune = (ItemStack) fileConfiguration.get("titanpickbluefortune");
        titanPickBlueSilk = (ItemStack) fileConfiguration.get("titanpickbluesilk");
        titanShovelRed = (ItemStack) fileConfiguration.get("titanshovelred");
        Init();
    }

    public static void Init(){
        createPowerCrystalCommon();
        createPowerCrystalUncommon();
        createPowerCrystalSuper();
        createPowerCrystalEpic();
        createPowerCrystalUltra();
        createExcavator();
        createSunFish();
        createNightFish();
        createEtherealFragment();
        createChristmasPick();
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
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_COMMON);
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
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_UNCOMMON);
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
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_SUPER);
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
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_EPIC);
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
        meta.setCustomModelData(CustomModelData.POWER_CRYSTAL_ULTRA);
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
        meta.setCustomModelData(CustomModelData.EXCAVATOR);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        item.addUnsafeEnchantment(Enchantment.MULTISHOT, 3);
        excavator = item;
    }

    private static void createSunFish() {
        ItemStack item = new ItemStack(Material.PUFFERFISH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW + "" +  ChatColor.ITALIC + "" + ChatColor.BOLD + "The Sun Fish");
        List<String> lore = new ArrayList<>();
        lore.add("Hold one in your hand");
        lore.add("and left click!");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        meta.setCustomModelData(CustomModelData.SUN_FISH);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        sunFish = item;
    }

    private static void createNightFish() {
        ItemStack item = new ItemStack(Material.TROPICAL_FISH);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.BLUE + "" +  ChatColor.ITALIC + "" + ChatColor.BOLD + "Night Fish");
        List<String> lore = new ArrayList<>();
        lore.add("Hold one in your hand");
        lore.add("and left click!");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        meta.setCustomModelData(CustomModelData.NIGHT_FISH);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        nightFish = item;
    }

    private static void createEtherealFragment() {
        ItemStack item = new ItemStack(Material.ECHO_SHARD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "" +  ChatColor.ITALIC + "" + ChatColor.BOLD + "Ethereal Fragment");
        List<String> lore = new ArrayList<>();
        lore.add("Pieces of a strange");
        lore.add("material... collect 16");
        lore.add("and you can trade");
        lore.add("it in for a special tool");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        meta.setCustomModelData(CustomModelData.ETHEREAL_FRAGMENT);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        etherealFragment = item;
    }

    // REMEMBER TO ADJUST TO CURRENT YEAR AND MODEL
    private static void createChristmasPick() {
        ItemStack item = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§x§0§f§b§c§0§0§l§o" + ChatColor.MAGIC + " c " + ChatColor.RESET + "§x§0§f§b§c§0§0§l§oT§x§0§e§c§0§0§0§l§oh§x§0§e§c§5§0§0§l§oe §x§0§d§c§9§0§0§l§oC§x§0§c§c§d§0§0§l§oh§x§0§c§d§2§0§0§l§or§x§0§b§d§6§0§0§l§oi§x§0§a§d§a§0§0§l§os§x§0§a§d§f§0§0§l§ot§x§0§9§e§3§0§0§l§om§x§0§8§e§7§0§0§l§oa§x§0§8§e§c§0§0§l§os §x§0§7§f§0§0§0§l§oP§x§0§6§f§4§0§0§l§oi§x§0§6§f§9§0§0§l§oc§x§0§5§f§d§0§0§l§ok" + ChatColor.MAGIC + " c");
        List<String> lore = new ArrayList<>();
        lore.add("§x§f§f§0§0§0§0E§x§f§f§1§4§1§4f§x§f§f§2§8§2§8f§x§f§e§3§c§3§ci§x§f§e§5§0§5§0c§x§f§e§6§5§6§5i§x§f§e§7§9§7§9e§x§f§d§8§d§8§dn§x§f§d§a§1§a§1c§x§f§d§b§5§b§5y " + ChatColor.WHITE + "VI");
        lore.add("§x§f§f§0§0§0§0F§x§f§f§1§e§1§eo§x§f§e§3§c§3§cr§x§f§e§5§b§5§bt§x§f§e§7§9§7§9u§x§f§d§9§7§9§7n§x§f§d§b§5§b§5e " + ChatColor.WHITE + "III");
        lore.add("§x§f§f§0§0§0§0M§x§f§f§1§e§1§ee§x§f§e§3§c§3§cn§x§f§e§5§b§5§bd§x§f§e§7§9§7§9i§x§f§d§9§7§9§7n§x§f§d§b§5§b§5g");
        lore.add("§x§f§f§0§0§0§0U§x§f§f§1§4§1§4n§x§f§f§2§8§2§8b§x§f§e§3§c§3§cr§x§f§e§5§0§5§0e§x§f§e§6§5§6§5a§x§f§e§7§9§7§9k§x§f§d§8§d§8§di§x§f§d§a§1§a§1n§x§f§d§b§5§b§5g " + ChatColor.WHITE + "V");
        lore.add("§x§f§f§0§0§0§0C§x§f§f§0§d§0§dh§x§f§f§1§a§1§ar§x§f§f§2§7§2§7i§x§f§e§3§4§3§4s§x§f§e§4§1§4§1t§x§f§e§4§e§4§em§x§f§e§5§b§5§ba§x§f§e§6§7§6§7s §x§f§e§7§4§7§4S§x§f§e§8§1§8§1p§x§f§d§8§e§8§ei§x§f§d§9§b§9§br§x§f§d§a§8§a§8i§x§f§d§b§5§b§5t " + ChatColor.WHITE + "☃");
        lore.add("");
        lore.add("§x§0§f§b§c§0§0M§x§0§e§c§1§0§0e§x§0§e§c§5§0§0r§x§0§d§c§a§0§0r§x§0§c§c§f§0§0y §x§0§b§d§3§0§0C§x§0§b§d§8§0§0h§x§0§a§d§d§0§0r§x§0§9§e§1§0§0i§x§0§9§e§6§0§0s§x§0§8§e§a§0§0t§x§0§7§e§f§0§0m§x§0§6§f§4§0§0a§x§0§6§f§8§0§0s§x§0§5§f§d§0§0!");
        lore.add("§x§0§f§b§c§0§0-§x§0§e§c§1§0§0T§x§0§d§c§7§0§0h§x§0§d§c§c§0§0e§x§0§c§d§2§0§0a§x§0§b§d§7§0§0t§x§0§a§d§d§0§0r§x§0§9§e§2§0§0i§x§0§8§e§7§0§0a §x§0§8§e§d§0§02§x§0§7§f§2§0§00§x§0§6§f§8§0§02§x§0§5§f§d§0§02");
        lore.add("§x§f§f§0§0§0§0H§x§f§f§1§e§1§eo §x§f§e§3§c§3§cH§x§f§e§5§b§5§bo §x§f§e§7§9§7§9H§x§f§d§9§7§9§7o§x§f§d§b§5§b§5!");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setLore(lore);
        meta.setCustomModelData(CustomModelData.CHRISTMAS_PICK);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 6);
        item.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
        item.addUnsafeEnchantment(Enchantment.MENDING, 1);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        christmasPick = item;
    }

}
