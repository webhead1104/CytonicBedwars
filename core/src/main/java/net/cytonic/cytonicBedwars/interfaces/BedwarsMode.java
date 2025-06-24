package net.cytonic.cytonicBedwars.interfaces;

import net.cytonic.cytonicBedwars.data.objects.Team;
import net.cytonic.cytosis.events.npcs.NpcInteractEvent;

import java.util.List;

public interface BedwarsMode {
    void start();

    List<Team> getTeams();
}
