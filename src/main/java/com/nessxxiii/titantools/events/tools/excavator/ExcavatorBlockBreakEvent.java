package com.nessxxiii.titantools.events.tools.excavator;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ExcavatorBlockBreakEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private boolean isCancelled;

    private final Player player;

    private final Location playerLocation;

    private final Location dropLocation;

    public ExcavatorBlockBreakEvent(Player player, Location dropLocation) {
        this.player = player;
        this.playerLocation = player.getLocation();
        this.dropLocation = dropLocation;
        this.isCancelled = false;
    }

    public Player getPlayer() { return player; }

    public Location getPlayerLocation() {
        return playerLocation;
    }

    public Location getDropLocation() {
        return dropLocation;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public boolean isCancelled() {
        return isCancelled;
    }
}
