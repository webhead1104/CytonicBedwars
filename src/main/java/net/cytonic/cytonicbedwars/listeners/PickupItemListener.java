package net.cytonic.cytonicbedwars.listeners;

import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.item.ItemStack;
import net.minestom.server.item.Material;

import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.utils.Items;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.events.api.Listener;

@SuppressWarnings("unused")
public class PickupItemListener {

    @Listener
    public static void onPickup(PickupItemEvent event) {
        if (!(event.getEntity() instanceof BedwarsPlayer player)) return;
        if (Cytosis.CONTEXT.getComponent(GameManager.class).getSpectators().contains(player.getUuid())) {
            event.setCancelled(true);
            return;
        }
        final ItemStack item = event.getItemEntity().getItemStack();
        if (item.hasTag(Items.NAMESPACE) && item.getTag(Items.NAMESPACE).contains("SWORD")) {
            player.removeItems(Material.WOODEN_SWORD, 1);
            player.getInventory().addItemStack(item);
            return;
        }
        final ItemStack itemStack = event.getItemEntity().getItemStack();
        event.setCancelled(!player.getInventory().addItemStack(itemStack));
    }
}
