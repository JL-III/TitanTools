package com.nessxxiii.titantools.commands;

import com.nessxxiii.titantools.enums.PowerCrystal;
import com.nessxxiii.titantools.utils.ConfigManager;
import com.nessxxiii.titantools.itemmanagement.ItemCreator;
import com.nessxxiii.titantools.utils.CustomLogger;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
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
                    add("excavator");
                    add("sun_fish");
                    add("night_fish");
                    add("ethereal_fragment");
                    add("christmas_pick");
                    add("gingerbread_man");

                    add("pick_red_fortune");
                    add("pick_red_silk");
                    add("pick_yellow_fortune");

                    add("pick_yellow_silk");
                    add("pick_blue_fortune");
                    add("pick_blue_silk");

                    add("shovel_red");

                    add("axe_red");
                    add("axe_yellow");
                    add("axe_blue");

                    add("sword_red");
                    add("sword_yellow");
                    add("sword_blue");

                    add("rod_red");

                    add("immortal_diadem");

                }};
            }
            if (args.length == 2) {
                return Bukkit.getOnlinePlayers().stream().collect(ArrayList::new, (list, p) -> list.add(p.getName()), ArrayList::addAll);
            }
        }
        return new ArrayList<>();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.hasPermission("titan.kit.give")) {
            return false;
        }
        if (args.length >= 2) {
            if (Bukkit.getPlayer(args[1]) != null) {
                Player player = Bukkit.getPlayer(args[1]);
                if (player == null) return false;
                String player_name = player.getName();
                Inventory inventory = player.getInventory();
                String input = args[0];
                switch (input) {
                    case "excavator" -> reportResult(input, inventory.addItem(ItemCreator.excavator), player_name);
                    case "sun_fish" -> reportResult(input, inventory.addItem(ItemCreator.sunFish), player_name);
                    case "night_fish" -> reportResult(input, inventory.addItem(ItemCreator.nightFish), player_name);
                    case "ethereal_fragment" -> reportResult(input, inventory.addItem(ItemCreator.etherealFragment), player_name);
                    case "christmas_pick" -> reportResult(input, inventory.addItem(ItemCreator.christmasPick), player_name);
                    case "gingerbread_man" -> reportResult(input, inventory.addItem(ItemCreator.gingerbreadMan), player_name);

                    case "pick_red_fortune" -> reportResult(input, inventory.addItem(configManager.getPickRedFortune()), player_name);
                    case "pick_red_silk" -> reportResult(input, inventory.addItem(configManager.getPickRedSilk()), player_name);
                    case "pick_yellow_fortune" -> reportResult(input, inventory.addItem(configManager.getPickYellowFortune()), player_name);
                    case "pick_yellow_silk" -> reportResult(input, inventory.addItem(configManager.getPickYellowSilk()), player_name);
                    case "pick_blue_fortune" -> reportResult(input,  inventory.addItem(configManager.getPickBlueFortune()), player_name);
                    case "pick_blue_silk" -> reportResult(input,  inventory.addItem(configManager.getPickBlueSilk()), player_name);

                    case "shovel_red" -> reportResult(input, inventory.addItem(configManager.getShovelRed()), player_name);

                    case "axe_red" -> reportResult(input, inventory.addItem(configManager.getAxeRed()), player_name);
                    case "axe_yellow" -> reportResult(input, inventory.addItem(configManager.getAxeYellow()), player_name);
                    case "axe_blue" -> reportResult(input, inventory.addItem(configManager.getAxeBlue()), player_name);

                    case "sword_red" -> reportResult(input, inventory.addItem(configManager.getSwordRed()), player_name);
                    case "sword_yellow" -> reportResult(input, inventory.addItem(configManager.getSwordYellow()), player_name);
                    case "sword_blue" -> reportResult(input, inventory.addItem(configManager.getSwordBlue()), player_name);

                    case "rod_red" -> reportResult(input, inventory.addItem(configManager.getRodRed()), player_name);

                    case "immortal_diadem" -> reportResult(input, inventory.addItem(configManager.getImmortalDiadem()), player_name);

                    default -> sender.sendMessage(Component.text("This kit does not exist").color(NamedTextColor.DARK_RED));
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
