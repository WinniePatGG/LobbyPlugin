package de.winniepat.lobbyPlugin.trails;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.*;
import org.bukkit.inventory.meta.ItemMeta;

public class TrailGUI implements Listener {

    private final TrailManager trailManager;
    private final String GUI_TITLE = ChatColor.LIGHT_PURPLE + "Select a Trail";

    public TrailGUI(TrailManager trailManager) {
        this.trailManager = trailManager;
        Bukkit.getPluginManager().registerEvents(this, trailManager.getPlugin());
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 27, GUI_TITLE);

        for (TrailType type : TrailType.values()) {
            ItemStack item = new ItemStack(Material.FIREWORK_ROCKET);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + type.getName());
            item.setItemMeta(meta);
            gui.addItem(item);
        }

        ItemStack removeItem = new ItemStack(Material.BARRIER);
        ItemMeta removeMeta = removeItem.getItemMeta();
        removeMeta.setDisplayName(ChatColor.RED + "Remove Trail");
        removeItem.setItemMeta(removeMeta);
        gui.setItem(26, removeItem);

        player.openInventory(gui);
    }

    public TrailManager getTrailManager() {
        return trailManager;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!event.getView().getTitle().equals(GUI_TITLE)) return;

        event.setCancelled(true);
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

        if (name.equalsIgnoreCase("Remove Trail")) {
            trailManager.removeTrail(player);
            player.sendMessage(ChatColor.YELLOW + "Trail removed.");
            player.closeInventory();
            return;
        }

        for (TrailType type : TrailType.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                trailManager.setTrail(player, type);
                player.sendMessage(ChatColor.GREEN + "Trail set to: " + type.getName());
                break;
            }
        }

        player.closeInventory();
    }
}
