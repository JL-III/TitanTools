package com.nessxxiii.titantools.events;

import org.bukkit.command.CommandSender;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DebugEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private List<String> lore;

    private CommandSender sender;

    private ItemStack item;

    public DebugEvent(List<String> lore, CommandSender sender, ItemStack item) {
        this.lore = lore;
        this.sender = sender;
        this.item = item;
    }

    public List<String> getLore() {
        return lore;
    }

    public CommandSender getSender() {
        return sender;
    }

    public ItemStack getItemStack() {
        return item;
    }
}
