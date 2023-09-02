package com.nessxxiii.titanenchants.listeners.enchantmentManagement;

import com.nessxxiii.titanenchants.config.ConfigManager;
import com.nessxxiii.titanenchants.util.TitanEnchantEffects;
import com.playtheatria.jliii.generalutils.enums.ToolColor;
import com.playtheatria.jliii.generalutils.enums.ToolStatus;
import com.playtheatria.jliii.generalutils.items.CustomModelData;
import com.playtheatria.jliii.generalutils.items.PowerCrystalInfo;
import com.playtheatria.jliii.generalutils.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import net.kyori.adventure.text.Component;
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

    private final ConfigManager configManager;

    public ChargeManagement(ConfigManager configManager) {
        this.configManager = configManager;
    }

    @EventHandler
    public void applyCharge(InventoryClickEvent event){
        if (event.getCurrentItem() == null) return;
        Player player = (Player) event.getWhoClicked();
        if (isValidated(player.getItemOnCursor(), event.getCurrentItem())) {
            ItemStack itemOnCursor = player.getItemOnCursor();
            try {
                int chargeAmount = getChargeAmount(itemOnCursor, itemOnCursor.getAmount());
                player.getItemOnCursor().setAmount(0);
                addChargeLore(player, event.getCurrentItem(), chargeAmount);
                event.setCancelled(true);
            } catch (NumberFormatException exception) {
                player.sendMessage("There was an issue finding the charge amount.");
            }

        }
    }

    private int getChargeAmount(ItemStack itemOnCursor, int amount) {

        return switch (PowerCrystalInfo.getPowerCrystalType(itemOnCursor)) {
            case COMMON -> 5 * amount;
            case UNCOMMON -> 50 * amount;
            case SUPER -> 100 * amount;
            case EPIC -> 250 * amount;
            case ULTRA -> 1000 * amount;
            default -> 0;
        };
    }

    private boolean isValidated(ItemStack itemOnCursor, ItemStack itemClicked) {
        //item on cursor must be powercrystal
        if (itemOnCursor.getItemMeta() == null) return false;
        if (!PowerCrystalInfo.isPowerCrystal(itemOnCursor)) return false;
        //item clicked is the titan tool
        if (itemClicked == null || itemClicked.getType() == Material.AIR) return false;
        if (PowerCrystalInfo.isPowerCrystal(itemClicked)) return false;

        Response<List<String>> loreListResponse = TitanItem.getLore(itemClicked);
        if (loreListResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            return false;
        }

        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            if (configManager.getDebug()) {
                Bukkit.getConsoleSender().sendMessage("Failed to add charge to a non Titan Tool.");
            }
            return false;
        }

        boolean isChargedTitanTool = TitanItem.isChargedTitanTool(loreListResponse.value(), isTitanTool);

        if (!TitanItem.isAllowedType(itemClicked, TitanItem.ALLOWED_TITAN_TYPES)) return false;
        return isChargedTitanTool;
    }

    public static void addChargeLore(Player player, ItemStack item, Integer amount){
        Response<List<String>> loreListResponse = TitanItem.getLore(item);
        if (loreListResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
            return;
        }
        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            Bukkit.getConsoleSender().sendMessage("Failed to add charge to a non Titan Tool.");
            return;
        }

        boolean hasChargeLore = TitanItem.hasChargeLore(loreListResponse.value(), isTitanTool);

        Response<Integer> indexResponse = TitanItem.getTitanLoreIndex(loreListResponse.value(), TitanItem.CHARGE_PREFIX, isTitanTool);
        if (indexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(indexResponse.error());
            return;
        }

        int finalCharge;
        if (hasChargeLore) {
            Response<Integer> previousChargeResponse = TitanItem.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);
            if (previousChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(previousChargeResponse.error());
                return;
            }
            finalCharge = previousChargeResponse.value() + (amount);
        } else {
            finalCharge = amount;
        }
        Response<ToolColor> colorResponse = TitanItem.getColor(loreListResponse.value());
        if (colorResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(colorResponse.error());
            return;
        }
        List<String> updatedLoreList = loreListResponse.value();
        updatedLoreList.set(indexResponse.value(), TitanItem.generateChargeLore(colorResponse.value(), finalCharge));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
        item.setItemMeta(meta);
        TitanItem.setLore(item, updatedLoreList);
        TitanEnchantEffects.addChargeEffect(player);
    }

    public static void decreaseChargeLore(ItemStack item, List<String> loreList, boolean isTitanTool, boolean hasChargeLore, Player player){
        Response<Integer> previousChargeResponse = TitanItem.getCharge(loreList, isTitanTool, hasChargeLore, 39);
        if (previousChargeResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(previousChargeResponse.error());
            return;
        }
        Response<Integer> chargeLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.CHARGE_PREFIX, isTitanTool);
        if (chargeLoreIndexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(chargeLoreIndexResponse.error());
            return;
        }
        Response<Integer> statusLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(statusLoreIndexResponse.error());
            return;
        }
        Response<ToolColor> toolColorResponse = TitanItem.getColor(loreList);
        if (toolColorResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(toolColorResponse.error());
            return;
        }
        int remainingCharge = previousChargeResponse.value() - 1;
        ItemMeta meta = item.getItemMeta();
        if (remainingCharge <= 0) {
            loreList.set(chargeLoreIndexResponse.value(), TitanItem.generateChargeLore(toolColorResponse.value(), 0));
            loreList.set(statusLoreIndexResponse.value(), TitanItem.generateStatusLore(toolColorResponse.value(), ToolStatus.OFF));
            meta.setCustomModelData(CustomModelData.UNCHARGED_TITAN_TOOL);
            depletedChargeEffect(player);
            player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CAncient Power:" + " " + ChatColor.YELLOW + "Depleted"));
        } else {
            loreList.set(chargeLoreIndexResponse.value(), TitanItem.generateChargeLore(toolColorResponse.value(), remainingCharge));
            player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CAncient Power" + " "
                    + ChatColor.ITALIC + "§x§F§F§0§0§4§CCharge: " + ChatColor.YELLOW + remainingCharge));
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }

}
