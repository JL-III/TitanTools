package com.nessxxiii.titantools.generalutils;

import com.nessxxiii.titantools.enums.ToolStatus;
import com.nessxxiii.titantools.itemmanagement.ItemCreator;
import com.nessxxiii.titantools.itemmanagement.ItemInfo;
import com.nessxxiii.titantools.listeners.tools.titan.ChargeManagement;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.nessxxiii.titantools.generalutils.ConfigManager.blockConversionQuantity;
import static com.nessxxiii.titantools.generalutils.ConfigManager.blockConversionTypes;

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

    public static void sendPluginMessage(CommandSender sender, String message) {
        sender.sendMessage(PLUGIN_PREFIX + message);
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
        return switch (material) {
            case COAL_ORE, DEEPSLATE_COAL_ORE -> getRandomNumber(0, 2);
            case DIAMOND_ORE, DEEPSLATE_DIAMOND_ORE, EMERALD_ORE, DEEPSLATE_EMERALD_ORE -> getRandomNumber(3, 7);
            case LAPIS_ORE, DEEPSLATE_LAPIS_ORE, NETHER_QUARTZ_ORE -> getRandomNumber(2, 5);
            case REDSTONE_ORE, DEEPSLATE_REDSTONE_ORE -> getRandomNumber(1, 5);
            case IRON_ORE, DEEPSLATE_IRON_ORE, GOLD_ORE, DEEPSLATE_GOLD_ORE, NETHER_GOLD_ORE, COPPER_ORE, DEEPSLATE_COPPER_ORE -> 6;
            default -> 0;
        };
    }

    public static int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
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

    public static boolean shouldBreakClickedBlockNaturally(Block clickedBlock) {
        return clickedBlock.getType() == Material.CHEST || clickedBlock.getType() == Material.SHULKER_BOX || clickedBlock.getType() == Material.BARREL;
    }

    public static boolean canBreakBlock(Location blockLocation) {
        return ((blockLocation.getY() > -64 && !blockLocation.getWorld().getEnvironment().equals(World.Environment.NETHER))
                || ((blockLocation.getY() > 0 && blockLocation.getY() < 127) && blockLocation.getWorld().getEnvironment().equals(World.Environment.NETHER)));
    }

    public static List<Block> getNearbyBlocks(Location location, BlockFace blockFace) {
        List<Block> blocks = new ArrayList<>();

        if (blockFace.getModY() != 0){
            int y = location.getBlockY();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModX() !=0){
            int x = location.getBlockX();
            for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                for (int z = location.getBlockZ() - 1; z <= location.getBlockZ() + 1; z++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        } else if (blockFace.getModZ() !=0){
            int z = location.getBlockZ();
            for (int x = location.getBlockX() - 1; x <= location.getBlockX() + 1; x++) {
                for (int y = location.getBlockY() - 1; y <= location.getBlockY() + 1; y++) {
                    blocks.add(location.getWorld().getBlockAt(x, y, z));
                }
            }
        }
        return blocks;
    }

    public static ItemStack getPowerCrystalType(int randomNumber) {
        ItemStack powerCrystal = ItemCreator.powerCrystalCommon;
        if (randomNumber > 995) {
            powerCrystal = ItemCreator.powerCrystalUltra;
        } else if (randomNumber > 990) {
            powerCrystal = ItemCreator.powerCrystalEpic;
        } else if (randomNumber > 985) {
            powerCrystal = ItemCreator.powerCrystalSuper;
        } else if (randomNumber > 900) {
            powerCrystal = ItemCreator.powerCrystalUncommon;
        }
        return powerCrystal;
    }

    public static boolean isValidTitanTool(Player player) {
        Response<List<String>> loreListResponse = ItemInfo.getLore(player.getInventory().getItemInMainHand());
        if (loreListResponse.error() != null) {
            return false;
        }
        boolean isTitanTool = ItemInfo.isTitanTool(loreListResponse.value());
        if (!isTitanTool) {
            return false;
        }
        if (ItemInfo.getStatus(loreListResponse.value(), true).value() == ToolStatus.OFF) {
            return false;
        }
        return true;
    }

    public static boolean isValidExcavator(ItemStack itemStack) {
        if (itemStack == null || itemStack.equals(Material.AIR)) return false;
        if (itemStack.getLore() == null || !itemStack.getItemMeta().getLore().equals(ItemCreator.excavator.getItemMeta().getLore())
                || !itemStack.getEnchantments().equals(ItemCreator.excavator.getEnchantments())) {
            return false;
        }
        return true;
    }
}
