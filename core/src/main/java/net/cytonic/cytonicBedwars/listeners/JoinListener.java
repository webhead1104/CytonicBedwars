package net.cytonic.cytonicBedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicBedwars.Config;
import net.cytonic.cytonicBedwars.CytonicBedWars;
import net.cytonic.cytonicBedwars.data.enums.GameState;
import net.cytonic.cytonicBedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.player.CytosisPlayer;
import net.minestom.server.entity.GameMode;
import net.minestom.server.event.player.AsyncPlayerConfigurationEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;

@NoArgsConstructor
@SuppressWarnings("unused")
public class JoinListener {

    @Listener
    public void onJoin(AsyncPlayerConfigurationEvent event) {
        CytosisPlayer player = (CytosisPlayer) event.getPlayer();
        event.setSpawningInstance(Cytosis.getDefaultInstance());
        player.setRespawnPoint(Config.spawnPlatformCenter.add(0, 1, 0));
    }

    @Listener
    public void onJoin(PlayerSpawnEvent event) {
        if (!CytonicBedWars.getGameManager().STARTED) {
            if (Cytosis.getOnlinePlayers().size() >= Config.minPlayers) {
                CytonicBedWars.getGameManager().setGameState(GameState.STARTING);
                CytonicBedWars.getGameManager().setWaitingRunnable(new WaitingRunnable());
            }
        }
        if (CytonicBedWars.getGameManager().STARTED) {
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                event.getPlayer().setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}
