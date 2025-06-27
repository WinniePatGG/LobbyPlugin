package de.winniepat.lobbyPlugin.pets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PetSelectorGUI implements Listener {

    private final PetManager petManager;

    public PetSelectorGUI(PetManager petManager) {
        this.petManager = petManager;
        Bukkit.getPluginManager().registerEvents(this, petManager.getPlugin());
    }

    public void open(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Choose Your Pet");

        int index = 0;
        for (PetType petType : PetType.values()) {
            ItemStack item = new ItemStack(petType.getIcon());
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(ChatColor.AQUA + petType.getDisplayName());
            item.setItemMeta(meta);

            gui.setItem(index++, item);
        }

        player.openInventory(gui);
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!event.getView().getTitle().equals(ChatColor.GREEN + "Choose Your Pet")) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());
        PetType selectedPet = PetType.fromDisplayName(name);

        if (selectedPet != null) {
            petManager.spawnPet(player, selectedPet.getEntityType());
            player.closeInventory();
        }
    }
}
