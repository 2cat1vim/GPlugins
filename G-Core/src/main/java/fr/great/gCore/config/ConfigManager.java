package fr.great.gCore.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

import static fr.great.gCore.Utils.Functions.Global.Error.sendErrorServer;

public class ConfigManager {

    private final JavaPlugin plugin;
    private String prefixMessage;
    private String welcomeMessage;
    private String deathTitle;
    private String deathSubTitle;
    private String kickMessage;
    private String broadcastKickMessage;
    private List<String> playerRoles;
    private List<String> messageFilter;

    private final String isMissing = " is missing in config.yml";

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.saveDefaultConfig();
        FileConfiguration file = plugin.getConfig();
        loadConfigList(file);
    }

    public String getStringOrDefault(FileConfiguration file, String name, String def) {
        String temp = file.getString(name);
        if (temp == null) {
            sendErrorServer(name + isMissing, plugin);
            return def;
        }
        return temp;
    }

    public List<String> getStringListOrDefault(FileConfiguration file, String name, List<String> def) {
        List<String> temp = file.getStringList(name);
        if (temp.isEmpty()) {
            sendErrorServer(name + isMissing, plugin);
            return def;
        }
        return temp;
    }

    public void loadConfigList(FileConfiguration file) {
        prefixMessage = getStringOrDefault(file, "prefixMessage", "§7[§bGCore§7]: §f");
        welcomeMessage = getStringOrDefault(file, "welcomeMessage", "§aGreat to see you §7<player> §ahave fun!");
        deathTitle = getStringOrDefault(file, "deathTitleMessage", "§cYou died");
        deathSubTitle = getStringOrDefault(file, "deathSubTitleMessage", "§7be careful next time");
        kickMessage = getStringOrDefault(file, "kickMessage", "§cYou have been kicked");
        broadcastKickMessage = getStringOrDefault(file, "broadcastKickMessage", "§7<player> §chave been kicked for <reason>");
        playerRoles = getStringListOrDefault(file, "playerRoles", List.of("New", "Player", "Vip", "Helper", "Moderator", "Admin", "Owner"));
        messageFilter = getStringListOrDefault(file, "messageFilter", List.of("fuck", "bitch", "puta"));
    }

    public JavaPlugin getPlugin() { return plugin; }
    public String getPrefixMessage() { return prefixMessage; }
    public String getWelcomeMessage() { return welcomeMessage; }
    public String getDeathTitle() { return deathTitle; }
    public String getDeathSubTitle() { return deathSubTitle; }
    public String getKickMessage() { return kickMessage; }
    public String getBroadcastKickMessage() { return broadcastKickMessage; }
    public List<String> getPlayerRoles() { return playerRoles; }
    public List<String> getMessageFilter() { return messageFilter; }
}
