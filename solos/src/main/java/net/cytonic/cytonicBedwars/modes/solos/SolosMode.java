package net.cytonic.cytonicBedwars.modes.solos;

import net.cytonic.cytonicBedwars.data.objects.Team;
import net.cytonic.cytonicBedwars.interfaces.BedwarsMode;
import net.cytonic.cytosis.events.npcs.NpcInteractEvent;
import net.cytonic.cytosis.logging.Logger;

import java.util.List;

public class SolosMode implements BedwarsMode {
    @Override
    public void start() {
        Logger.debug("Starting solos mode");
    }

    @Override
    public List<Team> getTeams() {
        return List.of();
    }

    @Override
    public int maxPlayers() {
        return 0;
    }

    @Override
    public int minPlayersStart1M() {
        return 0;
    }

    @Override
    public int minPlayerStart30S() {
        return 0;
    }

    @Override
    public int minPlayerStart10S() {
        return 0;
    }

    @Override
    public void onItemShopNpcClick(NpcInteractEvent event) {

    }
}
