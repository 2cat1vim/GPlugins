package fr.great.gCore.Utils.Functions.Global;

import fr.great.gCore.Utils.Definitions.BasicDef;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.awt.*;

public class Error {
    public static void sendError(String s, Player p) {
        p.sendMessage(BasicDef.PREFIX + ChatColor.RED + s);
    }
    public static void sendErrorServer(String s) {
        System.out.println(Color.RED + s);
    }
}
