package fr.great.gCore.Utils.Functions.Global;

import fr.great.gCore.Utils.Definitions.BasicDef;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class Success {
    public static void sendSuccess(String s, Player p) {
        p.sendMessage(BasicDef.PREFIX + ChatColor.GREEN + s);
    }
    public static void sendSuccessServer(String s, JavaPlugin plugin) {
        plugin.getLogger().info(Color.GREEN + s);
    }
}
