package com.nessxxiii.titanenchants.commands;

import com.nessxxiii.titanenchants.config.ConfigManager;
import com.nessxxiii.titanenchants.items.ItemCreator;
import com.nessxxiii.titanenchants.util.CustomLogger;
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

public class KitCommands implements CommandExecutor {

    private final CustomLogger logger;
    private final ConfigManager configManager;
    private final String COMMON = "common";
    private final String UNCOMMON = "uncommon";
    private final String SUPER = "super";
    private final String EPIC = "epic";
    private final String ULTRA = "ultra";
    private final String EXCAVATOR = "excavator";
    private final String SUN_FISH = "sunfish";
    private final String NIGHT_FISH = "nightfish";
    private final String ETHEREAL_FRAGMENT = "etherealfragment";
    private final String CHRISTMAS_PICK = "christmaspick";
    private final String GINGERBREAD_MAN = "gingerbreadman";


    //TODO this needs to be implemented.
    private final String CASINO_KEY = "casinokey";
    private String PLAYER_NAME;

    public KitCommands(ConfigManager configManager, CustomLogger logger) {
        this.configManager = configManager;
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
                        case GINGERBREAD_MAN -> reportResult(GINGERBREAD_MAN, inventory.addItem(ItemCreator.gingerbreadMan));
                        case "titanpickredfortune" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickRedFortune()));
                        case "titanpickredsilk" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickRedSilk()));
                        case "titanpickyellowfortune" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickYellowFortune()));
                        case "titanpickyellowsilk" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickYellowSilk()));
                        case "titanpickbluefortune" -> reportResult(args[1],  inventory.addItem(configManager.getTitanPickBlueFortune()));
                        case "titanpickbluesilk" -> reportResult(args[1],  inventory.addItem(configManager.getTitanPickBlueSilk()));
                        case "titanshovelred" -> reportResult(args[1], inventory.addItem(configManager.getTitanShovelRed()));
                        case "titanaxered" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeRed()));
                        case "titanaxeyellow" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeYellow()));
                        case "titanaxeblue" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeBlue()));
                        case "titanswordred" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordRed()));
                        case "titanswordyellow" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordYellow()));
                        case "titanswordblue" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordBlue()));
                        case "titanrodred" -> reportResult(args[1], inventory.addItem(configManager.getTitanRodRed()));
                        default -> sender.sendMessage(ChatColor.DARK_RED + "This kit does not exist");
                    }
                }
            }
        }
        return false;
    }

    private void reportResult(String item, HashMap<Integer, ItemStack> droppedItems) {
        if (droppedItems.isEmpty()) {
            logger.sendLog(PLAYER_NAME + " received their " + item);
        } else {
            logger.sendLog(PLAYER_NAME + " did not receive their " + item + " due to a full inventory.");
        }
    }

}
