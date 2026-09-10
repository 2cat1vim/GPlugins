package fr.great.gCore.database;

import fr.great.gCore.di.Context;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataRestriction {
    private final DataManager dm = Context.getInstance().getDataManager();

    public void setRestriction(Player p, String flag, long muteTime) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("UPDATE players SET " + flag + "_time = ?, " + flag + "_date = ? WHERE uuid = ?")
        ) {
            long muteDate = System.currentTimeMillis();
            preparedStatement.setLong(1, muteTime);
            preparedStatement.setLong(2, muteDate);
            preparedStatement.setString(3, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    // flag = mute or ban
    public long getRestrictionTime(Player p, String flag) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("SELECT " + flag + "_time" + " FROM players WHERE uuid = ?")
        ) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(flag + "_time");
                }
                return 0;
            }
        }
    }

    public long getRestrictionDate(Player p, String flag) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("SELECT " + flag + "_date" + " FROM players WHERE uuid = ?")
        ) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong(flag + "_date");
                }
                return 0;
            }
        }
    }

    // True if player is mute
    // -1 if ban is permanent
    public boolean isRestrict(Player p, String flag) throws SQLException {
        long restrictionTime = getRestrictionTime(p, flag);
        // Forever Restriction
        if (restrictionTime == -1) {
            return true;
        }
        long restrictionDate = getRestrictionDate(p, flag);
        long currentDate = System.currentTimeMillis();
        return (currentDate < (restrictionTime + restrictionDate));
    }
}
