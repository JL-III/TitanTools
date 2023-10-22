package com.nessxxiii.titantools.listeners.enchantmentManagement;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.enums.PowerCrystalType;
import com.nessxxiii.titantools.enums.ToolColor;
import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.items.CustomModelData;
import com.nessxxiii.titantools.items.PowerCrystalInfo;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Debugger;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.TitanEnchantEffects;

import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

import static com.nessxxiii.titantools.util.TitanEnchantEffects.depletedChargeEffect;


public class ChargeManagement implements Listener {
    private final Debugger debugger;

    public ChargeManagement(Debugger debugger) {
        this.debugger = debugger;
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
                addChargeLore(debugger, player, event.getCurrentItem(), chargeAmount);
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

        Response<List<String>> loreListResponse = ItemInfo.getLore(itemClicked);
        if (loreListResponse.error() != null) {
            debugger.sendDebugIfEnabled(loreListResponse.error());
            return false;
        }

        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            debugger.sendDebugIfEnabled("Failed to add charge to a non Titan Tool.");
            return false;
        }

        boolean isChargedTitanTool = ItemInfo.isChargedTitanTool(loreListResponse.value(), isTitanTool);

        if (!ItemInfo.isAllowedType(itemClicked, ItemInfo.ALLOWED_TITAN_TYPES)) return false;
        return isChargedTitanTool;
    }

    public static void addChargeLore(Debugger debugger, Player player, ItemStack item, Integer amount){
        Response<List<String>> loreListResponse = ItemInfo.getLore(item);
        if (loreListResponse.error() != null) {
            debugger.sendDebugIfEnabled(loreListResponse.error());
            return;
        }
        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            debugger.sendDebugIfEnabled("Failed to add charge to a non Titan Tool.");
            return;
        }

        boolean hasChargeLore = ItemInfo.hasChargeLore(loreListResponse.value(), isTitanTool);

        Response<Integer> indexResponse = ItemInfo.getTitanLoreIndex(loreListResponse.value(), ItemInfo.CHARGE_PREFIX, isTitanTool);
        if (indexResponse.error() != null) {
            debugger.sendDebugIfEnabled(indexResponse.error());
            return;
        }

        int finalCharge;
        if (hasChargeLore) {
            Response<Integer> previousChargeResponse = ItemInfo.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);
            if (previousChargeResponse.error() != null) {
                debugger.sendDebugIfEnabled(previousChargeResponse.error());
                return;
            }
            finalCharge = previousChargeResponse.value() + (amount);
        } else {
            finalCharge = amount;
        }
        Response<ToolColor> colorResponse = ItemInfo.getColor(loreListResponse.value());
        if (colorResponse.error() != null) {
            debugger.sendDebugIfEnabled(colorResponse.error());
            return;
        }
        List<String> updatedLoreList = loreListResponse.value();
        updatedLoreList.set(indexResponse.value(), ItemInfo.generateChargeLore(colorResponse.value(), finalCharge));
        ItemMeta meta = item.getItemMeta();
        meta.setCustomModelData(CustomModelData.CHARGED_TITAN_TOOL);
        item.setItemMeta(meta);
        ItemInfo.setLore(item, updatedLoreList);
        TitanEnchantEffects.addChargeEffect(player);
    }

    public static void decreaseChargeLore(Debugger debugger, ItemStack item, List<String> loreList, boolean isTitanTool, boolean hasChargeLore, Player player){
        Response<Integer> previousChargeResponse = ItemInfo.getCharge(loreList, isTitanTool, hasChargeLore, 39);
        if (previousChargeResponse.error() != null) {
            debugger.sendDebugIfEnabled(previousChargeResponse.error());
            return;
        }
        Response<Integer> chargeLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreList, ItemInfo.CHARGE_PREFIX, isTitanTool);
        if (chargeLoreIndexResponse.error() != null) {
            debugger.sendDebugIfEnabled(chargeLoreIndexResponse.error());
            return;
        }
        Response<Integer> statusLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreList, ItemInfo.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            debugger.sendDebugIfEnabled(statusLoreIndexResponse.error());
            return;
        }
        Response<ToolColor> toolColorResponse = ItemInfo.getColor(loreList);
        if (toolColorResponse.error() != null) {
            debugger.sendDebugIfEnabled(toolColorResponse.error());
            return;
        }
        int remainingCharge = previousChargeResponse.value() - 1;
        ItemMeta meta = item.getItemMeta();
        if (remainingCharge <= 0) {
            loreList.set(chargeLoreIndexResponse.value(), ItemInfo.generateChargeLore(toolColorResponse.value(), 0));
            loreList.set(statusLoreIndexResponse.value(), ItemInfo.generateStatusLore(toolColorResponse.value(), ToolStatus.OFF));
            meta.setCustomModelData(CustomModelData.UNCHARGED_TITAN_TOOL);
            depletedChargeEffect(player);
            player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CAncient Power:" + " " + ChatColor.YELLOW + "Depleted"));
        } else {
            loreList.set(chargeLoreIndexResponse.value(), ItemInfo.generateChargeLore(toolColorResponse.value(), remainingCharge));
            player.sendActionBar(Component.text(ChatColor.ITALIC + "§x§F§F§0§0§4§CAncient Power" + " "
                    + ChatColor.ITALIC + "§x§F§F§0§0§4§CCharge: " + ChatColor.YELLOW + remainingCharge));
        }
        meta.setLore(loreList);
        item.setItemMeta(meta);

    }

}
