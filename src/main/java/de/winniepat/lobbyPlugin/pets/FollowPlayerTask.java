package de.winniepat.lobbyPlugin.pets;

import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class FollowPlayerTask extends BukkitRunnable {
    private final Plugin plugin;
    private final Mob pet;
    private final Player owner;

    public FollowPlayerTask(Plugin plugin, Mob pet, Player owner) {
        this.plugin = plugin;
        this.pet = pet;
        this.owner = owner;
    }

    public void start() {
        this.runTaskTimer(plugin, 0L, 20L);
    }

    @Override
    public void run() {
        if (!owner.isOnline() || pet.isDead()) {
            cancel();
            return;
        }

        if (pet.getLocation().distance(owner.getLocation()) > 3) {
            pet.getPathfinder().moveTo(owner.getLocation());
        }
    }
}
