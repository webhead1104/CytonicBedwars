package net.cytonic.cytonicbedwars.commands;

import net.cytonic.cytonicbedwars.CytonicBedWars;
import net.cytonic.cytonicbedwars.data.enums.GameState;
import net.cytonic.cytonicbedwars.menu.itemShop.BlocksShopMenu;
import net.cytonic.cytonicbedwars.player.BedwarsPlayer;
import net.cytonic.cytonicbedwars.runnables.WaitingRunnable;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.commands.utils.CommandUtils;
import net.cytonic.cytosis.commands.utils.CytosisCommand;
import net.cytonic.cytosis.utils.Msg;
import net.minestom.server.command.builder.arguments.ArgumentType;
import net.minestom.server.command.builder.suggestion.SuggestionEntry;

public class DebugCommand extends CytosisCommand {

    public DebugCommand() {
        super("debug");

        setCondition(CommandUtils.IS_ADMIN);
        setDefaultExecutor((sender, context) -> sender.sendMessage(Msg.whoops("You must specify a command!")));

        var debugArgument = ArgumentType.Word("debug").from("start", "forceStart", "end", "cleanup", "listteams", "freeze", "f", "itemshop", "teaminfo");
        debugArgument.setCallback((sender, exception) -> sender.sendMessage(Msg.whoops("The command " + exception.getInput() + " is invalid!")));
        debugArgument.setSuggestionCallback((commandSender, commandContext, suggestion) -> {
            suggestion.addEntry(new SuggestionEntry("start", Msg.green("Starts the game!")));
            suggestion.addEntry(new SuggestionEntry("forceStart", Msg.green("Force starts the game!")));
            suggestion.addEntry(new SuggestionEntry("end", Msg.green("Ends the game!")));
            suggestion.addEntry(new SuggestionEntry("cleanup", Msg.green("Cleans up the game!")));
            suggestion.addEntry(new SuggestionEntry("listteams", Msg.green("Lists all the teams!")));
            suggestion.addEntry(new SuggestionEntry("freeze", Msg.green("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("f", Msg.green("Freezes the game!")));
            suggestion.addEntry(new SuggestionEntry("itemshop", Msg.green("Opens the item shop!")));
            suggestion.addEntry(new SuggestionEntry("teaminfo", Msg.green("Shows information about the teams!")));
        });

        addSyntax((sender, context) -> {
            if (sender instanceof BedwarsPlayer player) {
                String command = context.get(debugArgument);

                switch (command.toLowerCase()) {
                    case "start" -> {
                        if (CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.red("The game has already been started! Use '/debug stop' to end it!"));
                            player.sendMessage(Msg.red("Starting the game anyway!"));
                        }
                        CytonicBedWars.getGameManager().setGameState(GameState.STARTING);
                        CytonicBedWars.getGameManager().setWaitingRunnable(new WaitingRunnable());
                    }
                    case "forcestart" -> {
                        if (CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.red("The game has already been started! Use '/debug stop' to end it!"));
                        }
                        if (CytonicBedWars.getGameManager().getWaitingRunnable() != null) {
                            CytonicBedWars.getGameManager().getWaitingRunnable().stop();
                            CytonicBedWars.getGameManager().setWaitingRunnable(null);
                        }
                        CytonicBedWars.getGameManager().start();
                    }
                    case "end" -> {
                        player.sendMessage(Msg.green("Ending game!"));
                        CytonicBedWars.getGameManager().end();
                    }
                    case "cleanup" -> {
                        player.sendMessage(Msg.green("Cleaning up game!"));
                        CytonicBedWars.getGameManager().cleanup();
                    }
                    case "listteams" ->
                            CytonicBedWars.getGameManager().getTeams().forEach(team -> player.sendMessage(Msg.mm(team.getPrefix() + team.getDisplayName())));
                    case "freeze", "f" -> {
                        if (CytonicBedWars.getGameManager().getGameState() != GameState.FROZEN) {
                            Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(Msg.yellow("The game is now <aqua><bold>FROZEN<reset><yellow>!")));
                            CytonicBedWars.getGameManager().freeze();
                        } else {
                            Cytosis.getOnlinePlayers().forEach((player1) -> player1.sendMessage(Msg.yellow("The game is now <gold><bold>THAWED<reset><yellow>!")));
                            CytonicBedWars.getGameManager().thaw();
                        }
                    }
                    case "itemshop" -> {
                        if (!CytonicBedWars.getGameManager().STARTED) {
                            player.sendMessage(Msg.redSplash("!! WARNING !!", "The game has not been started. Some shop pages may not work!"));
                        }
                        new BlocksShopMenu().open(player);
                    }
                    case "teaminfo" -> CytonicBedWars.getGameManager().getTeams().forEach(team -> {
                        player.sendMessage(Msg.mm("<%s><b>Team:</b> %s", team.getColor(), team.getName()));
                        player.sendMessage(Msg.mm("Alive: %s", team.isAlive()));
                        player.sendMessage(Msg.mm("Bed: %s", team.hasBed()));
                        player.sendMessage(Msg.mm("MCTeam: %s", team.getMcTeam().getTeamName()));
                        player.sendMessage(Msg.mm("Players:"));
                        team.getPlayers().forEach(teamPlayer -> {
                            player.sendMessage(Msg.mm(" Name: %s", teamPlayer.getUsername()));
                            player.sendMessage(Msg.mm(" Armor Level: %s", teamPlayer.getArmorLevel().name()));
                            player.sendMessage(Msg.mm(" Axe Level: %s", teamPlayer.getAxeLevel().name()));
                            player.sendMessage(Msg.mm(" Pickaxe Level: %s", teamPlayer.getPickaxeLevel().name()));
                            player.sendMessage(Msg.mm(" Shears: %s", teamPlayer.hasShears()));
                            player.sendMessage(Msg.mm(" Alive: %s", teamPlayer.isAlive()));
                            player.sendMessage(Msg.mm(" Respawning: %s", teamPlayer.isRespawning()));
                        });
                    });
                }
            }
        }, debugArgument);
    }
}
