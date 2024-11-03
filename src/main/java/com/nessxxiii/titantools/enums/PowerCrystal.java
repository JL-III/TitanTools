package com.nessxxiii.titantools.enums;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.nessxxiii.titantools.utils.ConfigManager;
import com.playtheatria.jliii.generalutils.result.Err;
import com.playtheatria.jliii.generalutils.result.Ok;
import com.playtheatria.jliii.generalutils.result.Result;
import org.bukkit.Material;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public enum PowerCrystal {

    COMMON("§x§7§7§7§7§7§7C§x§8§1§8§1§8§1o§x§8§b§8§b§8§bm§x§9§6§9§6§9§6m§x§a§0§a§0§a§0o§x§a§a§a§a§a§an", 1000000, 5),
    UNCOMMON("§x§2§f§8§e§2§fU§x§3§4§9§e§3§4n§x§3§a§a§e§3§ac§x§3§f§b§e§3§fo§x§4§5§c§f§4§5m§x§4§a§d§f§4§am§x§5§0§e§f§5§0o§x§5§5§f§f§5§5n", 1000001, 50),
    SUPER("§x§4§9§4§9§d§aS§x§4§c§4§c§e§3u§x§4§f§4§f§e§dp§x§5§2§5§2§f§6e§x§5§5§5§5§f§fr", 1000002, 100),
    EPIC("§x§d§d§4§a§d§dE§x§e§8§4§e§e§8p§x§f§4§5§1§f§4i§x§f§f§5§5§f§fc", 1000003, 250),
    ULTRA("§x§f§b§6§9§0§0U§x§f§c§7§9§0§0l§x§f§c§8§8§0§0t§x§f§d§9§8§0§0r§x§f§d§a§7§0§0a", 1000004, 1000);

    public static final String POWER_CRYSTAL_DISPLAY = "§x§8§0§0§0§f§f§lP§x§8§c§0§8§f§f§lo§x§9§7§0§f§f§f§lw§x§a§3§1§7§f§f§le§x§a§e§1§f§f§f§lr §x§b§a§2§7§f§f§lC§x§c§5§2§e§f§f§lr§x§d§1§3§6§f§f§ly§x§d§c§3§e§f§f§ls§x§e§8§4§6§f§f§lt§x§f§3§4§d§f§f§la§x§f§f§5§5§f§f§ll";
    public static final String ANCIENT_CHARGE_STRING_LITERAL = "§x§c§1§0§0§8§bA§x§c§6§0§0§8§6n§x§c§b§0§0§8§1c§x§d§1§0§0§7§bi§x§d§6§0§0§7§6e§x§d§b§0§0§7§1n§x§e§0§0§0§6§ct §x§e§5§0§0§6§6C§x§e§a§0§0§6§1h§x§f§0§0§0§5§ca§x§f§5§0§0§5§7r§x§f§a§0§0§5§1g§x§f§f§0§0§4§ce ";
    public static final String POWER_CRYSTAL_TYPE_STRING_LITERAL = "§x§8§d§0§0§f§bT§x§a§8§0§0§f§cy§x§c§3§0§0§f§cp§x§d§d§0§0§f§de§x§f§8§0§0§f§d: ";

    private final String typeLore;
    private final Integer customModelData;
    private final Integer charge;

    PowerCrystal(String typeLore, Integer customModelData, Integer charge) {
        this.typeLore = typeLore;
        this.customModelData = customModelData;
        this.charge = charge;
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(Material.AMETHYST_SHARD);
        List<String> lore = new ArrayList<>();
        lore.add(ANCIENT_CHARGE_STRING_LITERAL + charge);
        lore.add(POWER_CRYSTAL_TYPE_STRING_LITERAL + typeLore);
        item.setLore(lore);
        ItemMeta meta = item.getItemMeta();
        Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();
        if(modifiers == null) {
            modifiers = HashMultimap.create();
            meta.setAttributeModifiers(modifiers);
        }
        meta.setDisplayName(POWER_CRYSTAL_DISPLAY);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(customModelData);
        item.addUnsafeEnchantment(Enchantment.CHANNELING,5);
        item.setItemMeta(meta);
        return item;
    }

    public static Result<PowerCrystal, Exception> getPowerCrystalType(ItemStack item) {
        for (PowerCrystal powerCrystal : PowerCrystal.values()) {
            if (powerCrystal.getItemStack().isSimilar(item)) {
                return new Ok<>(powerCrystal);
            }
        }
        return new Err<>(new Exception("This is not a power crystal."));
    }
}
