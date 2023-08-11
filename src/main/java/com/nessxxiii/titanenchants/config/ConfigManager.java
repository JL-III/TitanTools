package com.nessxxiii.titanenchants.config;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final Plugin plugin;
    public final Set<Material> allowedItems = new HashSet<>();
    public static final Set<Material> ENCHANTABLE_ITEMS = new HashSet<>();

    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
    }

    public void loadConfig() {
        ENCHANTABLE_ITEMS.clear();
        ConfigurationSection trench = plugin.getConfig().getConfigurationSection("trench");
        if (trench == null) {
            plugin.getLogger().warning("Trench configuration not found!");
            return;
        }

        List<String> items = trench.getStringList("destroyable-items");

        if (items.size() == 0) {
            plugin.getLogger().warning("No destroyable-items found in trench section of config.");
        }

        for (String item : items) {
            try {
                allowedItems.add(Material.valueOf(item));
            } catch (Exception e) {
                plugin.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }

        List<String> enchantableItems = trench.getStringList("enchantable-items");

        if (items.size() == 0) {
            plugin.getLogger().warning("No enchantable-items found in trench section of config.");
        }

        for (String item : enchantableItems) {
            try {
                ENCHANTABLE_ITEMS.add(Material.valueOf(item));
            } catch (Exception e) {
                plugin.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
    }

    public Set<Material> getAllowedItems() {
        return allowedItems;
    }

}
