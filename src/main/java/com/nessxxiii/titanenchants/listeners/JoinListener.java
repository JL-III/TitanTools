package com.nessxxiii.titanenchants.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.getPlayer().setResourcePack("https://www.dropbox.com/scl/fo/e5rksqxy5rvu0lzltnte0/h?dl=1&rlkey=hg2zik51ltbdcxgyoo2b9jix7");
    }

}
