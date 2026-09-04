package fr.great.gCore.Database;

import fr.great.gCore.Utils.Definitions.BasicDef;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Role {
    private final Manager manager;
    public Role(Manager manager) {
        this.manager = manager;
    }

    public List<String> Roles = BasicDef.CONFIG.getStringList("playerRoles");

    public List<String> getRoles() {
        return Roles;
    }

    public String getRoleByValue(int n) {
        return Roles.get(n);
    }

    public int getValueByRole(String r) {
        for (int i = 0; i < Roles.size(); ++i) {
            if (Roles.get(i).equals(r)) {
                return i;
            }
        }
        return 0;
    }

    public void setRole(Player p, int newRole) throws SQLException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement
                ("UPDATE players SET role = ? WHERE uuid = ?")
        ) {
            preparedStatement.setInt(1, newRole);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    public int getRole(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = manager.getConnection().prepareStatement
                ("SELECT role FROM players WHERE uuid = ?")
        ) {
            preparedStatement.setString(1, p.getUniqueId().toString());
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("role");
                }
                return 0;
            }
        }
    }
}
