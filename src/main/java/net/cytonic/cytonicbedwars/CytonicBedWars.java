package net.cytonic.cytonicbedwars;

import io.github.togar2.pvp.events.EntityPreDeathEvent;
import io.github.togar2.pvp.events.FinalDamageEvent;
import lombok.Getter;
import net.cytonic.cytonicbedwars.commands.DebugCommand;
import net.cytonic.cytonicbedwars.commands.ItemCommand;
import net.cytonic.cytonicbedwars.listeners.*;
import net.cytonic.cytonicbedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.data.objects.ServerGroup;
import net.cytonic.cytosis.events.EventListener;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.plugins.CytosisPlugin;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.inventory.InventoryItemChangeEvent;
import net.minestom.server.event.inventory.InventoryPreClickEvent;
import net.minestom.server.event.item.ItemDropEvent;
import net.minestom.server.event.item.PickupItemEvent;
import net.minestom.server.event.player.*;

@Getter
public final class CytonicBedWars implements CytosisPlugin {

    public static final String version = "0.1";
    @Getter
    private static GameManager gameManager;

    @Override
    public void initialize() {
        Logger.info("Initializing CytonicBedWars");
        String worldName = System.getenv("WORLD_NAME");
        String worldType = System.getenv("WORLD_TYPE");
        String gameType = System.getenv("GAME_TYPE");
        if (worldName == null || worldType == null || gameType == null) {
            Logger.error("World name or type or game type is not set!");
            MinecraftServer.stopCleanly();
            return;
        }
        Cytosis.setServerGroup(new ServerGroup("bedwars", gameType, false));
        Cytosis.getInstanceManager().getExtraData(worldName, worldType).whenComplete((extraData, throwable) -> {
            if (throwable != null) {
                Logger.error("error", throwable);
                return;
            }
            CytonicBedwarsSettings.importConfig(extraData);
            Logger.info("Loading game manager");
            gameManager = new GameManager();
            gameManager.setup();
            Logger.info("Registering commands");
            registerCommands();
            Logger.info("Registering listeners");
            registerListeners();
        });
    }

    @Override
    public void shutdown() {
        gameManager.cleanup();
    }

    private void registerListeners() {
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockBreakEvent", false, 2, PlayerBlockBreakEvent.class, BlockBreakListener::onBlockBreak));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEatEvent", false, 2, PlayerPreEatEvent.class, (PotionDrinkListener::onEat)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:ArmorEquipEvent", false, 2, InventoryItemChangeEvent.class, (ArmorEquipListener::onArmorEquip)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:BlockPlaceEvent", false, 2, PlayerBlockPlaceEvent.class, (BlockPlaceListener::onBlockPlace)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:FinalDamageEvent", false, 2, FinalDamageEvent.class, (DamageListener::onDamage)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:DropItemEvent", false, -1, ItemDropEvent.class, (DropItemListener::onDrop)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:GamemodeChangeEvent", false, 2, PlayerGameModeChangeEvent.class, (GamemodeChangeListener::onGamemodeChange)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerBlockInteractEvent", false, 2, PlayerBlockInteractEvent.class, (InteractListener::onInteract)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerEntityInteractEvent", false, 2, PlayerEntityInteractEvent.class, (InteractListener::onInteract)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:InventoryPreClickEvent", false, 2, InventoryPreClickEvent.class, (InventoryClickListener::onInventoryClick)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:AsyncPlayerConfigurationEvent", true, 2, AsyncPlayerConfigurationEvent.class, (JoinListener::onJoin)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerSpawnEvent", false, 2, PlayerSpawnEvent.class, (JoinListener::onJoin)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerDisconnectEvent", false, 2, PlayerDisconnectEvent.class, (LeaveListener::onLeave)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PlayerMoveEvent", false, 2, PlayerMoveEvent.class, (MoveListener::onMove)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:EntityPreDeathEvent", false, 2, EntityPreDeathEvent.class, (DeathListener::onDeath)));
        Cytosis.getEventHandler().registerListener(new EventListener<>("bedwars:PickupItemEvent", false, 2, PickupItemEvent.class, (PickupItemListener::onPickup)));
    }

    private void registerCommands() {
        Cytosis.getCommandManager().register(new DebugCommand());
        Cytosis.getCommandManager().register(new ItemCommand());
    }
}
