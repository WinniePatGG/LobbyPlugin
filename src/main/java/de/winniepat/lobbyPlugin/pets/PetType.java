package de.winniepat.lobbyPlugin.pets;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

public enum PetType {
    CHICKEN("Chicken", Material.EGG, EntityType.CHICKEN),
    SHEEP("Sheep", Material.WHITE_WOOL, EntityType.SHEEP),
    WOLF("Wolf", Material.BONE, EntityType.WOLF),
    CAT("Cat", Material.STRING, EntityType.CAT),
    COW("Cow", Material.LEATHER, EntityType.COW),
    RABBIT("Rabbit", Material.RABBIT_FOOT, EntityType.RABBIT);

    private final String displayName;
    private final Material icon;
    private final EntityType entityType;

    PetType(String displayName, Material icon, EntityType entityType) {
        this.displayName = displayName;
        this.icon = icon;
        this.entityType = entityType;
    }

    public String getDisplayName() {
        return displayName;
    }

    public Material getIcon() {
        return icon;
    }

    public EntityType getEntityType() {
        return entityType;
    }

    public static PetType fromDisplayName(String name) {
        for (PetType type : values()) {
            if (type.displayName.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
