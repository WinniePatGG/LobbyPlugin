package de.winniepat.lobbyPlugin.commands;

import de.winniepat.lobbyPlugin.trails.TrailGUI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TrailCommand implements CommandExecutor {

    private final TrailGUI trailGUI;

    public TrailCommand(TrailGUI trailGUI) {
        this.trailGUI = trailGUI;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        trailGUI.open(player);
        return true;
    }
}
