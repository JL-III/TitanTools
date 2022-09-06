package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.ItemInfo;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
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
//            player.sendMessage("Passed validate toggle attempt");

            if (processItemValidation(itemInMainHand)
                    && ItemInfo.isCharged (itemInMainHand)) {
                player.setCooldown(coolDown,25);
                event.setCancelled(true);
                ToggleAncientPower.toggleEnchant(itemInMainHand,player,false);
            } else if (processItemValidation(itemInMainHand)
                    && ItemInfo.isImbued(itemInMainHand)) {
                player.setCooldown(coolDown,25);
                ToggleAncientPower.toggleEnchant(itemInMainHand, player, true);
            } else {
                player.sendMessage("You cannot toggle this item since it is not imbued or charged");
            }
        }
    }

    public static void toggleEnchant(ItemStack item, Player player, boolean isImbued) {
        String inactiveToPowerOne = ItemInfo.IMBUED_ONE;
        String powerOneToPowerTwo = ItemInfo.IMBUED_TWO;
        String powerTwoToPowerThree = ItemInfo.IMBUED_THREE;
        String powerThreeToInactive = ItemInfo.IMBUED_INACTIVE;
        int itemLevel = ItemInfo.getItemLevel(item);

        if (!isImbued) {
            inactiveToPowerOne = ItemInfo.CHARGED_ONE;
            powerOneToPowerTwo = ItemInfo.CHARGED_TWO;
            powerTwoToPowerThree = ItemInfo.CHARGED_THREE;
            powerThreeToInactive = ItemInfo.CHARGED_INACTIVE;
        }
        switch (itemLevel) {
            case 1 -> {
                powerLevelConversion(item, powerOneToPowerTwo);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 1));
                new TitanEnchantEffects().enableEffect(player);
            }
            case 2 -> {
                powerLevelConversion(item, powerTwoToPowerThree);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 1));
                new TitanEnchantEffects().enableEffect(player);
            }
            case 3 -> {
                powerLevelConversion(item, powerThreeToInactive);
                player.sendActionBar(ChatColor.GREEN + "Ancient Power deactivated");
                new TitanEnchantEffects().disableEffect(player);
            }
            default -> {
                if (ItemInfo.isDormantCharged(item)) {
                    powerLevelConversion(item, inactiveToPowerOne);
                    player.sendActionBar(ChatColor.GREEN + "Ancient Power set to PowerLvl: " + (itemLevel + 2));
                    new TitanEnchantEffects().enableEffect(player);
                } else {
                    player.sendActionBar("An error has occurred");
                }
            }
        }

    }

    public static void handleFullInventory(ItemStack item, Player player, boolean isImbued, int currentLevel) {
        String powerThreeToPowerTwo = ItemInfo.IMBUED_TWO;
        String powerTwoToPowerOne = ItemInfo.IMBUED_ONE;
        String powerOneToInactive = ItemInfo.IMBUED_INACTIVE;

        if (!isImbued) {
            powerThreeToPowerTwo = ItemInfo.CHARGED_TWO;
            powerTwoToPowerOne = ItemInfo.CHARGED_ONE;
            powerOneToInactive = ItemInfo.CHARGED_INACTIVE;
        }
        switch (currentLevel) {
            case 3 -> {
                powerLevelConversion(item, powerThreeToPowerTwo);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power set to PowerLvl: " + (currentLevel - 1));
                new TitanEnchantEffects().disableEffect(player);
            }
            case 2 -> {
                powerLevelConversion(item, powerTwoToPowerOne);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power set to PowerLvl: " + (currentLevel - 1));
                new TitanEnchantEffects().disableEffect(player);
            }
            case 1 -> {
                powerLevelConversion(item, powerOneToInactive);
                player.sendMessage(ChatColor.GREEN + "§CInventory is full - Ancient Power deactivated");
                new TitanEnchantEffects().disableEffect(player);
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

    public static void powerLevelConversion(ItemStack item, String loreChange) {
        List<String> loreList = item.getItemMeta().getLore();
        Integer index = ItemInfo.getAncientPowerLoreIndex(loreList);
        loreList.set(index,loreChange);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    private static boolean processPlayerStateValidation() {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

    private static boolean processItemValidation(ItemStack item){
        if (!ItemInfo.hasCharge(item) && !ItemInfo.isImbued(item)) return false;
        if (!ItemInfo.isAllowedTitanType(item)) return false;
        if (!item.hasItemMeta()) return false;
        return ItemInfo.isTitanTool(item);
    }
}
