package de.winniepat.lobbyPlugin.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinClearListener implements Listener {

    @EventHandler
    public void onPlayerJoinClear(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        for (int i = 0; i < 100; i++) {
            player.sendMessage("");
        }
    }
}
