package com.nessxxiii.titantools.util;

import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.items.ItemInfo;
import com.nessxxiii.titantools.listeners.enchantmentManagement.ChargeManagement;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nessxxiii.titantools.config.ConfigManager.blockConversionQuantity;
import static com.nessxxiii.titantools.config.ConfigManager.blockConversionTypes;

public class Utils {

    private static final ChatColor BRACKET_COLOR = ChatColor.DARK_PURPLE;

    private static final ChatColor PLUGIN_COLOR = ChatColor.LIGHT_PURPLE;

    private static final String PLUGIN_PREFIX = BRACKET_COLOR + "[" + PLUGIN_COLOR + "TitanTools" + BRACKET_COLOR + "] " + ChatColor.RESET;

    public static final String PERMISSION_PREFIX_ADMIN = "titan.enchants.admincommands";

    public static final String NO_PERMISSION = ChatColor.RED + "No Permission.";

    public static void printBanner(CommandSender sender) {
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "################################################################################");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "#####   ###   #####    #    #   #        #####   ###    ###   #       ###   " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #      #      #     # #   ##  #          #    #   #  #   #  #      #      " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #      #      #    #####  # # #          #    #   #  #   #  #       ###   " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #      #      #    #   #  #  ##          #    #   #  #   #  #          #  " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.GOLD + "  #     ###     #    #   #  #   #          #     ###    ###   #####   ###   " + ChatColor.LIGHT_PURPLE + " #");
        sendPluginMessage(sender, ChatColor.LIGHT_PURPLE + "# " + ChatColor.LIGHT_PURPLE + "##############################################################################");
        sendPluginMessage(sender, ChatColor.GOLD + "By: NessXXIII");
    }

    public static void sendPluginMessage(CommandSender sender, String response) {
        sender.sendMessage(PLUGIN_PREFIX + response);
    }

    public static Response<List<String>> titanToolBlockBreakValidation(BlockBreakEvent event, List<Material> ALLOWED_TYPES) {
        if (event.isCancelled()) return Response.failure("Event was cancelled.");
        if (!ItemInfo.isAllowedType(event.getPlayer().getInventory().getItemInMainHand(), ALLOWED_TYPES)) return Response.failure("This item is not in the allowed types.");
        Response<List<String>> loreListResponse = ItemInfo.getLore(event.getPlayer().getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            return Response.failure(loreListResponse.error());
        }
        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        if (!isTitanTool) return Response.failure("This is not a Titan Tool.");
        Response<ToolStatus> toolStatusResponse = ItemInfo.getStatus(loreListResponse.value(), isTitanTool);
        if (toolStatusResponse.error() != null) {
            Bukkit.getConsoleSender().sendMessage(toolStatusResponse.error());
            return Response.failure(toolStatusResponse.error());
        }
        if (toolStatusResponse.value() == ToolStatus.OFF) return Response.failure("Tool Status is OFF");
        return loreListResponse;
    }

    public static Response<List<String>> titanShovelValidation(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        ItemStack itemInMainHand = event.getPlayer().getInventory().getItemInMainHand();
        if (event.getClickedBlock() == null) return Response.failure("The clicked block was null");
        if (!event.getAction().isLeftClick()) return Response.failure("Event was not a left click action.");
        if (!canBreakBedrock(clickedBlock, player)) return Response.failure("Failed can break bedrock check.");
        if (!ItemInfo.isAllowedType(itemInMainHand, ItemInfo.ALLOWED_SHOVEL_TYPES)) return Response.failure("This is not an allowed Titan Shovel Type.");

        Response<List<String>> getLoreResponse = ItemInfo.getLore(itemInMainHand);
        if (getLoreResponse.error() != null) {
            return Response.failure(getLoreResponse.error());
        }
        Response<ToolStatus> toolStatusResponse = ItemInfo.getStatus(getLoreResponse.value(), true);
        if (toolStatusResponse.error() != null) {
            return Response.failure(toolStatusResponse.error());
        }
        if (toolStatusResponse.value() == ToolStatus.OFF) return Response.failure("Tool Status: " + toolStatusResponse.value());
        BlockBreakEvent e = new BlockBreakEvent(clickedBlock, event.getPlayer());
        Bukkit.getPluginManager().callEvent(e);
        if (e.isCancelled()) return Response.failure("Called block break event was cancelled.");
        e.setCancelled(true);
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

    public static void getNewBlocksFromSmeltAndUpdateInventory(Player player, Material material) {
        player.sendMessage("Smelting " + material.toString());
        if (blockConversionTypes.containsKey(material)) {
            player.getInventory().addItem(getDropsFromConversionTable(material));
        }
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

    public static final List<Material> REPLANT_MATERIAL_LIST = new ArrayList<>() {{
        add(Material.DIRT);
        add(Material.PODZOL);
        add(Material.GRASS_BLOCK);
    }};

    public static final HashMap<Material, Material> REPLANT_MAP = new HashMap<>() {{
        put(Material.OAK_LOG, Material.OAK_SAPLING);
        put(Material.SPRUCE_LOG, Material.SPRUCE_SAPLING);
        put(Material.BIRCH_LOG, Material.BIRCH_SAPLING);
        put(Material.JUNGLE_LOG, Material.JUNGLE_SAPLING);
        put(Material.ACACIA_LOG, Material.ACACIA_SAPLING);
        put(Material.DARK_OAK_LOG, Material.DARK_OAK_SAPLING);
        put(Material.CRIMSON_STEM, Material.CRIMSON_FUNGUS);
        put(Material.WARPED_STEM, Material.WARPED_FUNGUS);
        put(Material.CHERRY_LOG, Material.CHERRY_SAPLING);
        put(Material.MANGROVE_LOG, Material.MANGROVE_PROPAGULE);
    }};

    public static List<ItemStack> getDropsProcessed(Block currentBlock, Player player) {
        if (blockConversionTypes.containsKey(currentBlock.getType()) && !player.getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH)) {
            TitanEnchantEffects.playSmeltVisualAndSoundEffect(player, currentBlock.getLocation());
            return new ArrayList<>(){{
                add(new ItemStack(blockConversionTypes.get(currentBlock.getType()), blockConversionQuantity.get(currentBlock.getType())));
            }};
        } else {
            return currentBlock.getDrops(player.getInventory().getItemInMainHand()).stream().toList();
        }
    }

    public static boolean simulateBlockBreakEventIsCancelled(Block block, Player player) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(block, player);
        Bukkit.getPluginManager().callEvent(blockBreakEvent);
        boolean isCancelled = blockBreakEvent.isCancelled();
        blockBreakEvent.setCancelled(true);
        return isCancelled;
    }

    public static boolean simulateItemPickupIsCancelled(Item item, Player player) {
        EntityPickupItemEvent playerPickupItemEvent = new EntityPickupItemEvent(player, item, (Math.max(item.getItemStack().getAmount() - 1, 0)));
        Bukkit.getPluginManager().callEvent(playerPickupItemEvent);
        return playerPickupItemEvent.isCancelled();
    }

    public static void processChargeManagement(Player player, Debugger debugger, ItemStack itemInMainHand, List<String> lore) {
        boolean hasChargeLore = ItemInfo.hasChargeLore(lore, true);
        if (hasChargeLore) {
            Response<Integer> getChargeResponse = ItemInfo.getCharge(lore, true, true, 39);
            if (getChargeResponse.error() != null) {
                debugger.sendDebugIfEnabled(getChargeResponse.error());
                return;
            }
            ChargeManagement.decreaseChargeLore(debugger, itemInMainHand, lore, true, true, player);
        }
    }
}
