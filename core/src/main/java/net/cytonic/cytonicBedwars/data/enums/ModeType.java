package net.cytonic.cytonicBedwars.data.enums;

import lombok.Getter;

@Getter
public enum ModeType {
    SOLOS("net.cytonic.cytonicBedwars.modes.solos.SolosMode"),
    DOUBLES("net.cytonic.cytonicBedwars.modes.doubles.DoublesMode"),
    TRIOS("net.cytonic.cytonicBedwars.modes.trios.TriosMode"),
    QUADS("net.cytonic.cytonicBedwars.modes.quads.QuadsMode");
    private final String clazz;

    ModeType(String clazz) {
        this.clazz = clazz;
    }
}
