package de.winniepat.lobbyPlugin.menus;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class ManageMenu {

    public ManageMenu(Player player, Player target) {

        Inventory inventory = Bukkit.createInventory(null, 27, ChatColor.RED + "Manage Player");

        ItemStack targetHead = new ItemStack(Material.PLAYER_HEAD, 1);
        ItemMeta targetHeadMeta = targetHead.getItemMeta();
        targetHeadMeta.setDisplayName(target.getName());
        targetHead.setItemMeta(targetHeadMeta);
        inventory.setItem(4, targetHead);

        ItemStack heal = new ItemStack(Material.RED_DYE, 1);
        ItemMeta healMeta = heal.getItemMeta();
        healMeta.setDisplayName(ChatColor.RED + "Heal");
        heal.setItemMeta(healMeta);
        inventory.setItem(9, heal);

        ItemStack kill = new ItemStack(Material.IRON_SWORD, 1);
        ItemMeta killMeta = kill.getItemMeta();
        killMeta.setDisplayName(ChatColor.BLUE + "Kill");
        kill.setItemMeta(killMeta);
        inventory.setItem(11, kill);

        ItemStack kick = new ItemStack(Material.GRAY_DYE, 1);
        ItemMeta kickMeta = kick.getItemMeta();
        kickMeta.setDisplayName(ChatColor.GRAY + "Kick");
        kick.setItemMeta(kickMeta);
        inventory.setItem(13, kick);

        ItemStack ban = new ItemStack(Material.MACE, 1);
        ItemMeta banMeta = ban.getItemMeta();
        banMeta.setDisplayName(ChatColor.DARK_RED + "Ban");
        ban.setItemMeta(banMeta);
        inventory.setItem(15, ban);

        player.openInventory(inventory);
    }
}
