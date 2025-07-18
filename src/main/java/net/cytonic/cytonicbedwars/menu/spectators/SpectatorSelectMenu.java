package net.cytonic.cytonicbedwars.menu.spectators;

import eu.koboo.minestom.stomui.api.PlayerView;
import eu.koboo.minestom.stomui.api.ViewBuilder;
import eu.koboo.minestom.stomui.api.ViewType;
import eu.koboo.minestom.stomui.api.component.ViewProvider;
import eu.koboo.minestom.stomui.api.item.PrebuiltItem;
import eu.koboo.minestom.stomui.api.item.ViewItem;
import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.utils.Msg;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.minestom.server.component.DataComponents;
import net.minestom.server.entity.Player;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;
import net.minestom.server.item.component.CustomData;
import net.minestom.server.item.component.HeadProfile;
import net.minestom.server.item.component.TooltipDisplay;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class SpectatorSelectMenu extends ViewProvider {

    public SpectatorSelectMenu() {
        super(Cytosis.VIEW_REGISTRY, ViewBuilder.of(ViewType.SIZE_3_X_9).title(Msg.mm("Spectate a player")));
    }

    @Override
    public void onOpen(@NotNull PlayerView view, @NotNull Player p) {
        AtomicInteger i = new AtomicInteger(0);
        CytonicBedWars.getGameManager().getTeams().forEach(team -> team.getPlayers().forEach(player -> {
            ItemStack stack = ItemStack.builder(Material.PLAYER_HEAD)
                    .set(DataComponents.PROFILE, new HeadProfile(Objects.requireNonNull(player.getSkin())))
                    .set(DataComponents.TOOLTIP_DISPLAY, new TooltipDisplay(false, Set.of(DataComponents.EQUIPPABLE, DataComponents.UNBREAKABLE)))
                    .set(DataComponents.ITEM_NAME, Msg.green(player.getUsername()))
                    .set(DataComponents.LORE, List.of((Msg.grey("Click to teleport to %s", player.getUsername()))))
                    .set(DataComponents.CUSTOM_DATA, new CustomData(CompoundBinaryTag.builder().putString("uuid", player.getUuid().toString()).build()))
                    .build();
            PrebuiltItem item = PrebuiltItem.of(stack, action -> {
                action.getEvent().setCancelled(true);
                if (Cytosis.getPlayer(UUID.fromString(Objects.requireNonNull(action.getEvent().getClickedItem().get(DataComponents.CUSTOM_DATA)).nbt().getString("uuid"))).isEmpty()) {
                    player.sendMessage(Msg.whoops("That player is not online."));
                    open(player);
                } else {
                    Player target = Cytosis.getPlayer(Objects.requireNonNull(action.getEvent().getClickedItem().get(DataComponents.CUSTOM_DATA)).nbt().getString("uuid")).orElseThrow();
                    player.sendMessage(Msg.success("Teleported you to " + target.getUsername() + "!"));
                    player.teleport(target.getPosition());
                }
            });
            ViewItem.bySlot(view, i.getAndIncrement()).applyPrebuilt(item);
        }));
    }
}
