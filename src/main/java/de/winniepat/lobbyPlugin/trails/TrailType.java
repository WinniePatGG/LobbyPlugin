package de.winniepat.lobbyPlugin.trails;

import org.bukkit.Particle;

public enum TrailType {
    HEART("Hearts", Particle.HEART),
    FLAME("Flame", Particle.FLAME),
    CLOUD("Cloud", Particle.CLOUD),
    ENCHANT("Enchant", Particle.ENCHANT),
    CRIT_MAGIC("Crit Magic", Particle.CRIT),
    SNOWBALL("Snowball Poof", Particle.SNOWFLAKE),
    WITCH("Witch Magic", Particle.WITCH),
    PORTAL("Portal", Particle.PORTAL),
    NOTE("Note", Particle.NOTE),
    VILLAGER_HAPPY("Villager Happy", Particle.HAPPY_VILLAGER),
    DRIP_WATER("Dripping Water", Particle.DRIPPING_WATER),
    LAVA("Lava", Particle.LAVA),
    FIREWORKS_SPARK("Firework", Particle.FIREWORK);

    private final String name;
    private final Particle particle;

    TrailType(String name, Particle particle) {
        this.name = name;
        this.particle = particle;
    }

    public String getName() {
        return name;
    }

    public Particle getParticle() {
        return particle;
    }
}
