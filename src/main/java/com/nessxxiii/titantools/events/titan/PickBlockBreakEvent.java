package com.nessxxiii.titantools.events.titan;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PickBlockBreakEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    public PickBlockBreakEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
