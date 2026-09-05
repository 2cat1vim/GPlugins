package fr.great.gCore.database;

import org.bukkit.entity.Player;

import java.sql.*;

public class DataManager {
    public static Connection connection = null;
    /* Constructor */
    public DataManager(String path) throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + path);
        try (Statement statement = connection.createStatement()) {
            statement.execute("CREATE TABLE IF NOT EXISTS players (" +
                    "uuid TEXT PRIMARY KEY, " +
                    "role INT NOT NULL DEFAULT 0)");
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
    public void addPlayer(Player p) throws SQLException {
        if (!isPlayerExist(p)) {
            try (PreparedStatement preparedStatement = connection.prepareStatement
                    ("INSERT INTO players (uuid) VALUES (?)")
            ) {
                preparedStatement.setString(1, p.getUniqueId().toString());
                preparedStatement.executeUpdate();
            }
        }
    }
    public boolean isPlayerExist(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement
                ("SELECT * FROM players WHERE uuid = ?")
        ) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            return preparedStatement.executeQuery().next();
        }
    }
}
