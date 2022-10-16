package com.nessxxiii.titanenchants.listeners.enchantmentManager;

import com.nessxxiii.titanenchants.items.ItemInfo;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.depletedChargeEffect;


public class ChargeManagement implements Listener {


    @EventHandler
    public static void applyCharge(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();

        if (isValidated(player.getItemOnCursor(), event.getCurrentItem())) {
            ItemStack itemOnCursor = player.getItemOnCursor();
            int chargeAmount = getChargeAmount(itemOnCursor, itemOnCursor.getAmount());
            player.getItemOnCursor().setAmount(0);
            addChargeLore(player, event.getCurrentItem(),chargeAmount);
            player.getWorld().spawnParticle(Particle.FIREWORKS_SPARK,player.getEyeLocation(),100);
            player.getWorld().playSound(player.getLocation(), Sound.BLOCK_CONDUIT_ACTIVATE,10, 1);
            event.setCancelled(true);
        }
    }

    private static int getChargeAmount(ItemStack itemOnCursor, int amount) {

        return switch (ItemInfo.getPowerCrystalType(itemOnCursor)) {
            case COMMON -> 5 * amount;
            case UNCOMMON -> 50 * amount;
            case SUPER -> 100 * amount;
            case EPIC -> 250 * amount;
            case ULTRA -> 1000 * amount;
            default -> 0;
        };
    }

    private static boolean isValidated(ItemStack itemOnCursor, ItemStack itemClicked) {
        //item on cursor must be powercrystal
        if (itemOnCursor.getItemMeta() == null) return false;
        if (!ItemInfo.isPowerCrystal(itemOnCursor)) return false;

        //item clicked is the titan tool
        if (itemClicked.getType() == Material.AIR) return false;
        if (ItemInfo.isPowerCrystal(itemClicked)) return false;
        if (!ItemInfo.isTitanTool(itemClicked)) return false;
        if (!ItemInfo.isAllowedTitanType(itemClicked)) return false;
        if (ItemInfo.isImbued(itemClicked)) return false;
        return true;
    }

    public static void addChargeLore(Player player, ItemStack item, Integer amount){

        List<String> loreList = item.getItemMeta().getLore();
        int index = ItemInfo.getAncientPowerLoreIndex(loreList);

        int chargeIndex = index + 1;
        String itemColor = ItemInfo.getColor(item);
        int previousCharge;
        int finalCharge;
        if (ItemInfo.isCharged(item)) {
            previousCharge = Integer.parseInt(loreList.get(chargeIndex).substring(24));
            finalCharge = previousCharge + (amount);
        } else {
            finalCharge = amount;
            previousCharge = 0;
        }
        if (itemColor != null) {
            String indexString;
            String chargeIndexString;
            switch (itemColor)
            {
                case "RED" -> {
                    indexString = ItemInfo.CHARGED_RED_ONE;
                    chargeIndexString = ItemInfo.ANCIENT_CHARGE_RED;
                }
                case "YELLOW" -> {
                    indexString = ItemInfo.CHARGED_YELLOW_ONE;
                    chargeIndexString = ItemInfo.ANCIENT_CHARGE_YELLOW;
                }
                case "BLUE" -> {
                    indexString = ItemInfo.CHARGED_BLUE_ONE;
                    chargeIndexString = ItemInfo.ANCIENT_CHARGE_BLUE;
                }
                default -> {
                    indexString = "Error";
                    chargeIndexString = "Error";
                }
            }
            if (indexString.equals("Error")) {
                Bukkit.getConsoleSender().sendMessage("Error occurred while adding charged lore.");
                return;
            }
            loreList.set(index, indexString);
            loreList.set(chargeIndex, chargeIndexString + " " + finalCharge);
            ItemMeta meta = item.getItemMeta();
            meta.setLore(loreList);
            item.setItemMeta(meta);
            printLog(player, item, previousCharge, amount, finalCharge, indexString, chargeIndexString);
        }
    }

    public static void decreaseChargeLore(ItemStack item, Player player, Integer amountTaken){
        List<String> loreList = item.getItemMeta().getLore();
        int index = ItemInfo.getAncientPowerLoreIndex(loreList);

        if (ItemInfo.isChargedAndActive(item)) {
            int remainingCharge = Integer.parseInt(loreList.get(index + 1).substring(24)) - amountTaken;
            String color = ItemInfo.getColor(item);
            ItemMeta meta = item.getItemMeta();
            if (remainingCharge < 1) {
                String indexString;
                switch (color)
                {
                    case "RED" -> {
                        indexString = ItemInfo.ANCIENT_RED;
                    }
                    case "YELLOW" -> {
                        indexString = ItemInfo.ANCIENT_YELLOW;
                    }
                    case "BLUE" -> {
                        indexString = ItemInfo.ANCIENT_BLUE;
                    }
                    default -> {
                        indexString = "Error";
                    }
                }
                loreList.set(index, indexString);
                loreList.set(index + 1, ItemInfo.ANCIENT_DEPLETED);
                depletedChargeEffect(player);
            } else {
                String chargeColorString;
                switch (color)
                {
                    case "RED" -> {
                        chargeColorString = ItemInfo.RED;
                    }
                    case "YELLOW" -> {
                        chargeColorString = ItemInfo.YELLOW;
                    }
                    case "BLUE" -> {
                        chargeColorString = ItemInfo.BLUE;
                    }
                    default -> {
                        chargeColorString = "";
                    }
                }
                loreList.set(index + 1, ItemInfo.ANCIENT_CHARGE + chargeColorString + " " + remainingCharge);
                player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CPowerLvl: " + ChatColor.GREEN + amountTaken + " "
                        + ChatColor.ITALIC + "§x§F§F§0§0§4§CCharge: " + ChatColor.YELLOW + (remainingCharge > 1 ? remainingCharge : 0)));
            }
            meta.setLore(loreList);
            item.setItemMeta(meta);
        }
    }

    public static void printLog(Player player, ItemStack item, int previousCharge, int amount, int finalCharge, String indexString, String chargeIndexString) {
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + player.getName() + "§x§f§b§0§0§f§1" + " used a PowerCrystal on: " + ChatColor.AQUA + item.getType().name());
        Bukkit.getConsoleSender().sendMessage(item.getItemMeta().hasDisplayName() ? Component.text("Name: ", TextColor.color(5, 250, 83)).append(item.getItemMeta().displayName()) : Component.text("No display name to show", TextColor.color(168, 50, 50)));
        Bukkit.getConsoleSender().sendMessage("§x§f§b§0§0§f§1" + "Prev Charge " + ChatColor.GREEN + previousCharge + "§x§f§b§0§0§f§1" + " PCrystal Charge: " + ChatColor.GREEN + amount);
        Bukkit.getConsoleSender().sendMessage(indexString + " " + chargeIndexString + " " + finalCharge);
    }

}
