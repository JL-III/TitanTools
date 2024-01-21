package com.nessxxiii.titantools.events.tools.titan.enchants;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

public class RodCatchFishEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    private final Item caughtItem;

    private final Vector velocity;

    public RodCatchFishEvent(Player player, Item caughtItem, Vector velocity) {
        this.player = player;
        this.caughtItem = caughtItem;
        this.velocity = velocity;
    }

    public Player getPlayer() {
        return player;
    }

    public Item getCaughtItem() {
        return caughtItem;
    }

    public Vector getVelocity() {
        return velocity;
    }
}
