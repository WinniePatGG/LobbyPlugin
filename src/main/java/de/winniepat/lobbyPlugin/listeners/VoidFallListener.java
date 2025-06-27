package de.winniepat.lobbyPlugin.listeners;

import de.winniepat.lobbyPlugin.LobbyPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class VoidFallListener implements Listener {

    private final LobbyPlugin plugin;

    public VoidFallListener(LobbyPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerFall(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location loc = player.getLocation();

        if (loc.getY() <= -70) {
            World world = player.getWorld();
            Location spawnLocation = new Location(world, 38.5, 39, 83.5, 90.0f, 0.0f);
            player.teleport(spawnLocation);
            player.sendMessage(ChatColor.RED + "You fell off the map!");
        }
    }
}
