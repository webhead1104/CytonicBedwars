package net.cytonic.cytonicbedwars.listeners;

import lombok.NoArgsConstructor;
import net.cytonic.cytosis.events.api.Listener;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.player.PlayerPreEatEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import java.time.Duration;

@NoArgsConstructor
@SuppressWarnings("unused")
public class PotionDrinkListener {

    @Listener
    public static void onEat(PlayerPreEatEvent e) {
        if (e.getItemStack().material() == Material.POTION || e.getItemStack().material() == Material.GLASS_BOTTLE) {
            MinecraftServer.getSchedulerManager().buildTask(() -> e.getPlayer().setItemInHand(e.getHand(), ItemStack.AIR)).delay(Duration.ofMillis(100));
        }
    }
}
