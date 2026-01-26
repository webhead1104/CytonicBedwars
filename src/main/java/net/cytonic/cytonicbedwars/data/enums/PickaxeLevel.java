package net.cytonic.cytonicbedwars.data.enums;

import lombok.Getter;

@Getter
public enum PickaxeLevel {
    NONE,
    WOODEN,
    STONE,
    IRON,
    DIAMOND;

    public static PickaxeLevel getByOrdinal(int index) {
        for (PickaxeLevel level : values()) {
            if (level.ordinal() == index) {
                return level;
            }
        }
        return null;
    }
}
