package com.nessxxiii.titantools.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AddCrystalEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    private final String[] args;

    public AddCrystalEvent(Player player, String[] args) {
        this.player = player;
        this.args = args;
    }

    public Player getPlayer() {
        return player;
    }

    public String[] getArgs() {
        return args;
    }
}
