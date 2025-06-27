package de.winniepat.lobbyPlugin.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DiscordCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;

            TextComponent message = new TextComponent("Click here to join our discord server");
            message.setColor(ChatColor.AQUA);
            message.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://dc.lunaris-mc.de"));

            player.sendMessage(message);
        }
        return true;
    }
}
