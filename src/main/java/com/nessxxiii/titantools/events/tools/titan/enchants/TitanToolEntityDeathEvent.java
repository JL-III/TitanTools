package com.nessxxiii.titantools.events.tools.titan.enchants;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class TitanToolEntityDeathEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    private final int droppedExp;

    public TitanToolEntityDeathEvent(Player player, int droppedExp) {
        this.player = player;
        this.droppedExp = droppedExp;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDroppedExp() {
        return droppedExp;
    }
}
