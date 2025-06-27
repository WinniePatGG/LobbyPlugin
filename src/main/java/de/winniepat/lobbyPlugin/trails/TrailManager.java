package de.winniepat.lobbyPlugin.trails;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class TrailManager {

    private final JavaPlugin plugin;
    private final Map<UUID, TrailType> activeTrails = new HashMap<>();

    public TrailManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }


    public void setTrail(Player player, TrailType type) {
        activeTrails.put(player.getUniqueId(), type);
    }

    public void removeTrail(Player player) {
        activeTrails.remove(player.getUniqueId());
    }

    public void startTrailTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    TrailType type = activeTrails.get(player.getUniqueId());
                    if (type == null) continue;

                    Location loc = player.getLocation().add(0, 0.1, 0);

                    switch (type) {
                        case SNOWBALL -> {
                            Particle.DustOptions dust = new Particle.DustOptions(Color.RED, 1.5f);
                            player.getWorld().spawnParticle(Particle.SNOWFLAKE, loc, 5, 0.3, 0.1, 0.3, 0, dust);
                        }
                        case NOTE -> {
                            for (int i = 0; i < 3; i++) {
                                player.getWorld().spawnParticle(Particle.NOTE, loc, 1, 0.3, 0, 0.3, 1);
                            }
                        }
                        default -> {
                            player.getWorld().spawnParticle(type.getParticle(), loc, 5, 0.3, 0.1, 0.3, 0.01);
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 10);
    }
}
