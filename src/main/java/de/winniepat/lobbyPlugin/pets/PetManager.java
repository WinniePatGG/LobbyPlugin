package de.winniepat.lobbyPlugin.pets;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.*;

public class PetManager implements Listener {
    private final JavaPlugin plugin;
    private final Map<UUID, Mob> activePets = new HashMap<>();

    public PetManager(JavaPlugin plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public void spawnPet(Player player, EntityType type) {
        UUID uuid = player.getUniqueId();
        removePet(uuid);

        Mob mob = (Mob) player.getWorld().spawnEntity(player.getLocation().add(1, 0, 1), type);
        mob.setCustomName(ChatColor.GREEN + player.getName() + "'s Pet");
        mob.setCustomNameVisible(true);
        mob.setAI(true);
        mob.setInvulnerable(true);
        mob.setCollidable(false);

        activePets.put(uuid, mob);
        new FollowPlayerTask(plugin, mob, player).start();
    }

    public void removePet(UUID uuid) {
        Mob mob = activePets.remove(uuid);
        if (mob != null && !mob.isDead()) {
            mob.remove();
        }
    }

    public void removeAllPets() {
        activePets.values().forEach(Entity::remove);
        activePets.clear();
    }

    public void openPetSelectionGUI(Player player) {
        Inventory gui = Bukkit.createInventory(null, 9, ChatColor.GREEN + "Choose Your Pet");

        addPetOption(gui, Material.EGG, "Chicken", EntityType.CHICKEN);
        addPetOption(gui, Material.BONE, "Wolf", EntityType.WOLF);
        addPetOption(gui, Material.CARROT, "Rabbit", EntityType.RABBIT);
        addPetOption(gui, Material.WHITE_WOOL, "Sheep", EntityType.SHEEP);
        addPetOption(gui, Material.STRING, "Cat", EntityType.CAT);
        addPetOption(gui, Material.LEATHER, "Cow", EntityType.COW);
        addPetOption(gui, Material.SWEET_BERRIES, "Fox", EntityType.FOX);

        ItemStack removePetItem = new ItemStack(Material.BARRIER);
        ItemMeta meta = removePetItem.getItemMeta();
        meta.setDisplayName(ChatColor.RED + "Remove Pet");
        removePetItem.setItemMeta(meta);
        gui.setItem(8, removePetItem);

        player.openInventory(gui);
    }

    private void addPetOption(Inventory gui, Material material, String name, EntityType type) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.AQUA + name);
        item.setItemMeta(meta);
        gui.addItem(item);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        String title = event.getView().getTitle();
        if (!title.equals(ChatColor.GREEN + "Choose Your Pet")) return;

        event.setCancelled(true);

        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;

        String name = ChatColor.stripColor(clicked.getItemMeta().getDisplayName());

        if (name.equalsIgnoreCase("Remove Pet")) {
            removePet(player.getUniqueId());
            player.closeInventory();
            return;
        }

        EntityType type = switch (name.toLowerCase()) {
            case "chicken" -> EntityType.CHICKEN;
            case "wolf" -> EntityType.WOLF;
            case "rabbit" -> EntityType.RABBIT;
            case "sheep" -> EntityType.SHEEP;
            case "cat" -> EntityType.CAT;
            case "cow" -> EntityType.COW;
            case "fox" -> EntityType.FOX;
            default -> null;
        };


        if (type != null) {
            spawnPet(player, type);
            player.closeInventory();
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        removePet(uuid);
        plugin.getLogger().info("[Pets] Despawned " + event.getPlayer().getName() + "'s pet.");
    }
}