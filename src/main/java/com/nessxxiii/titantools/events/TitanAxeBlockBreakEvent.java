package com.nessxxiii.titantools.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TitanAxeBlockBreakEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    public TitanAxeBlockBreakEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
