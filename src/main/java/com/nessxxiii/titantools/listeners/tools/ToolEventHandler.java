package com.nessxxiii.titantools.listeners.tools;

import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.events.PowerCrystalDropEvent;
import com.nessxxiii.titantools.events.tools.AxeBlockBreakEvent;
import com.nessxxiii.titantools.events.tools.PickBlockBreakEvent;
import com.nessxxiii.titantools.events.tools.ShovelBlockBreakEvent;
import com.nessxxiii.titantools.items.ItemCreator;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.util.Response;
import com.nessxxiii.titantools.util.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ToolEventHandler implements Listener {

    @EventHandler
    public static void onBlockBreak(BlockBreakEvent event){
        // validation
        if (event.getBlock().getType() != Material.AMETHYST_BLOCK) return;
        if (!isValidExcavator(event.getPlayer().getInventory().getItemInMainHand())) return;
        if (!event.getPlayer().hasPermission("titan.enchants.powercrystaldrop")) return;
        // event
        event.setCancelled(true);
        Bukkit.getPluginManager().callEvent(new PowerCrystalDropEvent(event.getPlayer(), event.getBlock().getLocation()));
    }

    @EventHandler
    public static void onBlockBreakDetectTool(BlockBreakEvent event) {
        // this section will determine the item a player is holding and then fire the appropriate event
        if (event.isCancelled()) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.DIAMOND_SHOVEL || itemInMainHand.getType() == Material.NETHERITE_SHOVEL) return;
        if (isValidTitanTool(event.getPlayer())) return;
        switch (itemInMainHand.getType()) {
            case DIAMOND_PICKAXE, NETHERITE_PICKAXE -> {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new PickBlockBreakEvent(event.getPlayer()));
            }
            case DIAMOND_AXE, NETHERITE_AXE -> {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new AxeBlockBreakEvent(event.getPlayer()));
            }
        }
    }

    @EventHandler
    public static void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction().isRightClick()) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (itemInMainHand.getType() != Material.DIAMOND_SHOVEL && itemInMainHand.getType() != Material.NETHERITE_SHOVEL) return;
        if (!isValidTitanTool(event.getPlayer())) return;
        switch (itemInMainHand.getType()) {
            case DIAMOND_SHOVEL, NETHERITE_SHOVEL -> {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new ShovelBlockBreakEvent(event.getPlayer(), event.getClickedBlock(), event.getBlockFace()));
            }
        }
    }

    private static boolean isValidTitanTool(Player player) {
        Response<List<String>> loreListResponse = ItemInfo.getLore(player.getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            Utils.sendPluginMessage(player, loreListResponse.error());
            return false;
        }
        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            Utils.sendPluginMessage(player, "This is not a titan tool.");
            return false;
        }
        if (ItemInfo.getStatus(loreListResponse.value(), isTitanTool).value() == ToolStatus.OFF) {
            Utils.sendPluginMessage(player, "This item is not active!");
            return false;
        }
        return true;
    }

    private static boolean isValidExcavator(ItemStack itemStack) {
        if (itemStack == null || itemStack.equals(Material.AIR)) return false;
        if (itemStack.getLore() == null || !itemStack.getItemMeta().getLore().equals(ItemCreator.excavator.getItemMeta().getLore())
                || !itemStack.getEnchantments().equals(ItemCreator.excavator.getEnchantments())) {
            return false;
        }
        return true;
    }
}
