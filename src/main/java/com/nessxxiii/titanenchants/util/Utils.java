package com.nessxxiii.titanenchants.util;

import com.nessxxiii.titanenchants.enums.ToolStatus;
import com.nessxxiii.titanenchants.items.TitanItem;
import com.playtheatria.jliii.generalutils.utils.Response;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.nessxxiii.titanenchants.config.ConfigManager.blockConversionQuantity;
import static com.nessxxiii.titanenchants.config.ConfigManager.blockConversionTypes;

public class Utils {

    public static void printBanner() {
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "   _____   _____   _        ");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |    /_\\  |\\ |");
        Bukkit.getConsoleSender().sendMessage(ChatColor.GOLD + "     |   |   |   /   \\ | \\| enchants");
        Bukkit.getConsoleSender().sendMessage("");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "               NessXXIII");
        Bukkit.getConsoleSender().sendMessage(ChatColor.LIGHT_PURPLE + "<>------------------------------------<>");
    }

    public static Response<List<String>> titanToolBlockBreakValidation(BlockBreakEvent event, List<Material> ALLOWED_TYPES) {
        if (event.isCancelled()) return Response.failure("Event was cancelled.");
        if (!TitanItem.isAllowedType(event.getPlayer().getInventory().getItemInMainHand(), ALLOWED_TYPES)) return Response.failure("This item is not in the allowed types.");
        Response<List<String>> loreListResponse = TitanItem.getLore(event.getPlayer().getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            return Response.failure(loreListResponse.error());
        }
        boolean isTitanTool = TitanItem.isTitanTool(loreListResponse.value());
        if (!isTitanTool) return Response.failure("This is not a Titan Tool.");
        Response<ToolStatus> toolStatusResponse = TitanItem.getStatus(loreListResponse.value(), isTitanTool);
        if (toolStatusResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
            return Response.failure(toolStatusResponse.error());
        }
        if (toolStatusResponse.value() == ToolStatus.OFF) return Response.failure("Tool Status is OFF");
        return loreListResponse;
    }

    public static Response<List<String>> titanShovelValidation(PlayerInteractEvent event, Player player, ItemStack itemInMainHand, Block clickedBlock) {
        if (event.getClickedBlock() == null) return Response.failure("The clicked block was null");
        if (!event.getAction().isLeftClick()) return Response.failure("Event was not a left click action.");
        if (!canBreakBedrock(clickedBlock, player)) return Response.failure("Failed can break bedrock check.");
        if (!TitanItem.isAllowedType(itemInMainHand, TitanItem.ALLOWED_SHOVEL_TYPES)) return Response.failure("This is not an allowed Titan Shovel Type.");
        BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
        Bukkit.getPluginManager().callEvent(e);
        if (e.isCancelled()) return Response.failure("Called block break event was cancelled.");
        e.setCancelled(true);

        Response<List<String>> getLoreResponse = TitanItem.getLore(itemInMainHand);
        if (getLoreResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(getLoreResponse.error());
            return Response.failure(getLoreResponse.error());
        }
        Response<ToolStatus> toolStatusResponse = TitanItem.getStatus(getLoreResponse.value(), true);
        if (toolStatusResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
            return Response.failure(toolStatusResponse.error());
        }
        if (toolStatusResponse.value() == ToolStatus.OFF) return Response.failure("Tool Status: " + toolStatusResponse.value());
        return Response.success(getLoreResponse.value());
    }

    public static boolean canBreakBedrock(Block clickedBlock, Player player) {
        if (clickedBlock.getType() != Material.BEDROCK) return true;
        if (clickedBlock.getLocation().getY() >= -63 && !player.getWorld().getEnvironment().equals(World.Environment.NETHER)) return true;
        return (player.getWorld().getEnvironment().equals(World.Environment.NETHER) && !(clickedBlock.getLocation().getY() < 1 || clickedBlock.getLocation().getY() > 126));
    }

    public static List<Block> getSphereBlocks(Location location, int radius, boolean hollow) {
        List<Block> circleBlocks = new ArrayList<>();
        int bx = location.getBlockX();
        int by = location.getBlockY();
        int bz = location.getBlockZ();
        for(int x = bx - radius; x <= bx + radius; x++) {
            for(int y = by - radius; y <= by + radius; y++) {
                for(int z = bz - radius; z <= bz + radius; z++) {
                    double distance = ((bx-x) * (bx-x) + ((bz-z) * (bz-z)) + ((by-y) * (by-y)));
                    if(distance < radius * radius && !(hollow && distance < ((radius - 1) * (radius - 1)))) {
                        circleBlocks.add(location.getWorld().getBlockAt(x, y, z));
                    }
                }
            }
        }
        return circleBlocks;
    }

    public static List<Block> getCubeBlocks(Location location) {
        List<Block> blocks = new ArrayList<>();
        for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static int calculateExperienceAmount(Material material) {
        int experience = switch (material) {
            case COAL_ORE, DEEPSLATE_COAL_ORE -> getRandomNumber(0, 2);
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> getRandomNumber(3, 7);
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE, NETHER_QUARTZ_ORE -> getRandomNumber(2, 5);
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> getRandomNumber(1, 5);
            case IRON_ORE, DEEPSLATE_IRON_ORE, GOLD_ORE, DEEPSLATE_GOLD_ORE, NETHER_GOLD_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE -> 6;
            default -> 0;
        };
        return experience;
    }

    public void updateInventoryWithAllDropsFromBlockbreak(Player player, ItemStack itemInMainHand, Block block) {
        Collection<ItemStack> dropsCollection = block.getDrops(itemInMainHand);
        for (ItemStack itemStack : dropsCollection) {
            updatePlayerInventory(player, itemStack);
        }
    }

    public void updatePlayerInventory(Player player, ItemStack drops) {
        player.getInventory().addItem(drops);
        player.updateInventory();
    }

    public void getNewBlocksFromSmeltAndUpdateInventory(Player player, Material material) {
        updatePlayerInventory(player, getDropsFromConversionTable(material));
    }

    @NotNull
    public static ItemStack getDropsFromConversionTable(Material material) {
        return new ItemStack(blockConversionTypes.get(material), blockConversionQuantity.get(material));
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private double getFortuneAmount(double fortuneLevel) {
        return 1/(fortuneLevel + 2) + (fortuneLevel + 1)/2;
    }


}
