package com.nessxxiii.titanenchants.listeners.enchantmentManagement;

import com.nessxxiii.titanenchants.enums.ToolColor;
import com.nessxxiii.titanenchants.enums.ToolStatus;
import com.nessxxiii.titanenchants.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static com.nessxxiii.titanenchants.util.TitanEnchantEffects.enableEffect;

public class ToggleAncientPower implements Listener {

    @EventHandler
    public void activateClick(PlayerInteractEvent event) {
        if (!event.getAction().isRightClick()) return;

        if (processPlayerStateValidation(event.getPlayer())) {
            Player player = event.getPlayer();
            ItemStack itemInMainHand = player.getInventory().getItemInMainHand();
            Material coolDown = Material.JIGSAW;

            Response<List<String>> loreListResponse = TitanItem.getLore(itemInMainHand);
            if (loreListResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(loreListResponse.error());
                return;
            }

            boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
            if (!isTitanTool) return;

            Response<ToolStatus> toolStatusResponse = TitanItem.getStatus(loreListResponse.value(), isTitanTool);
            if (toolStatusResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
                return;
            }
            Response<ToolColor> toolColorResponse = TitanItem.getColor(loreListResponse.value());
            if (toolColorResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(toolColorResponse.error());
                return;
            }

            boolean hasChargeLore = TitanItem.hasChargeLore(loreListResponse.value(), isTitanTool);
            boolean isImbuedTitanTool = TitanItem.isImbuedTitanTool(loreListResponse.value(), isTitanTool);

            player.setCooldown(coolDown,25);
            event.setCancelled(true);

            if (isImbuedTitanTool) {
                Response<String> toggleImbuedTitanTool = toggleImbuedTitanTool(itemInMainHand, loreListResponse.value(), isTitanTool, toolColorResponse.value(), toolStatusResponse.value());
                if (toggleImbuedTitanTool.error() != null) {
                    Bukkit.getConsoleSender().sendMessage(toggleImbuedTitanTool.error());
                    return;
                }

                player.sendActionBar(toggleImbuedTitanTool.value());
                enableEffect(player);
                return;
            }

            Response<Integer> getChargeResponse = TitanItem.getCharge(loreListResponse.value(), isTitanTool, hasChargeLore, 39);

            if (getChargeResponse.error() != null) {
                Bukkit.getConsoleSender().sendMessage(getChargeResponse.error());
                return;
            }


            if (getChargeResponse.value() <= 0 && !isImbuedTitanTool) return;

            if (hasChargeLore) {
                Response<String> toggleChargedTitanTool = toggleChargedTitanTool(itemInMainHand, loreListResponse.value(), isTitanTool, toolColorResponse.value(), toolStatusResponse.value(), getChargeResponse.value());
                if (toggleChargedTitanTool.error() != null) {
                    Bukkit.getConsoleSender().sendMessage(toggleChargedTitanTool.error());
                    return;
                }

                player.sendActionBar(toggleChargedTitanTool.value());
                enableEffect(player);
                return;
            }
            Bukkit.getConsoleSender().sendMessage("An error has occurred, this block should not be reachable.");
        }
    }

    public static Response<String> toggleImbuedTitanTool(ItemStack item, List<String> loreList, boolean isTitanTool, ToolColor color, ToolStatus status) {
        String statusLore = TitanItem.generateStatusLore(color, status == ToolStatus.ON ? ToolStatus.OFF : ToolStatus.ON);
        Response<Integer> statusLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(statusLoreIndexResponse.error());
            return Response.failure(statusLoreIndexResponse.error());
        }

        loreList.set(statusLoreIndexResponse.value(), statusLore);
        TitanItem.setLore(item, loreList);
        return Response.success(ChatColor.YELLOW + (status == ToolStatus.ON ? "Ancient Power:" + ChatColor.RED + " OFF" : "Ancient Power: " + ChatColor.GREEN + "ON"));
    }

    public static Response<String> toggleChargedTitanTool(ItemStack item, List<String> loreList, boolean isTitanTool, ToolColor color, ToolStatus status, int charge) {
        String statusLore = TitanItem.generateStatusLore(color, status == ToolStatus.ON ? ToolStatus.OFF : ToolStatus.ON);
        Response<Integer> statusLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.STATUS_PREFIX, isTitanTool);
        if (statusLoreIndexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(statusLoreIndexResponse.error());
            return Response.failure(statusLoreIndexResponse.error());
        }
        String chargeLore = TitanItem.generateChargeLore(color, charge);

        Response<Integer> chargeLoreIndexResponse = TitanItem.getTitanLoreIndex(loreList, TitanItem.CHARGE_PREFIX, isTitanTool);
        if (chargeLoreIndexResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(chargeLoreIndexResponse.error());
            return Response.failure(chargeLoreIndexResponse.error());
        }
        loreList.set(chargeLoreIndexResponse.value(), chargeLore);
        loreList.set(statusLoreIndexResponse.value(), statusLore);

        TitanItem.setLore(item, loreList);
        return Response.success(ChatColor.YELLOW + (status == ToolStatus.ON ? "Ancient Power:" + ChatColor.RED + " OFF" : "Ancient Power: " + ChatColor.GREEN + "ON"));

    }

    private static boolean processPlayerStateValidation(Player player) {
        if (!player.isSneaking()) return false;
        Material coolDown = Material.JIGSAW;
        if (player.hasCooldown(coolDown)) return false;
        return player.hasPermission("titan.enchants.toggle");
    }

}
