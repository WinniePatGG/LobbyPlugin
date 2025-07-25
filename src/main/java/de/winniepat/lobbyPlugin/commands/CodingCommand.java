package de.winniepat.lobbyPlugin.commands;

import de.winniepat.lobbyPlugin.LobbyPlugin;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CodingCommand implements CommandExecutor {

    private Plugin plugin;
    private LobbyPlugin lobbyPlugin;
    private final Map<UUID, ArmorStand> codingStands = new HashMap<>();
    private final Map<UUID, BukkitRunnable> animationTasks = new HashMap<>();

    private static final String[] COLORS = {
            "#FF0000", "#FF4000", "#FF8000", "#FFBF00", "#FFFF00",
            "#BFFF00", "#80FF00", "#40FF00", "#00FF00", "#00FF40",
            "#00FF80", "#00FFBF", "#00FFFF", "#00BFFF", "#0080FF",
            "#0040FF", "#0000FF", "#4000FF", "#8000FF", "#BF00FF",
            "#FF00FF"
    };

    private final String[] frames = new String[21];

    public CodingCommand(Plugin plugin, LobbyPlugin lobbyPlugin) {
        this.plugin = plugin;
        this.lobbyPlugin = lobbyPlugin;

        char[] glyphs = {
                '\uE014', '\uE015', '\uE016', '\uE017', '\uE018',
                '\uE019', '\uE020', '\uE021', '\uE022', '\uE023',
                '\uE024', '\uE025', '\uE026', '\uE035', '\uE028',
                '\uE029', '\uE030', '\uE031', '\uE032', '\uE033',
                '\uE034'
        };
        for (int i = 0; i < 21; i++) {
            frames[i] = glyphs[i] + " " + rgb("Coding...", COLORS[i]);
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length != 1 || (!args[0].equalsIgnoreCase("on") && !args[0].equalsIgnoreCase("off"))) {
            sender.sendMessage("§cUse /coding on or /coding off");
            return true;
        }

        UUID uuid = player.getUniqueId();

        if (args[0].equalsIgnoreCase("on")) {
            if (codingStands.containsKey(uuid)) {
                sender.sendMessage("§eYou're already marked as coding.");
                return true;
            }

            Location loc = player.getLocation().add(0, 2.2, 0);
            ArmorStand stand = player.getWorld().spawn(loc, ArmorStand.class);
            stand.setVisible(false);
            stand.setCustomNameVisible(true);
            stand.setGravity(false);
            stand.setMarker(true);

            codingStands.put(uuid, stand);

            BukkitRunnable task = new BukkitRunnable() {
                int tick = 0;

                @Override
                public void run() {
                    if (!stand.isValid() || !player.isOnline()) {
                        this.cancel();
                        if (stand.isValid()) stand.remove();
                        codingStands.remove(uuid);
                        animationTasks.remove(uuid);
                        return;
                    }

                    stand.teleport(player.getLocation().add(0, 2.2, 0));
                    stand.setCustomName(frames[tick % frames.length]);
                    tick++;
                }
            };
            task.runTaskTimer(plugin, 0L, 4L);

            animationTasks.put(uuid, task);
            sender.sendMessage("§aYou are now marked as coding.");
        }

        if (args[0].equalsIgnoreCase("off")) {
            if (animationTasks.containsKey(uuid)) {
                animationTasks.get(uuid).cancel();
                animationTasks.remove(uuid);
            }

            if (codingStands.containsKey(uuid)) {
                ArmorStand stand = codingStands.remove(uuid);
                if (stand != null && !stand.isDead()) {
                    stand.remove();
                }
                player.sendMessage("§aYou are no longer marked as coding.");
            } else {
                sender.sendMessage("§cError while executing.");
            }
        }
        return true;
    }

    private static String rgb(String text, String hexColor) {
        StringBuilder out = new StringBuilder("§x");
        for (char c : hexColor.substring(1).toCharArray()) {
            out.append('§').append(c);
        }
        return out + text;
    }

    public Map<UUID, ArmorStand> getCodingStands() {
        return codingStands;
    }

    public Map<UUID, BukkitRunnable> getAnimationTasks() {
        return animationTasks;
    }
}
