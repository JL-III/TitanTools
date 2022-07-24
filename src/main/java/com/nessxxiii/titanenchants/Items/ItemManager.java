package com.nessxxiii.titanenchants.Items;

import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


public class ItemManager {

    public static ItemStack powerCrystal;

    public ItemStack getPowerCrystal() {
        return powerCrystal;
    }

    public void setPowerCrystal(ItemStack powerCrystal) {
        this.powerCrystal = powerCrystal;
    }

    public static void Init(){
        createPowerCrystal();
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

    public static void createTitanPickFortDiamond(Player player) {
        Set<ItemStack> allowedMaterials = new HashSet<>();
        allowedMaterials.add(new ItemStack(Material.DIAMOND_PICKAXE));
        allowedMaterials.add(new ItemStack(Material.NETHERITE_PICKAXE));
        Map<Enchantment, Integer> allowedEnchantments = player.getInventory().getItemInMainHand().getEnchantments();
        if (allowedEnchantments.get(Enchantment.DURABILITY) == 10) {
            player.sendMessage("This item is level 10 unbreaking");
        }
    }

}
