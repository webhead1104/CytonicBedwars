package net.cytonic.cytonicBedwars.managers;

import net.cytonic.cytonicBedwars.CytonicBedWars;
import net.cytonic.cytosis.Cytosis;
import net.cytonic.cytosis.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseManager {

    public void createTables() {
        createStatsTable();
    }

    private void createStatsTable() {
        PreparedStatement ps = Cytosis.getDatabaseManager().getMysqlDatabase().prepare("CREATE TABLE IF NOT EXISTS bedwars_stats (uuid VARCHAR(36), kills INT, deaths INT, finalKills INT, bedsBroken INT, damageDealt DOUBLE, damageTaken DOUBLE, PRIMARY KEY (uuid))");
        Cytosis.getDatabaseManager().getMysqlDatabase().update(ps);
    }


    //fixme Use a batch for this, (avoids bombarding our database with 16 requests in the same millisecond)
    public void saveStats() {
        CytonicBedWars.getGameManager().getStatsManager().getStats().forEach((uuid, kills) -> {
            try {
                PreparedStatement ps = Cytosis.getDatabaseManager().getMysqlDatabase().prepare("INSERT INTO bedwars_stats (uuid, kills, deaths, finalKills, bedsBroken, damageDealt, damageTaken) VALUES (?, ?, ?, ?, ?, ?, ?)");
                ps.setString(1, uuid.toString());
                ps.setInt(2, kills.getKills());
                ps.setInt(3, kills.getDeaths());
                ps.setInt(4, kills.getFinalKills());
                ps.setInt(5, kills.getBedsBroken());
                ps.setDouble(6, kills.getDamageDealt());
                ps.setDouble(7, kills.getDamageTaken());
                Cytosis.getDatabaseManager().getMysqlDatabase().update(ps);
            } catch (SQLException e) {
                Logger.error("Failed to save stats!", e);
            }
        });
    }
}