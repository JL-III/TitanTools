package com.nessxxiii.titantools.enums;

import com.nessxxiii.titantools.itemmanagement.CustomModelData;
import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public enum TheatriaTool {
    EXCAVATOR(
            Material.GOLDEN_PICKAXE,
            ItemInfo.EXCAVATION_TOOL_DISPLAY_NAME,
            CustomModelData.EXCAVATOR,
            new ArrayList<>(){{
                add("§x§f§b§b§5§0§0A tool used to extract");
                add(ChatColor.DARK_PURPLE + "Power Crystals §x§f§b§b§5§0§0from");
                add("§x§f§b§b§5§0§0amethyst blocks");
            }},
            new HashMap<>(){{
                put(Enchantment.UNBREAKING, 5);
                put(Enchantment.MULTISHOT, 3);
            }}
    ),
    SUN_FISH(
            Material.PUFFERFISH,
            ChatColor.YELLOW + "" +  ChatColor.ITALIC + "" + ChatColor.BOLD + "The Sun Fish",
            CustomModelData.SUN_FISH,
            new ArrayList<>(){{
                add("Hold one in your hand");
                add("and left click!");
            }},
            new HashMap<>(){{
                put(Enchantment.UNBREAKING, 1);
            }}
    ),
    NIGHT_FISH(
            Material.TROPICAL_FISH,
            ChatColor.BLUE + "" +  ChatColor.ITALIC + "" + ChatColor.BOLD + "Night Fish",
            CustomModelData.NIGHT_FISH,
            new ArrayList<>(){{
                add("Hold one in your hand");
                add("and left click!");
            }},
            new HashMap<>(){{
                put(Enchantment.UNBREAKING, 1);
            }}
    ),
    ETHEREAL_FRAGMENT(
            Material.ECHO_SHARD,
            ChatColor.LIGHT_PURPLE + "" +  ChatColor.ITALIC + "" + ChatColor.BOLD + "Ethereal Fragment",
            CustomModelData.ETHEREAL_FRAGMENT,
            new ArrayList<>(){{
                add("Pieces of strange");
                add("material... collect 16");
                add("and you can trade");
                add("it in for a special tool");
            }},
            new HashMap<>()
    ),
    CHRISTMAS_PICK(
            Material.NETHERITE_PICKAXE,
            "§x§0§f§b§c§0§0§l§o" + ChatColor.MAGIC + " c " + ChatColor.RESET + "§x§0§f§b§c§0§0§l§oT§x§0§e§c§0§0§0§l§oh§x§0§e§c§5§0§0§l§oe §x§0§d§c§9§0§0§l§oC§x§0§c§c§d§0§0§l§oh§x§0§c§d§2§0§0§l§or§x§0§b§d§6§0§0§l§oi§x§0§a§d§a§0§0§l§os§x§0§a§d§f§0§0§l§ot§x§0§9§e§3§0§0§l§om§x§0§8§e§7§0§0§l§oa§x§0§8§e§c§0§0§l§os §x§0§7§f§0§0§0§l§oP§x§0§6§f§4§0§0§l§oi§x§0§6§f§9§0§0§l§oc§x§0§5§f§d§0§0§l§ok" + ChatColor.MAGIC + " c",
            CustomModelData.CHRISTMAS_PICK,
            new ArrayList<>(){{
                add("§x§f§f§0§0§0§0E§x§f§f§1§4§1§4f§x§f§f§2§8§2§8f§x§f§e§3§c§3§ci§x§f§e§5§0§5§0c§x§f§e§6§5§6§5i§x§f§e§7§9§7§9e§x§f§d§8§d§8§dn§x§f§d§a§1§a§1c§x§f§d§b§5§b§5y " + ChatColor.WHITE + "VI");
                add("§x§f§f§0§0§0§0F§x§f§f§1§e§1§eo§x§f§e§3§c§3§cr§x§f§e§5§b§5§bt§x§f§e§7§9§7§9u§x§f§d§9§7§9§7n§x§f§d§b§5§b§5e " + ChatColor.WHITE + "III");
                add("§x§f§f§0§0§0§0M§x§f§f§1§e§1§ee§x§f§e§3§c§3§cn§x§f§e§5§b§5§bd§x§f§e§7§9§7§9i§x§f§d§9§7§9§7n§x§f§d§b§5§b§5g");
                add("§x§f§f§0§0§0§0U§x§f§f§1§4§1§4n§x§f§f§2§8§2§8b§x§f§e§3§c§3§cr§x§f§e§5§0§5§0e§x§f§e§6§5§6§5a§x§f§e§7§9§7§9k§x§f§d§8§d§8§di§x§f§d§a§1§a§1n§x§f§d§b§5§b§5g " + ChatColor.WHITE + "V");
                add("§x§f§f§0§0§0§0C§x§f§f§0§d§0§dh§x§f§f§1§a§1§ar§x§f§f§2§7§2§7i§x§f§e§3§4§3§4s§x§f§e§4§1§4§1t§x§f§e§4§e§4§em§x§f§e§5§b§5§ba§x§f§e§6§7§6§7s §x§f§e§7§4§7§4S§x§f§e§8§1§8§1p§x§f§d§8§e§8§ei§x§f§d§9§b§9§br§x§f§d§a§8§a§8i§x§f§d§b§5§b§5t " + ChatColor.WHITE + "☃");
                add("");
                add("§x§0§f§b§c§0§0M§x§0§e§c§1§0§0e§x§0§e§c§5§0§0r§x§0§d§c§a§0§0r§x§0§c§c§f§0§0y §x§0§b§d§3§0§0C§x§0§b§d§8§0§0h§x§0§a§d§d§0§0r§x§0§9§e§1§0§0i§x§0§9§e§6§0§0s§x§0§8§e§a§0§0t§x§0§7§e§f§0§0m§x§0§6§f§4§0§0a§x§0§6§f§8§0§0s§x§0§5§f§d§0§0!");
                add("§x§0§f§b§c§0§0-§x§0§e§c§1§0§0T§x§0§d§c§7§0§0h§x§0§d§c§c§0§0e§x§0§c§d§2§0§0a§x§0§b§d§7§0§0t§x§0§a§d§d§0§0r§x§0§9§e§2§0§0i§x§0§8§e§7§0§0a §x§0§8§e§d§0§02§x§0§7§f§2§0§00§x§0§6§f§8§0§02§x§0§5§f§d§0§02");
                add("§x§f§f§0§0§0§0H§x§f§f§1§e§1§eo §x§f§e§3§c§3§cH§x§f§e§5§b§5§bo §x§f§e§7§9§7§9H§x§f§d§9§7§9§7o§x§f§d§b§5§b§5!");
            }},
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 6);
                put(Enchantment.FORTUNE, 3);
                put(Enchantment.MENDING, 1);
                put(Enchantment.UNBREAKING, 5);
            }}
    ),
    GINGERBREAD_MAN(
            Material.COOKIE,
            "§x§6§e§f§b§0§0§l§oT§x§6§7§f§7§0§7§l§oh§x§6§0§f§4§0§e§l§oe §x§5§9§f§0§1§5§l§oG§x§5§3§e§d§1§c§l§oi§x§4§c§e§9§2§3§l§on§x§4§5§e§5§2§a§l§og§x§3§e§e§2§3§1§l§oe§x§3§7§d§e§3§8§l§or§x§3§0§d§a§3§f§l§ob§x§2§9§d§7§4§6§l§or§x§2§2§d§3§4§d§l§oe§x§1§c§d§0§5§4§l§oa§x§1§5§c§c§5§b§l§od §x§0§e§c§8§6§2§l§oM§x§0§7§c§5§6§9§l§oa§x§0§0§c§1§7§0§l§on",
            CustomModelData.GINGERBREAD_MAN,
            new ArrayList<>(){{
                add("§x§0§f§b§c§0§0M§x§0§e§c§1§0§0e§x§0§e§c§5§0§0r§x§0§d§c§a§0§0r§x§0§c§c§f§0§0y §x§0§b§d§3§0§0C§x§0§b§d§8§0§0h§x§0§a§d§d§0§0r§x§0§9§e§1§0§0i§x§0§9§e§6§0§0s§x§0§8§e§a§0§0t§x§0§7§e§f§0§0m§x§0§6§f§4§0§0a§x§0§6§f§8§0§0s§x§0§5§f§d§0§0!");
                add("§x§0§f§b§c§0§0-§x§0§e§c§1§0§0T§x§0§d§c§7§0§0h§x§0§d§c§c§0§0e§x§0§c§d§2§0§0a§x§0§b§d§7§0§0t§x§0§a§d§d§0§0r§x§0§9§e§2§0§0i§x§0§8§e§7§0§0a §x§0§8§e§d§0§02§x§0§7§f§2§0§00§x§0§6§f§8§0§02§x§0§5§f§d§0§02");
                add("§x§f§f§0§0§0§0H§x§f§f§1§e§1§eo §x§f§e§3§c§3§cH§x§f§e§5§b§5§bo §x§f§e§7§9§7§9H§x§f§d§9§7§9§7o§x§f§d§b§5§b§5!");
            }},
            new HashMap<>()
    );

    private final Material material;
    private final String displayName;
    private final int customModelData;
    private final List<String> itemLore;
    private final HashMap<Enchantment, Integer> enchantments;

    TheatriaTool(
            Material material,
            String displayName,
            int customModelData,
            List<String> itemLore,
            HashMap<Enchantment, Integer> enchantments
    ) {
        this.material = material;
        this.displayName = displayName;
        this.customModelData = customModelData;
        this.itemLore = itemLore;
        this.enchantments = enchantments;
    }

    public ItemStack getItemStack() {
        ItemStack item = new ItemStack(material);
        item.setLore(new ArrayList<>(itemLore));
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.setCustomModelData(customModelData);
        enchantments.forEach(((enchantment, integer) -> meta.addEnchant(enchantment, integer, true)));
        item.setItemMeta(meta);
        return item;
    }
}
