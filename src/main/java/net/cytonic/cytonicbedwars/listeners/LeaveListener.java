package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytonicbedwars.Config;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.entity.Player;
import net.minestom.server.event.player.PlayerDisconnectEvent;

@NoArgsConstructor
@SuppressWarnings("unused")
public class LeaveListener {

    @Listener
    public void onLeave(PlayerDisconnectEvent event) {
        if (CytonicBedWars.getGameManager().STARTED) {
            if (CytonicBedWars.getGameManager().getPlayerTeam(event.getPlayer().getUuid()).isEmpty()) {
                // they are a spectator
                Player player = event.getPlayer();
                player.clearEffects();
                CytonicBedWars.getGameManager().getSpectators().remove(player.getUuid());
            }
        } else {
            if (Cytosis.getOnlinePlayers().size() < Config.minPlayers) {
                if (CytonicBedWars.getGameManager().getWaitingRunnable() != null) {
                    CytonicBedWars.getGameManager().getWaitingRunnable().stop();
                    CytonicBedWars.getGameManager().setWaitingRunnable(null);
                    CytonicBedWars.getGameManager().setGameState(GameState.WAITING);
                    Cytosis.getOnlinePlayers().forEach(player -> player.sendMessage(Msg.redSplash("START CANCELLED!", "There are not enough players to start the game!")));
                }
            }
        }
    }
}