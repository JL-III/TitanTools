package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.config.ConfigManager;
import com.nessxxiii.titantools.items.ItemCreator;
import com.nessxxiii.titantools.util.CustomLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KitCommands implements CommandExecutor, TabCompleter {

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
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player player && player.hasPermission("titan.kit.admin.tabcomplete")) {
            if (args.length == 1) {
                return new ArrayList<>() {{
                    add("kit");
                }};
            }
            if (args.length == 2) {
                return new ArrayList<>() {{
                    add("titan_pick_red_fortune");
                    add("titan_pick_red_silk");
                    add("titan_pick_yellow_fortune");

                    add("titan_pick_yellow_silk");
                    add("titan_pick_blue_fortune");
                    add("titan_pick_blue_silk");

                    add("titan_shovel_red");

                    add("titan_axe_red");
                    add("titan_axe_yellow");
                    add("titan_axe_blue");

                    add("titan_sword_red");
                    add("titan_sword_yellow");
                    add("titan_sword_blue");

                    add("titan_rod_red");
                }};
            }
            if (args.length == 3) {
                return Bukkit.getOnlinePlayers().stream().collect(ArrayList::new, (list, p) -> list.add(p.getName()), ArrayList::addAll);
            }
        }
        return new ArrayList<>();
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
                        case "titan_pick_red_fortune" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickRedFortune()));
                        case "titan_pick_red_silk" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickRedSilk()));
                        case "titan_pick_yellow_fortune" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickYellowFortune()));
                        case "titan_pick_yellow_silk" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickYellowSilk()));
                        case "titan_pick_blue_fortune" -> reportResult(args[1],  inventory.addItem(configManager.getTitanPickBlueFortune()));
                        case "titan_pick_blue_silk" -> reportResult(args[1],  inventory.addItem(configManager.getTitanPickBlueSilk()));
                        case "titan_shovel_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanShovelRed()));
                        case "titan_axe_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeRed()));
                        case "titan_axe_yellow" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeYellow()));
                        case "titan_axe_blue" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeBlue()));
                        case "titan_sword_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordRed()));
                        case "titan_sword_yellow" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordYellow()));
                        case "titan_sword_blue" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordBlue()));
                        case "titan_rod_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanRodRed()));
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
