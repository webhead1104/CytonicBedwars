package net.cytonic.cytonicbedwars.data.enums;

import lombok.Getter;

@Getter
public enum AxeLevel {
    NONE,
    WOODEN,
    STONE,
    IRON,
    DIAMOND;

    public static AxeLevel getByOrdinal(int index) {
        for (AxeLevel level : values()) {
            if (level.ordinal() == index) {
                return level;
            }
        }
        return null;
    }
}
