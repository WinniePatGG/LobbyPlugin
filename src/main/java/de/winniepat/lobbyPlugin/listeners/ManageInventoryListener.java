package de.winniepat.lobbyPlugin.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.time.Instant;

public class ManageInventoryListener implements Listener {

    public ManageInventoryListener() {

    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals(ChatColor.RED + "Manage Player")){
            return;
        }

        event.setCancelled(true);

        if(event.getCurrentItem() == null){
            return;
        }

        Player player = (Player) event.getWhoClicked();
        Player target = Bukkit.getPlayer(event.getView().getItem(4).getItemMeta().getDisplayName());

        if(target == null) {
            player.closeInventory();
            player.sendMessage(ChatColor.RED + "Target is not online anymore");
            return;
        }

        if(event.getCurrentItem().getType() == Material.RED_DYE){
            target.setHealth(20);
            player.sendMessage(ChatColor.GREEN + target.getName() + " was healed");
        }else if(event.getCurrentItem().getType() == Material.IRON_SWORD){
            target.setHealth(0);
            player.sendMessage(ChatColor.GREEN + target.getName() + " was killed");
        }else if(event.getCurrentItem().getType() == Material.GRAY_DYE){
            target.kick();
            player.sendMessage(ChatColor.GREEN + target.getName() + " was kicked");
        }else if(event.getCurrentItem().getType() == Material.MACE) {
            target.banPlayer("Join the Discord for more information ");
            player.sendMessage(ChatColor.GREEN + target.getName() + " was banned");
        }
        player.closeInventory();

    }
}
