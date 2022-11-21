package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.ItemInfo;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.disableEffect;
import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.enableEffect;

public class ToggleAncientPower implements Listener {

    private Plugin plugin;
    private static Player player;

    public ToggleAncientPower(Plugin plugin) {
        this.plugin = plugin;
    };

    @EventHandler
    public static void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;
        player = event.getPlayer();

        if (processPlayerStateValidation()) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;
            player.sendMessage("Passed validate toggle attempt");

            if (processItemValidation(itemInMainHand) && ItemInfo.isCharged(itemInMainHand)) {
                player.setCooldown(coolDown,25);
                player.sendMessage("if statement line 43 activate click");
                event.setCancelled(true);
                ToggleAncientPower.toggleEnchant(itemInMainHand,player,false);
            } else if (processItemValidation(itemInMainHand) && ItemInfo.isImbued(itemInMainHand)) {
                player.setCooldown(coolDown,25);
                ToggleAncientPower.toggleEnchant(itemInMainHand, player, true);
            } else {
                player.sendMessage("You cannot toggle this item since it is not imbued or charged");
            }
        }
    }

    public static void toggleEnchant(ItemStack item, Player player, boolean isImbued) {

        int itemLevel = ItemInfo.getItemLevel(item);
        String color = ItemInfo.getColorForAddChargeLore(item);

        switch (itemLevel) {
            case 1,2 -> {
                powerLevelConversion(item, itemLevel, color, isImbued);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 1));
                enableEffect(player);
            }
            case 3 -> {
                powerLevelConversion(item, itemLevel, color, isImbued);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power deactivated");
                disableEffect(player);
            }
            default -> {
                if (ItemInfo.isDormantCharged(item)) {
                    powerLevelConversion(item, itemLevel, color, isImbued);
                    player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 2));
                    enableEffect(player);
                } else {
                    player.sendActionBar("An error has occurred");
                }
            }
        }

    }

    public static void handleFullInventory(ItemStack item, Player player, boolean isImbued, int currentLevel) {
        int itemLevel = currentLevel;
        String color = ItemInfo.getColorForAddChargeLore(item);

        String powerThreeToPowerTwo = ItemInfo.IMBUED_TWO;
        String powerTwoToPowerOne = ItemInfo.IMBUED_ONE;
        String powerOneToInactive = ItemInfo.IMBUED_INACTIVE;

        if (!isImbued) {
            powerThreeToPowerTwo = ItemInfo.CHARGED_TWO;
            powerTwoToPowerOne = ItemInfo.CHARGED_ONE;
            powerOneToInactive = ItemInfo.CHARGED_INACTIVE;
        }

        switch (currentLevel) {
            case 3,2 -> {
                powerLevelConversion(item, itemLevel, color, isImbued);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power set to PowerLvl: " + (currentLevel - 1));
                disableEffect(player);
            }
            case 1 -> {
                powerLevelConversion(item, itemLevel, color, isImbued);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power deactivated");
                disableEffect(player);
            }
            default -> {
            }
        }

    }

    public static void disableEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.CHARGED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void imbue(ItemStack item){
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index, ItemInfo.IMBUED_ONE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void disableImbuedEnchant(ItemStack item) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,ItemInfo.IMBUED_INACTIVE);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void powerLevelConversion(ItemStack item, int itemLevel, String color, boolean isImbued) {

        String loreToAdd;
        int itemLevelToGet = itemLevel <= 2 ? itemLevel + 1 : 0;

        if (isImbued) {
            loreToAdd = ItemInfo.IMBUED_LORE_MATRIX.get(color)[itemLevelToGet];
        } else {
            loreToAdd = ItemInfo.CHARGED_LORE_MATRIX.get(color)[itemLevelToGet];
        }
        printLog(isImbued, itemLevel, itemLevelToGet, color);
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index, loreToAdd);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
        NBTItem nbti = new NBTItem(item);
        nbti.setInteger("itemLevel", itemLevelToGet);
        if (itemLevelToGet == 0) {
            nbti.setBoolean("isActive", false);
        } else if (!nbti.getBoolean("isActive")) {
            nbti.setBoolean("isActive", true);
        }
        nbti.applyNBT(item);
    }

    private static boolean processPlayerStateValidation() {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

    private static boolean processItemValidation(ItemStack item){
        if (!ItemInfo.isCharged(item) && !ItemInfo.isImbued(item)) {
            Bukkit.getConsoleSender().sendMessage("item was not charged and not imbued");
            return false;
        }
        if (!ItemInfo.isAllowedTitanType(item)) {
            Bukkit.getConsoleSender().sendMessage("Item is not allowed titan type");
            return false;
        }
        if (!item.hasItemMeta()) {
            Bukkit.getConsoleSender().sendMessage("Item does not have item meta");
            return false;
        }
        if (!ItemInfo.isTitanTool(item)) {
            Bukkit.getConsoleSender().sendMessage("Item is not a titan tool");
            return false;
        }
        return true;
    }

    public static void printLog(boolean isImbued, int itemLevel, int itemLevelToGet, String color) {
        Bukkit.getConsoleSender().sendMessage("Item imbued status: " + isImbued);
        Bukkit.getConsoleSender().sendMessage("Item powerLevel: " + itemLevel);
        Bukkit.getConsoleSender().sendMessage("ItemLevel to get: " + itemLevelToGet);
        Bukkit.getConsoleSender().sendMessage("Item Color: " + color);
    }

}
