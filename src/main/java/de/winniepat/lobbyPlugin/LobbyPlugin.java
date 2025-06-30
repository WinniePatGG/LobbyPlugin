package de.winniepat.lobbyPlugin;

import de.winniepat.lobbyPlugin.commands.*;
import de.winniepat.lobbyPlugin.listeners.*;
import de.winniepat.lobbyPlugin.pets.PetManager;
import de.winniepat.lobbyPlugin.trails.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public final class LobbyPlugin extends JavaPlugin {

    private PetManager petManager;
    private TrailManager trailManager;
    private TrailGUI trailGUI;
    CodingCommand codingCommand = new CodingCommand(this, this);

    @Override
    public void onEnable() {
        petManager = new PetManager(this);

        trailManager = new TrailManager(this);
        trailGUI = new TrailGUI(trailManager);

        registerCommands();
        registerListeners();

        trailManager.startTrailTask();

        getLogger().info("Started successfully");
    }

    @Override
    public void onDisable() {
        petManager.removeAllPets();
        getLogger().info("Shut down successfully");
    }

    private void registerCommands() {
        Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand());
        Objects.requireNonNull(getCommand("manage")).setExecutor(new ManageCommand());
        Objects.requireNonNull(getCommand("discord")).setExecutor(new DiscordCommand());
        Objects.requireNonNull(getCommand("pet")).setExecutor(new PetCommand(petManager));
        Objects.requireNonNull(getCommand("trail")).setExecutor(new TrailCommand(trailGUI));
        Objects.requireNonNull(getCommand("coding")).setExecutor(new CodingCommand(this, this));
    }

    private void registerListeners() {
        getServer().getPluginManager().registerEvents(new CodingCleanupListener(codingCommand), this);
        getServer().getPluginManager().registerEvents(new ManageInventoryListener(), this);
        getServer().getPluginManager().registerEvents(new RespawnListener(), this);
        getServer().getPluginManager().registerEvents(new ServerJoinListener(), this);
        getServer().getPluginManager().registerEvents(trailGUI, this);
        getServer().getPluginManager().registerEvents(new VoidFallListener(this), this);
        getServer().getPluginManager().registerEvents(new HungerPreventListener(),this);
    }
}
