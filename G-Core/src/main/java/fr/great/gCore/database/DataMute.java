package fr.great.gCore.database;

import fr.great.gCore.di.Context;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataMute {
    private final DataManager dm = Context.getInstance().getDataManager();

    public void setMute(Player p, long muteTime) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("UPDATE players SET mute_time = ?, mute_date = ? WHERE uuid = ?")
        ) {
            long muteDate = System.currentTimeMillis();
            preparedStatement.setLong(1, muteTime);
            preparedStatement.setLong(2, muteDate);
            preparedStatement.setString(3, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    public long getMuteTime(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("SELECT mute_time FROM players WHERE uuid = ?")
        ) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("mute_time");
                }
                return 0;
            }
        }
    }

    public long getMuteDate(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("SELECT mute_date FROM players WHERE uuid = ?")
        ) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getLong("mute_date");
                }
                return 0;
            }
        }
    }

    // True if player is mute
    // -1 if ban is permanent
    public boolean isMute(Player p) throws SQLException {
        long mute_time = getMuteTime(p);
        if (mute_time == -1) {
            return true;
        }
        long mute_date = getMuteDate(p);
        long curr_date = System.currentTimeMillis();
        return (curr_date < (mute_time + mute_date));
    }
}
