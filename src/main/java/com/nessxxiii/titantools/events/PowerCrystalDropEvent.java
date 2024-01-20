package com.nessxxiii.titantools.events;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PowerCrystalDropEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private boolean isCancelled;

    private final Location playerLocation;

    private final Location dropLocation;

    public PowerCrystalDropEvent(Location playerLocation, Location dropLocation) {
        this.playerLocation = playerLocation;
        this.dropLocation = dropLocation;
        this.isCancelled = false;
    }

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
