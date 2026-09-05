package fr.great.gCore.Utils.Definitions;

import fr.great.gCore.GCore;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class BasicDef {
    public static final ChatColor RESET = ChatColor.WHITE;
    public static final String PREFIX = ChatColor.AQUA + "[GCORE]: " + RESET;
    public static final JavaPlugin PLUGIN = JavaPlugin.getPlugin(GCore.class);
    public static final FileConfiguration CONFIG = PLUGIN.getConfig();
    public static final String MSGWELCOME = CONFIG.getString("welcomeMessage");
    public static final List<String> MSGFILTER = CONFIG.getStringList("messageFilter");
    public static final List<String> ROLES = CONFIG.getStringList("playerRoles");
    public static final String MSGDEATHTITLE = CONFIG.getString("deathTitleMessage");
    public static final String MSGDEATHSUBTITLE = CONFIG.getString("deathSubTitleMessage");
    public static final String KICKMESSAGE = CONFIG.getString("kickMessage");
    public static final String BROADCASTKICKMESSAGE = CONFIG.getString("broadcastKickMessage");
}
