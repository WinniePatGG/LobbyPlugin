package de.winniepat.lobbyPlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;

public class RespawnListener implements Listener {

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event){
        Player player = event.getPlayer();
        Location spawnLoc = new Location(Bukkit.getWorld("world"), 38.5, 39, 83.5, 90, 0);
        player.teleport(spawnLoc);
    }
}
