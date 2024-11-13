package com.nessxxiii.titantools.enums;

import com.nessxxiii.titantools.itemmanagement.CustomModelData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum TitanTool {
    PICK_RED_FORTUNE(
            Material.DIAMOND_PICKAXE,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 7);
                put(Enchantment.FORTUNE, 4);
            }}
    ),
    PICK_RED_SILK(
            Material.DIAMOND_PICKAXE,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 9);
                put(Enchantment.SILK_TOUCH, 1);
            }}
    ),
    PICK_YELLOW_FORTUNE(
            Material.DIAMOND_PICKAXE,
            ToolColor.YELLOW,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 9);
                put(Enchantment.FORTUNE, 4);
            }}
    ),
    PICK_YELLOW_SILK(
            Material.DIAMOND_PICKAXE,
            ToolColor.YELLOW,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 11);
                put(Enchantment.SILK_TOUCH, 1);
            }}
    ),
    PICK_BLUE_FORTUNE(
            Material.DIAMOND_PICKAXE,
            ToolColor.BLUE,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 11);
                put(Enchantment.FORTUNE, 5);
            }}
    ),
    PICK_BLUE_SILK(
            Material.DIAMOND_PICKAXE,
            ToolColor.BLUE,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 13);
                put(Enchantment.SILK_TOUCH, 1);
            }}
    ),
    SHOVEL_RED(
            Material.DIAMOND_SHOVEL,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 10);
                put(Enchantment.SILK_TOUCH, 1);
            }}
    ),
    AXE_RED(
            Material.DIAMOND_AXE,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 10);
                put(Enchantment.SILK_TOUCH, 1);
            }}
    ),
    AXE_YELLOW(
            Material.DIAMOND_AXE,
            ToolColor.YELLOW,
            new HashMap<>(){{
                put(Enchantment.BANE_OF_ARTHROPODS, 5);
                put(Enchantment.EFFICIENCY, 10);
                put(Enchantment.KNOCKBACK, 2);
                put(Enchantment.LOOTING, 4);
                put(Enchantment.SHARPNESS, 5);
                put(Enchantment.SMITE, 5);
            }}
    ),
    AXE_BLUE(
            Material.DIAMOND_AXE,
            ToolColor.BLUE,
            new HashMap<>(){{
                put(Enchantment.BANE_OF_ARTHROPODS, 5);
                put(Enchantment.EFFICIENCY, 13);
                put(Enchantment.KNOCKBACK, 3);
                put(Enchantment.LOOTING, 7);
                put(Enchantment.SHARPNESS, 7);
                put(Enchantment.SMITE, 5);
            }}
    ),
    SWORD_RED(
            Material.DIAMOND_SWORD,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.BANE_OF_ARTHROPODS, 5);
                put(Enchantment.FIRE_ASPECT, 2);
                put(Enchantment.KNOCKBACK, 2);
                put(Enchantment.LOOTING, 5);
                put(Enchantment.SHARPNESS, 5);
                put(Enchantment.SMITE, 5);
                put(Enchantment.SWEEPING_EDGE, 3);
            }}
    ),
    SWORD_YELLOW(
            Material.DIAMOND_SWORD,
            ToolColor.YELLOW,
            new HashMap<>(){{
                put(Enchantment.BANE_OF_ARTHROPODS, 5);
                put(Enchantment.FIRE_ASPECT, 2);
                put(Enchantment.KNOCKBACK, 2);
                put(Enchantment.LOOTING, 5);
                put(Enchantment.SHARPNESS, 7);
                put(Enchantment.SMITE, 5);
                put(Enchantment.SWEEPING_EDGE, 3);
            }}
    ),
    SWORD_BLUE(
            Material.DIAMOND_SWORD,
            ToolColor.BLUE,
            new HashMap<>(){{
                put(Enchantment.BANE_OF_ARTHROPODS, 5);
                put(Enchantment.KNOCKBACK, 2);
                put(Enchantment.LOOTING, 6);
                put(Enchantment.SHARPNESS, 7);
                put(Enchantment.SMITE, 5);
                put(Enchantment.SWEEPING_EDGE, 3);
            }}
    ),
    ROD_RED(
            Material.FISHING_ROD,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.LURE, 5);
                put(Enchantment.LUCK_OF_THE_SEA, 5);
            }}
    );

    private final Material material;
    private final ToolColor toolColor;
    private final HashMap<Enchantment, Integer> enchantments;

    TitanTool(
            Material material,
            ToolColor toolColor,
            HashMap<Enchantment, Integer> enchantments
    ) {
        this.material = material;
        this.toolColor = toolColor;
        this.enchantments = enchantments;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(createDisplayName());
        itemMeta.lore(createLore());
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData(CustomModelData.UNCHARGED_TITAN_TOOL);
        enchantments.forEach((enchantment, level) -> itemMeta.addEnchant(enchantment, level, true));
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ToolColor getToolColor() {
        return toolColor;
    }

    private Component createDisplayName() {
        return MiniMessage.miniMessage().deserialize(
                "<obfuscated><color:" + toolColor.getDarkHexCode() + ">%</color></obfuscated>"
                        + "<bold><gradient:" + toolColor.getDarkHexCode() + ":" + toolColor.getBrightHexCode() + "> The Titan " + formatToolName() + "</gradient></bold>"
                        + "<obfuscated><color:" + toolColor.getBrightHexCode() + "> %</color></obfuscated>");
    }

    public List<Component> createLore() {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        List<Component> loreComponents = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            if (romanNumeralMap.get(entry.getValue()) != null && !entry.getKey().equals(Enchantment.SILK_TOUCH)) {
                loreComponents.add(miniMessage.deserialize("<dark_gray>" + formatEnchantmentName(entry.getKey().getKey().asString()) + "</dark_gray><color:" + toolColor.getBrightHexCode() + "> " + romanNumeralMap.get(entry.getValue()) + "</color>"));
            } else {
                loreComponents.add(miniMessage.deserialize("<dark_gray>" + formatEnchantmentName(entry.getKey().getKey().asString()) + "</dark_gray>"));
            }
        }
        loreComponents.add(miniMessage.deserialize("<dark_gray>Unbreakable</dark_gray>"));
        loreComponents.add(miniMessage.deserialize("<gray> </gray>"));
        loreComponents.add(miniMessage.deserialize("<color:" + toolColor.getDarkHexCode() + ">Ancient Power</color><color:" + toolColor.getBrightHexCode() + "> Ω</color>"));
        loreComponents.add(miniMessage.deserialize("<color:" + toolColor.getDarkHexCode() + ">  ● Charge</color><color:" + toolColor.getBrightHexCode() + "> 0</color>"));
        loreComponents.add(miniMessage.deserialize("<color:" + toolColor.getDarkHexCode() + ">  ● Status</color><color:" + toolColor.getBrightHexCode() + "> OFF</color>"));
        loreComponents.add(miniMessage.deserialize("<gray> </gray>"));
        loreComponents.add(miniMessage.deserialize("<gradient:" + toolColor.getDarkHexCode() + ":" + toolColor.getBrightHexCode() + ">A relic from a time</gradient>"));
        loreComponents.add(miniMessage.deserialize("<gradient:" + toolColor.getDarkHexCode() + ":" + toolColor.getBrightHexCode() + ">long past...</gradient>"));
        return loreComponents;
    }

    public static final HashMap<Integer, String> romanNumeralMap = new HashMap<>(){{
        put(1, "I");
        put(2, "II");
        put(3, "III");
        put(4, "IV");
        put(5, "V");
        put(6, "VI");
        put(7, "VII");
        put(8, "VIII");
        put(9, "IX");
        put(10, "X");
        put(11, "XI");
        put(12, "XII");
        put(13, "XIII");
        put(14, "XIV");
        put(15, "XV");
    }};

    /**
     * Formats a Minecraft enchantment name by removing the namespace and capitalizing the first letter.
     *
     * @param enchantmentId The enchantment ID (e.g., "minecraft:unbreaking").
     * @return The formatted enchantment name (e.g., "Unbreaking").
     */
    public static String formatEnchantmentName(@NotNull String enchantmentId) {
        if (enchantmentId.isEmpty()) {
            return "";
        }

        String[] parts = enchantmentId.split(":");
        String name = parts.length > 1 ? parts[1] : parts[0];

        name = name.replace('_', ' ').toLowerCase();

        String[] words = name.split(" ");
        StringBuilder formattedName = new StringBuilder();

        for (String word : words) {
            if (!word.isEmpty()) {
                formattedName.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1))
                        .append(" ");
            }
        }
        return formattedName.toString().trim();
    }

    public String formatToolName() {
        switch (material) {
            case Material.DIAMOND_PICKAXE -> {
                return "Pick";
            }
            case Material.DIAMOND_AXE -> {
                return "Axe";
            }
            case Material.DIAMOND_SWORD -> {
                return "Sword";
            }
            case Material.DIAMOND_SHOVEL -> {
                return "Shovel";
            }
            case Material.FISHING_ROD -> {
                return "Rod";
            }
            default -> {
                return material.name();
            }
        }
    }
}
