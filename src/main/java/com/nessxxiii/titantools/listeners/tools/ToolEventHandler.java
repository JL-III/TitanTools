package com.nessxxiii.titantools.listeners.tools;

import com.gmail.nossr50.skills.fishing.FishingManager;
import com.gmail.nossr50.util.player.UserManager;
import com.nessxxiii.titantools.TitanTools;
import com.nessxxiii.titantools.events.tools.excavator.ExcavatorBlockBreakEvent;
import com.nessxxiii.titantools.events.tools.titan.enchants.*;
import com.nessxxiii.titantools.events.utils.ExploitFishingEvent;
import com.nessxxiii.titantools.generalutils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class ToolEventHandler implements Listener {

    private final TitanTools plugin;

    public ToolEventHandler(TitanTools plugin) {
        this.plugin = plugin;
    }

    // Used for the Excavator
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        // validation
        if (event.getBlock().getType() != Material.AMETHYST_BLOCK) return;
        if (!Utils.isValidExcavator(event.getPlayer().getInventory().getItemInMainHand())) return;
        if (!event.getPlayer().hasPermission("titan.enchants.powercrystaldrop")) return;
        // event
        event.setCancelled(true);
        Bukkit.getPluginManager().callEvent(new ExcavatorBlockBreakEvent(event.getPlayer(), event.getBlock().getLocation()));
    }

    // Used for the Titan Pickaxe, Titan Axe and Titan Hoe
    @EventHandler
    public void onBlockBreakDetectTool(BlockBreakEvent event) {
        if (event.isCancelled()) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (itemInMainHand.getType() == Material.DIAMOND_SHOVEL || itemInMainHand.getType() == Material.NETHERITE_SHOVEL) return;
        if (!Utils.isValidTitanTool(event.getPlayer())) return;
        switch (itemInMainHand.getType()) {
            case DIAMOND_PICKAXE, NETHERITE_PICKAXE -> {
                Bukkit.getPluginManager().callEvent(new PickBlockBreakEvent(event.getPlayer(), event.getBlock()));
            }
            case DIAMOND_AXE, NETHERITE_AXE -> {
                Bukkit.getPluginManager().callEvent(new AxeBlockBreakEvent(event.getPlayer(), event.getBlock()));
            }
            case DIAMOND_HOE, NETHERITE_HOE -> {
                Bukkit.getPluginManager().callEvent(new HoeBlockBreakEvent(event.getPlayer(), event.getBlock()));
            }
        }
    }

    // Used for the Titan Shovel
    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.isCancelled()) return;
        if (event.getAction().isRightClick()) return;
        if (event.getClickedBlock() == null) return;
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (itemInMainHand.getType() != Material.DIAMOND_SHOVEL && itemInMainHand.getType() != Material.NETHERITE_SHOVEL) return;
        if (!Utils.isValidTitanTool(event.getPlayer())) return;
        switch (itemInMainHand.getType()) {
            case DIAMOND_SHOVEL, NETHERITE_SHOVEL -> {
                event.setCancelled(true);
                Bukkit.getPluginManager().callEvent(new ShovelBlockBreakEvent(event.getPlayer(), event.getClickedBlock(), event.getBlockFace()));
            }
        }
    }

    // Used for the Titan Rod
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerFishEvent(PlayerFishEvent event) {
        if (event.isCancelled()) return;
        if (event.getCaught() == null) return;
        Player player = event.getPlayer();
        //Profile not loaded
        if(UserManager.getPlayer(player) == null) {
            return;
        }
        if (UserManager.getPlayer(event.getPlayer()) == null) {
            Bukkit.getConsoleSender().sendMessage("Player is null.");
            return;
        }
        FishingManager fishingManager = UserManager.getPlayer(player).getFishingManager();
        if (fishingManager == null) {
            Bukkit.getConsoleSender().sendMessage("Fishing manager is null.");
            return;
        }
        if (fishingManager.isExploitingFishing(event.getHook().getLocation().toVector())) {
            Bukkit.getPluginManager().callEvent(new ExploitFishingEvent(event.getPlayer(), event.getHook().getLocation()));
            return;
        }
        if (!Utils.isValidTitanTool(event.getPlayer())) return;
        if (event.getState() == PlayerFishEvent.State.CAUGHT_FISH) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getPluginManager().callEvent(new RodCatchFishEvent(event.getPlayer(), (Item) event.getCaught(), event.getCaught().getVelocity()));
                    event.getCaught().remove();
                }
            }.runTaskLater(plugin, 1);
        }
    }

    // Used for the Titan Sword and Titan Axe
    @EventHandler
    public void onPlayerKillEntityEvent(EntityDeathEvent event) {
        if (event.isCancelled()) return;
        if (event.getEntity().getKiller() == null) return;
        if (!Utils.isValidTitanTool(event.getEntity().getKiller())) return;
        Material itemMaterial = event.getEntity().getKiller().getInventory().getItemInMainHand().getType();
        if (itemMaterial != Material.DIAMOND_SWORD && itemMaterial != Material.NETHERITE_SWORD && itemMaterial != Material.DIAMOND_AXE && itemMaterial != Material.NETHERITE_AXE) return;
        Bukkit.getPluginManager().callEvent(new TitanToolEntityDeathEvent(event.getEntity().getKiller(), event.getDroppedExp()));
    }
}
