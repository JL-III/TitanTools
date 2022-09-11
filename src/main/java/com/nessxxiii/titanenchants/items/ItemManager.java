package com.nessxxiii.titanenchants.items;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


public class ItemManager {

    public static ItemStack powerCrystal;
    public static ItemStack excavator;
    public static ItemStack titanSponge;

    public static void Init(){
        createPowerCrystal();
        createExcavator();
        createTitanSponge(1);
    }

    private static void createPowerCrystal() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add("§x§F§F§0§0§4§CAncient Charge");
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Power Crystal");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,1);
        powerCrystal = item;
    }

    private static void createExcavator() {
        ItemStack item = new ItemStack(Material.GOLDEN_PICKAXE);
        List<String> lore = new ArrayList<>();
        lore.add("A tool used to extract");
        lore.add("Power Crystals from ore");
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "Excavation Tool");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
        item.addUnsafeEnchantment(Enchantment.MULTISHOT, 3);
        excavator = item;
    }

    public static ItemStack createTitanSponge(int amount){

        ItemStack item = new ItemStack(Material.SPONGE, amount);
        ItemMeta meta = item.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(ItemInfo.TITAN_SPONGE_LORE);
        meta.setLore(lore);
        meta.setDisplayName(ItemInfo.SPONGE_DISPLAY_NAME);
        meta.addEnchant(Enchantment.SILK_TOUCH,1,true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        titanSponge = item;

        return item;
    }

}
