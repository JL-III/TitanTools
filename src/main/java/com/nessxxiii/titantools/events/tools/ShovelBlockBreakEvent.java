package com.nessxxiii.titantools.events.tools;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class ShovelBlockBreakEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    private final Player player;

    private final Block clickedBlock;

    private final BlockFace blockFace;

    public ShovelBlockBreakEvent(Player player, Block clickedBlock, BlockFace blockFace) {
        this.player = player;
        this.clickedBlock = clickedBlock;
        this.blockFace = blockFace;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getClickedBlock() {
        return clickedBlock;
    }

    public BlockFace getBlockFace() {
        return blockFace;
    }
}
