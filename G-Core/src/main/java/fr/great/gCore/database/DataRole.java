package fr.great.gCore.database;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Set;

public class DataRole {
    private final DataManager dm = Context.getInstance().getDataManager();
    private final ConfigManager cm = Context.getInstance().getConfigManager();

    public List<String> Roles = cm.getPlayerRoles();

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

    public void clearTeams() {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        for (Team team : scoreboard.getTeams()) {
            team.unregister();
        }
    }

    public void setNameTagColor(Player p, ChatColor color, int n_role) {
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        Team team = scoreboard.getTeam(String.valueOf(n_role));
        Team oldTeam = scoreboard.getEntryTeam(p.getName());
        if (oldTeam != null) {
            oldTeam.removeEntry(p.getName());
        }
        if (team == null) {
            team = scoreboard.registerNewTeam(String.valueOf(n_role));
            String format = cm.getPlayerNameTag();
            format = format.replace("<rank>", getRoleByValue(n_role));
            format = format.replace("<rank_color>", color.toString());
            team.setColor(color);
            team.setPrefix(format);
        }
        team.addEntry(p.getName());
    }

    public ChatColor getNameTagColor(Player p, int n_role) {
        ChatColor color;
        switch (n_role) {
            case 1:
                color = ChatColor.AQUA;
                break;
            case 2:
                color = ChatColor.YELLOW;
                break;
            case 3:
                color = ChatColor.GREEN;
                break;
            case 4:
                color = ChatColor.GOLD;
                break;
            case 5:
                color = ChatColor.RED;
                break;
            case 6:
                color = ChatColor.DARK_RED;
                break;
            default:
                color = ChatColor.GRAY;
                break;
        }
        return color;
    }

    public void setRole(Player p, int newRole) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
                ("UPDATE players SET role = ? WHERE uuid = ?")
        ) {
            preparedStatement.setInt(1, newRole);
            preparedStatement.setString(2, p.getUniqueId().toString());
            preparedStatement.executeUpdate();
        }
    }

    public int getRole(Player p) throws SQLException {
        try (PreparedStatement preparedStatement = dm.getConnection().prepareStatement
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
