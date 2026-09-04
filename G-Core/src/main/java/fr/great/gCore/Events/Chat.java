package fr.great.gCore.Events;

import fr.great.gCore.Utils.Definitions.BasicDef;
import fr.great.gCore.Utils.Functions.Global.Error;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class Chat implements Listener {
    public boolean chatFilter(String msg) {
        List<String> list = BasicDef.MSGFILTER;
        for (int i = 0; i < list.toArray().length; ++i) {
            if (msg.contains(list.get(i))) {
                return false;
            }
        }
        return true;
    }

    @EventHandler
    public void onChatMessage(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        Player p = e.getPlayer();
        String m = e.getMessage();
        if (chatFilter(m)) {
            String newFormat = ChatColor.AQUA + p.getName() + BasicDef.RESET + " : " + ChatColor.GRAY + m;
            Bukkit.getServer().sendPlainMessage(newFormat);
            return ;
        }
        Error.sendError("Your message contains bad word", p);
    }
}
