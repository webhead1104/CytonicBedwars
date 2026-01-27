package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.minestom.server.entity.GameMode;
import net.minestom.server.entity.ItemEntity;
import net.minestom.server.event.player.PlayerBlockBreakEvent;
import net.minestom.server.instance.InstanceContainer;
import net.minestom.server.item.ItemStack;

import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.managers.WorldManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.utils.Msg;
import net.cytonic.cytosis.utils.PosSerializer;

import static net.cytonic.cytonicbedwars.listeners.BlockPlaceListener.BW_ID;
import static net.cytonic.cytonicbedwars.listeners.BlockPlaceListener.PLACED_BY_PLAYER_TAG;

@NoArgsConstructor
public class BlockBreakListener {

    @Listener
    public void onBlockBreak(PlayerBlockBreakEvent event) {
        if (!(event.getPlayer() instanceof BedwarsPlayer player)) return;
        if (player.getGameMode() == GameMode.CREATIVE) return;
        if (Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())) {
            player.sendMessage(Msg.whoops("You cannot do this as a spectator!"));
            event.setCancelled(true);
            return;
        }
        if (event.getBlock().name().contains("bed")) {
            if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).isPresent()) {
                if (Cytosis.CONTEXT.getComponent(GameManager.class).getPlayerTeam(player).get().getBedType().key()
                    .equals(event.getBlock().key())) {
                    event.setCancelled(true);
                    player.sendMessage(Msg.whoops("You cannot break your own bed!"));
                    return;
                }
            }
            Cytosis.CONTEXT.getComponent(GameManager.class).getTeams().forEach(team -> {
                if (event.getBlock().name().equals(team.getBedType().name())) {
                    Cytosis.CONTEXT.getComponent(GameManager.class).breakBed(player, team);
                    Cytosis.CONTEXT.getComponent(WorldManager.class).breakBed(team);
                }
            });
            return;
        }
        if (!event.getBlock().hasTag(PLACED_BY_PLAYER_TAG) || !event.getBlock().getTag(PLACED_BY_PLAYER_TAG)) {
            player.sendMessage(Msg.whoops("You can only break blocks placed by players!"));
            event.setCancelled(true);
            return;
        }
        if (!event.getBlock().hasTag(BW_ID)) {
            Logger.error("Somehow block %s at %s doesn't have bw_id tag!", event.getBlock().key().asString(),
                PosSerializer.serialize(event.getBlockPosition().asPos()));
            return;
        }
        ItemStack stack = Items.get(event.getBlock().getTag(BW_ID));
        ItemEntity item = new ItemEntity(stack);
        item.setInstance(Cytosis.CONTEXT.getComponent(InstanceContainer.class), event.getBlockPosition());
        item.spawn();
    }
}
