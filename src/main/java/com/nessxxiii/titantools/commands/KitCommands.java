package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.utils.ConfigManager;
import com.nessxxiii.titantools.itemmanagement.ItemCreator;
import com.nessxxiii.titantools.utils.CustomLogger;
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

    //TODO this needs to be implemented.
    private final String CASINO_KEY = "casinokey";

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
                    add("common");
                    add("uncommon");
                    add("super");
                    add("epic");
                    add("ultra");
                    add("excavator");
                    add("sunfish");
                    add("nightfish");
                    add("etherealfragment");
                    add("christmaspick");
                    add("gingerbreadman");

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
                    String player_name = player.getName();
                    Inventory inventory = player.getInventory();
                    switch (args[1]) {
                        case "common" -> reportResult(args[1], inventory.addItem(ItemCreator.powerCrystalCommon), player_name);
                        case "uncommon" -> reportResult(args[1], inventory.addItem(ItemCreator.powerCrystalUncommon), player_name);
                        case "super" -> reportResult(args[1], inventory.addItem(ItemCreator.powerCrystalSuper), player_name);
                        case "epic" -> reportResult(args[1], inventory.addItem(ItemCreator.powerCrystalEpic), player_name);
                        case "ultra" -> reportResult(args[1], inventory.addItem(ItemCreator.powerCrystalUltra), player_name);
                        case "excavator" -> reportResult(args[1], inventory.addItem(ItemCreator.excavator), player_name);
                        case "sunfish" -> reportResult(args[1], inventory.addItem(ItemCreator.sunFish), player_name);
                        case "nightfish" -> reportResult(args[1], inventory.addItem(ItemCreator.nightFish), player_name);
                        case "etherealfragment" -> reportResult(args[1], inventory.addItem(ItemCreator.etherealFragment), player_name);
                        case "christmaspick" -> reportResult(args[1], inventory.addItem(ItemCreator.christmasPick), player_name);
                        case "gingerbreadman" -> reportResult(args[1], inventory.addItem(ItemCreator.gingerbreadMan), player_name);
                        case "titan_pick_red_fortune" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickRedFortune()), player_name);
                        case "titan_pick_red_silk" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickRedSilk()), player_name);
                        case "titan_pick_yellow_fortune" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickYellowFortune()), player_name);
                        case "titan_pick_yellow_silk" -> reportResult(args[1], inventory.addItem(configManager.getTitanPickYellowSilk()), player_name);
                        case "titan_pick_blue_fortune" -> reportResult(args[1],  inventory.addItem(configManager.getTitanPickBlueFortune()), player_name);
                        case "titan_pick_blue_silk" -> reportResult(args[1],  inventory.addItem(configManager.getTitanPickBlueSilk()), player_name);
                        case "titan_shovel_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanShovelRed()), player_name);
                        case "titan_axe_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeRed()), player_name);
                        case "titan_axe_yellow" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeYellow()), player_name);
                        case "titan_axe_blue" -> reportResult(args[1], inventory.addItem(configManager.getTitanAxeBlue()), player_name);
                        case "titan_sword_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordRed()), player_name);
                        case "titan_sword_yellow" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordYellow()), player_name);
                        case "titan_sword_blue" -> reportResult(args[1], inventory.addItem(configManager.getTitanSwordBlue()), player_name);
                        case "titan_rod_red" -> reportResult(args[1], inventory.addItem(configManager.getTitanRodRed()), player_name);
                        default -> sender.sendMessage(ChatColor.DARK_RED + "This kit does not exist");
                    }
                }
            }
        }
        return false;
    }

    private void reportResult(String item_name, HashMap<Integer, ItemStack> droppedItems, String player_name) {
        if (droppedItems.isEmpty()) {
            logger.sendLog(player_name + " received their " + item_name);
        } else {
            logger.sendLog(player_name + " did not receive their " + item_name + " due to a full inventory.");
        }
    }
}
