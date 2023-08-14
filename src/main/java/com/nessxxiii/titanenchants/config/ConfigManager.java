package com.nessxxiii.titanenchants.config;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ConfigManager {

    private final Plugin plugin;
    private final Set<Material> allowedPickBlocks = new HashSet<>();
    private final Set<Material> allowedAxeBlocks = new HashSet<>();
    public ConfigManager(Plugin plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        setAllowedBlocks("titanPick", allowedPickBlocks);
        setAllowedBlocks("titanAxe", allowedAxeBlocks);
    }

    public void setAllowedBlocks(String targetSection, Set<Material> targetList) {
        ConfigurationSection trench = plugin.getConfig().getConfigurationSection(targetSection);
        if (trench == null) {
            plugin.getLogger().warning(targetSection + " configuration section not found!");
            return;
        }

        List<String> items = trench.getStringList("destroyable-items");

        if (items.size() == 0) {
            plugin.getLogger().warning("No destroyable-items found in trench section of config.");
        }

        for (String item : items) {
            try {
                targetList.add(Material.valueOf(item));
            } catch (Exception e) {
                plugin.getLogger().warning("'" + item + "' is not a valid material name! Skipping this item.");
            }
        }
        Bukkit.getConsoleSender().sendMessage("Loaded " + targetList.size() + " items for " + targetSection);
    }

    public Set<Material> getAllowedPickBlocks() {
        return allowedPickBlocks;
    }

    public Set<Material> getAllowedAxeBlocks() { return allowedAxeBlocks; }
}
