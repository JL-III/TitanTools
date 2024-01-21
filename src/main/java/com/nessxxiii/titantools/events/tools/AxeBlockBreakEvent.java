package com.nessxxiii.titantools.events.tools;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AxeBlockBreakEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    private final Block block;

    public AxeBlockBreakEvent(Player player, Block block) {
        this.player = player;
        this.block = block;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getBlock() {
        return block;
    }
}
