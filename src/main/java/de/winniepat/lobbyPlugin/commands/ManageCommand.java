package de.winniepat.lobbyPlugin.commands;

import de.winniepat.lobbyPlugin.menus.ManageMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ManageCommand implements CommandExecutor {

    @Override
    public boolean onCommand( CommandSender sender,  Command command,  String s,  String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Only players can execute this command!");
            return true;
        }

        Player player = (Player) sender;

        if(args.length != 1) {
            player.sendMessage(ChatColor.RED + "Use: /manage <player>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);

        if(target == null) {
            player.sendMessage(ChatColor.RED + "Player not found!");
            return true;
        }

        new ManageMenu(player, target);

        return true;
    }
}
