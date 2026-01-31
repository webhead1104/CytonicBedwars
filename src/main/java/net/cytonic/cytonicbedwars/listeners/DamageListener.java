package net.cytonic.cytonicbedwars.listeners;

import io.github.togar2.pvp.events.FinalDamageEvent;
import lombok.NoArgsConstructor;

import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;

@NoArgsConstructor
@SuppressWarnings("unused")
public class DamageListener {

    @Listener
    public void onDamage(FinalDamageEvent event) {
        if (!(event.getDamage().getAttacker() instanceof BedwarsPlayer player)) return;
        if (Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())) {
            event.setCancelled(true);
            return;
        }
        if (!Cytosis.CONTEXT.getComponent(GameManager.class).STARTED) {
            event.setCancelled(true);
            return;
        }

        if (event.getDamage().getAttacker() instanceof BedwarsPlayer damager) {
            damager.getStats().addDamageDealt(event.getDamage().getAmount());
        }
        player.getStats().addDamageTaken(event.getDamage().getAmount());
    }
}
