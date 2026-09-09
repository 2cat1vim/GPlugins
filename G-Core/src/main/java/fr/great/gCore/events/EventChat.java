package fr.great.gCore.events;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataMute;
import fr.great.gCore.database.DataRole;
import fr.great.gCore.di.Context;
import fr.great.gCore.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.sql.SQLException;
import java.util.List;

import static fr.great.gCore.utils.Logger.sendError;

public class EventChat implements Listener {
    private final DataRole dr = Context.getInstance().getDataRole();
    private final ConfigManager cm = Context.getInstance().getConfigManager();
    private final DataMute dm = Context.getInstance().getDataMute();

    public boolean chatFilter(String msg) {
        List<String> list = cm.getMessageFilter();
        for (int i = 0; i < list.toArray().length; ++i) {
            if (msg.contains(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void sendMessageForEachRole(Player p, String msg) throws SQLException {
        ChatColor color;
        int n_role = dr.getRole(p);
        switch (n_role) {
            case 1: color = ChatColor.AQUA; break;
            case 2: color = ChatColor.YELLOW; break;
            case 3: color = ChatColor.GREEN; break;
            case 4: color = ChatColor.GOLD; break;
            case 5: color = ChatColor.RED; break;
            case 6: color = ChatColor.DARK_RED; break;
            default: color = ChatColor.GRAY; break;
        }
        String chatFormat = cm.getGeneralChatFormat();
        chatFormat = chatFormat.replace("<rank>", dr.getRoleByValue(n_role));
        chatFormat = chatFormat.replace("<player>", p.getName());
        chatFormat = chatFormat.replace("<rank_color>", color.toString());
        Bukkit.getServer().sendPlainMessage(chatFormat + msg);
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        try {
            if (dm.isMute(p)) {
                sendError("You are muted", p);
                return ;
            }
        } catch (SQLException ex) {
            sendError("SQL Error, contact dev", p);
            return ;
        }
        String msg = e.getMessage();
        if (chatFilter(msg)) {
            try {
                sendMessageForEachRole(p, msg);
            } catch (SQLException ex) {
                sendError("SQL Error, contact dev", p);
                return ;
            }
            return ;
        }
        sendError("Your message contains bad word", p);
    }
}
