package com.nessxxiii.titantools.listeners.tools.titan;

import com.nessxxiii.titantools.enums.ToolColor;
import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.nessxxiii.titantools.generalutils.Debugger;
import com.nessxxiii.titantools.generalutils.Response;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.nessxxiii.titantools.generalutils.TitanEnchantEffects.enableEffect;

public class ToggleAncientPower implements Listener {
    private final Debugger debugger;
    public ToggleAncientPower(Debugger debugger) {
        this.debugger = debugger;
    }

    @EventHandler
    public void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        if (processPlayerStateValidation(event.getPlayer())) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;

            Response<List<String>> loreListResponse = ItemInfo.getLore(itemInMainHand);
            if (loreListResponse.error() != null) {
                debugger.sendDebugIfEnabled(loreListResponse.error());
                return;
            }

            boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
            if (!isTitanTool) return;

            Response<ToolStatus> toolStatusResponse = ItemInfo.getStatus(loreListResponse.value(), isTitanTool);
            if (toolStatusResponse.error() != null) {
                debugger.sendDebugIfEnabled(toolStatusResponse.error());
                return;
            }
            Response<ToolColor> toolColorResponse = ItemInfo.getColor(loreListResponse.value());
            if (toolColorResponse.error() != null) {
                debugger.sendDebugIfEnabled(toolColorResponse.error());
                return;
            }

            boolean hasChargeLore = ItemInfo.hasChargeLore(loreListResponse.value(), isTitanTool);
            boolean isImbuedTitanTool = ItemInfo.isImbuedTitanTool(loreListResponse.value(), isTitanTool);

            player.setCooldown(coolDown,25);
            event.setCancelled(true);

            if (isImbuedTitanTool) {
                Response<String> toggleImbuedTitanTool = toggleImbuedTitanTool(debugger, itemInMainHand, loreListResponse.value(), isTitanTool, toolColorResponse.value(), toolStatusResponse.value());
                if (toggleImbuedTitanTool.error() != null) {
                    debugger.sendDebugIfEnabled(toggleImbuedTitanTool.error());
                    return;
                }

                player.sendActionBar(toggleImbuedTitanTool.value());
                enableEffect(player);
                return;
            }

            Response<Integer> getChargeResponse = ItemInfo.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);

            if (getChargeResponse.error() != null) {
                debugger.sendDebugIfEnabled(getChargeResponse.error());
                return;
            }


            if (getChargeResponse.value() <= 0 && !isImbuedTitanTool) return;

            if (hasChargeLore) {
                Response<String> toggleChargedTitanTool = toggleChargedTitanTool(debugger, itemInMainHand, loreListResponse.value(), isTitanTool, toolColorResponse.value(), toolStatusResponse.value(), getChargeResponse.value());
                if (toggleChargedTitanTool.error() != null) {
                    debugger.sendDebugIfEnabled(toggleChargedTitanTool.error());
                    return;
                }

                player.sendActionBar(toggleChargedTitanTool.value());
                enableEffect(player);
                return;
            }
            Bukkit.getConsoleSender().sendMessage("An error has occurred, this block should not be reachable.");
        }
    }

    public static Response<String> toggleImbuedTitanTool(Debugger debugger, ItemStack item, List<String> loreList, boolean isTitanTool, ToolColor color, ToolStatus status) {
        String statusLore = ItemInfo.generateStatusLore(color, status == ToolStatus.ON ? ToolStatus.OFF : ToolStatus.ON);
        Response<Integer> statusLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreList, ItemInfo.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            debugger.sendDebugIfEnabled(statusLoreIndexResponse.error());
            return Response.failure(statusLoreIndexResponse.error());
        }

        loreList.set(statusLoreIndexResponse.value(), statusLore);
        ItemInfo.setLore(item, loreList);
        return Response.success(ChatColor.YELLOW + (status == ToolStatus.ON ? "Ancient Power:" + ChatColor.RED + " OFF" : "Ancient Power: " + ChatColor.GREEN + "ON"));
    }

    public static Response<String> toggleChargedTitanTool(Debugger debugger, ItemStack item, List<String> loreList, boolean isTitanTool, ToolColor color, ToolStatus status, int charge) {
        String statusLore = ItemInfo.generateStatusLore(color, status == ToolStatus.ON ? ToolStatus.OFF : ToolStatus.ON);
        Response<Integer> statusLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreList, ItemInfo.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            debugger.sendDebugIfEnabled(statusLoreIndexResponse.error());
            return Response.failure(statusLoreIndexResponse.error());
        }
        String chargeLore = ItemInfo.generateChargeLore(color, charge);

        Response<Integer> chargeLoreIndexResponse = ItemInfo.getTitanLoreIndex(loreList, ItemInfo.CHARGE_PREFIX, isTitanTool);
        if (chargeLoreIndexResponse.error() != null) {
            debugger.sendDebugIfEnabled(chargeLoreIndexResponse.error());
            return Response.failure(chargeLoreIndexResponse.error());
        }
        loreList.set(chargeLoreIndexResponse.value(), chargeLore);
        loreList.set(statusLoreIndexResponse.value(), statusLore);

        ItemInfo.setLore(item, loreList);
        return Response.success(ChatColor.YELLOW + (status == ToolStatus.ON ? "Ancient Power:" + ChatColor.RED + " OFF" : "Ancient Power: " + ChatColor.GREEN + "ON"));

    }

    private static boolean processPlayerStateValidation(Player player) {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

}
