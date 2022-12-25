package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.items.ItemCreator;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class KitCommands implements CommandExecutor {

    private Logger logger;
    private final String COMMON = "common";
    private final String UNCOMMON = "uncommon";
    private final String SUPER = "super";
    private final String EPIC = "epic";
    private final String ULTRA = "ultra";
    private final String EXCAVATOR = "excavator";
    private final String SUN_FISH = "sunfish";
    private final String NIGHT_FISH = "nightfish";
    private final String ETHEREAL_FRAGMENT = "etherealfragement";
    private final String CHRISTMAS_PICK = "christmaspick";
    private final String TITAN_PICK_RED_FORTUNE = "titanpickredfortune";
    private final String TITAN_PICK_RED_SILK = "titanpickredsilk";
    private final String TITAN_PICK_YELLOW_FORTUNE = "titanpickyellowfortune";
    private final String TITAN_PICK_YELLOW_SILK = "titanpickyellowsilk";
    private final String TITAN_PICK_BLUE_FORTUNE = "titanpickbluefortune";
    private final String TITAN_PICK_BLUE_SILK = "titanpickbluesilk";
    private final String TITAN_SHOVEL_RED = "titanshovelred";
    private String PLAYER_NAME;

    public KitCommands(Logger logger) {
        this.logger = logger;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender.hasPermission("titan.kit.give")) {
            if ("kit".equalsIgnoreCase(args[0]) && args.length == 3) {
                if (Bukkit.getPlayer(args[2]) != null) {
                    Player player = Bukkit.getPlayer(args[2]);
                    if (player == null) return false;
                    PLAYER_NAME = player.getName();
                    Inventory inventory = player.getInventory();
                    switch (args[1]) {
                        case COMMON -> reportResult(COMMON, inventory.addItem(ItemCreator.powerCrystalCommon));
                        case UNCOMMON -> reportResult(UNCOMMON, inventory.addItem(ItemCreator.powerCrystalUncommon));
                        case SUPER -> reportResult(SUPER, inventory.addItem(ItemCreator.powerCrystalSuper));
                        case EPIC -> reportResult(EPIC, inventory.addItem(ItemCreator.powerCrystalEpic));
                        case ULTRA -> reportResult(ULTRA, inventory.addItem(ItemCreator.powerCrystalUltra));
                        case EXCAVATOR -> reportResult(EXCAVATOR, inventory.addItem(ItemCreator.excavator));
                        case SUN_FISH -> reportResult(SUN_FISH, inventory.addItem(ItemCreator.sunFish));
                        case NIGHT_FISH -> reportResult(NIGHT_FISH, inventory.addItem(ItemCreator.nightFish));
                        case ETHEREAL_FRAGMENT -> reportResult(ETHEREAL_FRAGMENT, inventory.addItem(ItemCreator.etherealFragment));
                        case CHRISTMAS_PICK -> reportResult(CHRISTMAS_PICK, inventory.addItem(ItemCreator.christmasPick));
                        case TITAN_PICK_RED_FORTUNE -> reportResult(TITAN_PICK_RED_FORTUNE, inventory.addItem(ItemCreator.titanPickRedFortune));
                        case TITAN_PICK_RED_SILK -> reportResult(TITAN_PICK_RED_SILK, inventory.addItem(ItemCreator.titanPickRedSilk));
                        case TITAN_PICK_YELLOW_FORTUNE -> reportResult(TITAN_PICK_YELLOW_FORTUNE, inventory.addItem(ItemCreator.titanPickYellowFortune));
                        case TITAN_PICK_YELLOW_SILK -> reportResult(TITAN_PICK_YELLOW_SILK, inventory.addItem(ItemCreator.titanPickYellowSilk));
                        case TITAN_PICK_BLUE_FORTUNE -> reportResult(TITAN_PICK_BLUE_FORTUNE,  inventory.addItem(ItemCreator.titanPickBlueFortune));
                        case TITAN_PICK_BLUE_SILK -> reportResult(TITAN_PICK_BLUE_SILK,  inventory.addItem(ItemCreator.titanPickBlueSilk));
                        case TITAN_SHOVEL_RED -> reportResult(TITAN_SHOVEL_RED, inventory.addItem(ItemCreator.titanShovelRed));
                        default -> sender.sendMessage(ChatColor.DARK_RED + "This kit does not exist");
                    }
                }
            }
        }
        return false;
    }

    private void reportResult(String item, HashMap<Integer, ItemStack> droppedItems) {
        if (droppedItems.isEmpty()) {
            logger.info(PLAYER_NAME + " received their " + item);
        } else {
            logger.warning(PLAYER_NAME + " did not receive their " + item + " due to a full inventory.");
        }
    }

}
