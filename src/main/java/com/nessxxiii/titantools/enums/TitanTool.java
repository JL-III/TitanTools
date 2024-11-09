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
import java.util.regex.Pattern;

public enum TitanTool {
    PICK_RED_FORTUNE(
            MiniMessage.miniMessage().deserialize(
                    "<obfuscated><color:#7F0000> %</color></obfuscated>"
                    + "<bold><gradient:#880001:#F50001>The Titan Pick</gradient></bold>"
                    + "<obfuscated><color:#FF0000> %</color></obfuscated>"),
            Material.DIAMOND_PICKAXE,
            ToolColor.RED,
            new HashMap<>(){{
                put(Enchantment.EFFICIENCY, 7);
                put(Enchantment.FORTUNE, 4);
            }},
            CustomModelData.UNCHARGED_TITAN_TOOL
    );
//    PICK_RED_SILK,
//    PICK_YELLOW_FORTUNE,
//    PICK_YELLOW_SILK,
//    PICK_BLUE_FORTUNE,
//    PICK_BLUE_SILK,
//    SHOVEL_RED,
//    AXE_RED,
//    AXE_YELLOW,
//    AXE_BLUE,
//    SWORD_RED,
//    SWORD_YELLOW,
//    SWORD_BLUE,
//    ROD_RED,
//    TEST_TOOL,
//    IMMORTAL_DIADEM;

    private final Component displayName;
    private final Material material;
    private List<Component> lore;
    private final ToolColor toolColor;
    private final HashMap<Enchantment, Integer> enchantments;
    private final int customModelData;

    TitanTool(
            Component displayName,
            Material material,
            ToolColor toolColor,
            HashMap<Enchantment, Integer> enchantments,
            int customModelData
    ) {
        this.displayName =displayName;
        this.material = material;
        this.toolColor = toolColor;
        this.enchantments = enchantments;
        this.customModelData = customModelData;
    }

    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(displayName);
        itemMeta.lore(createLore());
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setCustomModelData(customModelData);
        enchantments.forEach(itemStack::addUnsafeEnchantment);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ToolColor getToolColor() {
        return toolColor;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public static Component gradientText(String text, String startHex, String endHex) {
        if (!isValidHexColor(startHex) || !isValidHexColor(endHex)) {
            throw new IllegalArgumentException("Invalid hex color string provided.");
        }

        String miniMessageString = "<gradient:" + startHex + ":" + endHex + ">" + text + "</gradient>";
        return MiniMessage.miniMessage().deserialize(miniMessageString);
    }

    private static boolean isValidHexColor(String hex) {
        return HEX_COLOR_PATTERN.matcher(hex).matches();
    }

    private static final Pattern HEX_COLOR_PATTERN = Pattern.compile("^#([A-Fa-f0-9]{6})$");

    public List<Component> createLore() {
        MiniMessage miniMessage = MiniMessage.miniMessage();
        List<Component> loreComponents = new ArrayList<>();
        for (Map.Entry<Enchantment, Integer> entry : enchantments.entrySet()) {
            if (romanNumeralMap.get(entry.getValue()) != null) {
                loreComponents.add(miniMessage.deserialize("<dark_gray>" + formatEnchantmentName(entry.getKey().getKey().asString()) + "</dark_gray><color:" + toolColor.getBrightHexCode() + "> " + romanNumeralMap.get(entry.getValue()) + "</color>"));
            } else {
                loreComponents.add(miniMessage.deserialize("<dark_gray>" + entry.getKey() + "</dark_gray>"));
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
    public static String formatEnchantmentName(@NotNull  String enchantmentId) {
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
}
