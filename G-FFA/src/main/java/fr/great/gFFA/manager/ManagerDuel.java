package fr.great.gFFA.manager;

import fr.great.gCore.database.DataRole;
import fr.great.gCore.di.API;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.SQLException;
import java.util.*;

public class ManagerDuel {

    private DataRole dr = API.get().dataRole();

    private final HashMap<Player, Player> duelMap;
    private final Scoreboard scoreboard;

    public ManagerDuel() {
        this.duelMap = new HashMap<>();
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
    }

    public void createDuelTeamIfAbsent(Player a, Player b) {
        Team team = scoreboard.getTeam(a.getName() + b.getName());
        if (team != null) {
            return ;
        }
        team = scoreboard.getTeam(b.getName() + a.getName());
        if (team != null) {
            return ;
        }
        team = scoreboard.registerNewTeam(a.getName() + b.getName());
        for (Player p : Arrays.asList(a, b)) {
            team.addPlayer(p);
            p.setGlowing(true);
        }
        team.setAllowFriendlyFire(true);
        team.setPrefix("§7[§eIn Private Duel§7] ");
        team.setColor(ChatColor.RED);
    }

    public void removeDuelTeam(Player a, Player b) {
        Team team = scoreboard.getTeam(a.getName() + b.getName());
        if (team == null) {
            team = scoreboard.getTeam(b.getName() + a.getName());
        }
        if (team != null) {
            team.unregister();
        }
        for (Player p : Arrays.asList(a, b)) {
            int role;
            try {
                role = dr.getRole(p);
            } catch (SQLException e) {
                p.kick(Component.text("§cFailed to retrieve role after death"));
                continue;
            }
            dr.setNameTagColor(p, dr.getNameTagColor(p, role), role);
            p.setGlowing(false);
        }
    }

    public void addDuel(Player key, Player value) {
        createDuelTeamIfAbsent(key, value);
        duelMap.putIfAbsent(key, value);
    }

    public void eraseDuel(Player key, Player value) {
        removeDuelTeam(key, value);
        if (!duelMap.remove(key, value)) {
            duelMap.remove(value, key);
        }
    }

    public boolean hasDuel(Player key, Player value) {
        return (duelMap.get(key) == value || duelMap.get(value) == key);
    }

    public Player getOpponent(Player key) {
        if (duelMap.containsKey(key)) {
            return duelMap.get(key);
        }
        if (duelMap.containsValue(key)) {
            for (Map.Entry<Player, Player> entry : duelMap.entrySet()) {
                if (entry.getValue() == key) {
                    return (entry.getKey());
                }
            }
        }
        return null;
    }

    public HashMap<Player, Player> getDuelMap() { return duelMap; }
}
