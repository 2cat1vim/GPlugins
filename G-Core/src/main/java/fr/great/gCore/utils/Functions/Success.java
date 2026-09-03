package fr.great.gCore.utils.Functions;

import fr.great.gCore.utils.Definitions.BasicDef;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Success {
    public static void sendSuccess(String s, Player p) {
        p.sendMessage(BasicDef.PREFIX + ChatColor.GREEN + s);
    }
}
