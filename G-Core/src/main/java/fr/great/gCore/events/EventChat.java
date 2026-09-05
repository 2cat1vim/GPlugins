package fr.great.gCore.events;

import fr.great.gCore.database.DataRole;
import fr.great.gCore.Utils.Definitions.BasicDef;
import fr.great.gCore.Utils.Functions.Global.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;
import java.util.List;

public class EventChat implements Listener {
    private final DataRole role;

    public EventChat(DataRole role) {
        this.role = role;
    }

    public boolean chatFilter(String msg) {
        List<String> list = BasicDef.MSGFILTER;
        for (int i = 0; i < list.toArray().length; ++i) {
            if (msg.contains(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void sendMessageForEachRole(Player p, String msg) throws SQLException {
        ChatColor color;
        int n_role = role.getRole(p);
        switch (n_role) {
            case 1: color = ChatColor.AQUA; break;
            case 2: color = ChatColor.YELLOW; break;
            case 3: color = ChatColor.GREEN; break;
            case 4: color = ChatColor.GOLD; break;
            case 5: color = ChatColor.RED; break;
            case 6: color = ChatColor.DARK_RED; break;
            default: color = ChatColor.GRAY; break;
        }
        String newFormat = color + p.getName() + BasicDef.RESET + " : " + ChatColor.GRAY + msg;
        Bukkit.getServer().sendPlainMessage(newFormat);
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        String msg = e.getMessage();
        if (chatFilter(msg)) {
            try {
                sendMessageForEachRole(p, msg);
            } catch (SQLException ex) {
                Error.sendError("SQL Error", p);
                return ;
            }
            return ;
        }
        Error.sendError("Your message contains bad word", p);
    }
}
