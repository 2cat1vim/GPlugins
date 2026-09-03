package fr.great.gCore.utils.Definitions;

import fr.great.gCore.GCore;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BasicDef {
    public static final ChatColor RESET = ChatColor.WHITE;
    public static final String PREFIX = ChatColor.AQUA + "[GCORE]: " + RESET;
    public static final JavaPlugin PLUGIN = JavaPlugin.getPlugin(GCore.class);
    public static final FileConfiguration CONFIG = PLUGIN.getConfig();
    public static final String WSTART = CONFIG.getString("welcomeMessageStart");
    public static final String WEND = CONFIG.getString("welcomeMessageEnd");
}
