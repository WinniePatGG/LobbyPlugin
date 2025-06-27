package de.winniepat.lobbyPlugin.pets;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class PetActionRegistry {
    private static final Map<String, Consumer<Player>> actions = new HashMap<>();

    public static void register(String key, Consumer<Player> action) {
        actions.put(key, action);
    }

    public static Consumer<Player> get(String key) {
        return actions.get(key);
    }
}
