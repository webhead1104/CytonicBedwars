package net.cytonic.cytonicBedwars;

import lombok.Getter;
import net.cytonic.cytonicBedwars.commands.DebugCommand;
import net.cytonic.cytonicBedwars.commands.ItemCommand;
import net.cytonic.cytonicBedwars.data.enums.ModeType;
import net.cytonic.cytonicBedwars.interfaces.BedwarsMode;
import net.cytonic.cytonicBedwars.managers.GameManager;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;
import net.cytonic.cytosis.plugins.CytosisPlugin;

import java.lang.reflect.InvocationTargetException;

@Getter
public final class CytonicBedWars implements CytosisPlugin {

    public static final String version = "0.1";
    @Getter
    private static GameManager gameManager;

    @Override
    public void initialize() {
        Logger.info("Initializing CytonicBedWars");
        ModeType modeType = ModeType.SOLOS;
        try {
            Class<?> clazz = Class.forName(modeType.getClazz());
            if (BedwarsMode.class.isAssignableFrom(clazz)) {
                BedwarsMode mode = (BedwarsMode) clazz.getDeclaredConstructor().newInstance();
                mode.start();
                Logger.debug("after start");
            } else {
                throw new IllegalArgumentException("Class doesn't implement BedwarsMode");
            }
        } catch (ClassNotFoundException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

//        String worldName = System.getenv("WORLD_NAME");
//        String worldType = System.getenv("WORLD_TYPE");
//        String gameType = System.getenv("GAME_TYPE");
//        if (worldName == null || worldType == null || gameType == null) {
//            Logger.error("World name or type or game type is not set!");
//            MinecraftServer.stopCleanly();
//            return;
//        }
//        Cytosis.setServerGroup(new ServerGroup("bedwars", gameType, false));
//        Cytosis.getInstanceManager().getExtraData(worldName, worldType).whenComplete((extraData, throwable) -> {
//            if (throwable != null) {
//                Logger.error("error", throwable);
//                return;
//            }
//            Config.importConfig(extraData);
//            Logger.info("Loading game manager");
//            gameManager = new GameManager();
//            gameManager.setup();
//            Logger.info("Registering commands");
//            registerCommands();
//            Logger.info("Registering listeners");
//        });
    }

    @Override
    public void shutdown() {
//        gameManager.cleanup();
    }

    private void registerCommands() {
        Cytosis.getCommandManager().register(new DebugCommand());
        Cytosis.getCommandManager().register(new ItemCommand());
    }
}
