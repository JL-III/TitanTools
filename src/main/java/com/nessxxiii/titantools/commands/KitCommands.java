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

                    add("immortal_diadem");

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
                        case "crystal_common" -> reportResult(args[1], inventory.addItem(PowerCrystal.COMMON.getItemStack()), player_name);
                        case "crystal_uncommon" -> reportResult(args[1], inventory.addItem(PowerCrystal.UNCOMMON.getItemStack()), player_name);
                        case "crystal_super" -> reportResult(args[1], inventory.addItem(PowerCrystal.SUPER.getItemStack()), player_name);
                        case "crystal_epic" -> reportResult(args[1], inventory.addItem(PowerCrystal.EPIC.getItemStack()), player_name);
                        case "crystal_ultra" -> reportResult(args[1], inventory.addItem(PowerCrystal.ULTRA.getItemStack()), player_name);

                        case "excavator" -> reportResult(args[1], inventory.addItem(ItemCreator.excavator), player_name);
                        case "sun_fish" -> reportResult(args[1], inventory.addItem(ItemCreator.sunFish), player_name);
                        case "night_fish" -> reportResult(args[1], inventory.addItem(ItemCreator.nightFish), player_name);
                        case "ethereal_fragment" -> reportResult(args[1], inventory.addItem(ItemCreator.etherealFragment), player_name);
                        case "christmas_pick" -> reportResult(args[1], inventory.addItem(ItemCreator.christmasPick), player_name);
                        case "gingerbread_man" -> reportResult(args[1], inventory.addItem(ItemCreator.gingerbreadMan), player_name);

                        case "pick_red_fortune" -> reportResult(args[1], inventory.addItem(configManager.getPickRedFortune()), player_name);
                        case "pick_red_silk" -> reportResult(args[1], inventory.addItem(configManager.getPickRedSilk()), player_name);
                        case "pick_yellow_fortune" -> reportResult(args[1], inventory.addItem(configManager.getPickYellowFortune()), player_name);
                        case "pick_yellow_silk" -> reportResult(args[1], inventory.addItem(configManager.getPickYellowSilk()), player_name);
                        case "pick_blue_fortune" -> reportResult(args[1],  inventory.addItem(configManager.getPickBlueFortune()), player_name);
                        case "pick_blue_silk" -> reportResult(args[1],  inventory.addItem(configManager.getPickBlueSilk()), player_name);

                        case "shovel_red" -> reportResult(args[1], inventory.addItem(configManager.getShovelRed()), player_name);

                        case "axe_red" -> reportResult(args[1], inventory.addItem(configManager.getAxeRed()), player_name);
                        case "axe_yellow" -> reportResult(args[1], inventory.addItem(configManager.getAxeYellow()), player_name);
                        case "axe_blue" -> reportResult(args[1], inventory.addItem(configManager.getAxeBlue()), player_name);

                        case "sword_red" -> reportResult(args[1], inventory.addItem(configManager.getSwordRed()), player_name);
                        case "sword_yellow" -> reportResult(args[1], inventory.addItem(configManager.getSwordYellow()), player_name);
                        case "sword_blue" -> reportResult(args[1], inventory.addItem(configManager.getSwordBlue()), player_name);

                        case "rod_red" -> reportResult(args[1], inventory.addItem(configManager.getRodRed()), player_name);

                        case "immortal_diadem" -> reportResult(args[1], inventory.addItem(configManager.getImmortalDiadem()), player_name);

                        default -> sender.sendMessage(Component.text("This kit does not exist").color(NamedTextColor.DARK_RED));
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
