package fr.great.gCore.utils;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.di.Context;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.awt.*;

public class Logger {
    private static final ConfigManager cm = Context.getInstance().getConfigManager();
    public static void sendSuccess(String s, Player p) {
        p.sendMessage(cm.getPrefixMessage() + ChatColor.GREEN + s);
    }
    public static void sendError(String s, Player p) {
        p.sendMessage(cm.getPrefixMessage() + ChatColor.RED + s);
    }
    public static void sendSuccessServer(String s, JavaPlugin plugin) {
        plugin.getLogger().info(Color.GREEN + s);
    }
    public static void sendErrorServer(String s, JavaPlugin plugin) {
        plugin.getLogger().info(Color.RED + s);
    }
}
