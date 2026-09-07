package fr.great.gCore.config;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.List;

import static fr.great.gCore.utils.Logger.sendErrorServer;

public class ConfigManager {

    private final JavaPlugin plugin;
    private String prefixMessage;
    private String welcomeMessage;
    private String deathTitle;
    private String deathSubTitle;
    private String kickMessage;
    private String broadcastKickMessage;
    private String generalChatFormat;
    private String playerNameTag;
    private List<String> playerRoles;
    private List<String> messageFilter;

    private Location spawnLocation;

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

    public Location getLocationOrDefault(FileConfiguration config) {
        double x = config.getDouble("spawnPosition.x");
        double y = config.getDouble("spawnPosition.y");
        double z = config.getDouble("spawnPosition.z");
        float yaw = (float) config.getDouble("spawnPosition.yaw");
        float pitch = (float) config.getDouble("spawnPosition.pitch");
        World world = Bukkit.getWorld(config.getString("spawnPosition.world"));
        if (world == null) {
            sendErrorServer("Spawn position > World" + isMissing, plugin);
            world = Bukkit.getWorlds().get(0);
        }
        return new Location(world, x, y, z, yaw, pitch);
    }

    public void setSpawnLocation(FileConfiguration file, Location loc){
        file.set("spawnPosition.x", loc.getX());
        file.set("spawnPosition.y", loc.getY());
        file.set("spawnPosition.z", loc.getZ());
        file.set("spawnPosition.yaw", loc.getYaw());
        file.set("spawnPosition.pitch", loc.getPitch());
        file.set("spawnPosition.world", loc.getWorld());
        plugin.saveConfig();
    }

    public void loadConfigList(FileConfiguration file) {
        prefixMessage = getStringOrDefault(file, "prefixMessage", "§7[§bGCore§7]: §f");
        welcomeMessage = getStringOrDefault(file, "welcomeMessage", "§aGreat to see you §7<player> §ahave fun!");
        deathTitle = getStringOrDefault(file, "deathTitleMessage", "§cYou died");
        deathSubTitle = getStringOrDefault(file, "deathSubTitleMessage", "§7be careful next time");
        kickMessage = getStringOrDefault(file, "kickMessage", "§cYou have been kicked");
        broadcastKickMessage = getStringOrDefault(file, "broadcastKickMessage", "§7<player> §chave been kicked for <reason>");
        generalChatFormat = getStringOrDefault(file, "generalChatFormat", "§f[<rank_color><rank>§f] <rank_color><player>§f: §f");
        playerNameTag = getStringOrDefault(file, "playerNameTag", "§f[<rank_color><rank>§f] <rank_color>");
        playerRoles = getStringListOrDefault(file, "playerRoles", List.of("New", "Player", "Vip", "Helper", "Moderator", "Admin", "Owner"));
        messageFilter = getStringListOrDefault(file, "messageFilter", List.of("fuck", "bitch", "puta"));

        spawnLocation = getLocationOrDefault(file);
    }

    public JavaPlugin getPlugin() { return plugin; }
    public String getPrefixMessage() { return prefixMessage; }
    public String getWelcomeMessage() { return welcomeMessage; }
    public String getDeathTitle() { return deathTitle; }
    public String getDeathSubTitle() { return deathSubTitle; }
    public String getKickMessage() { return kickMessage; }
    public String getBroadcastKickMessage() { return broadcastKickMessage; }
    public String getGeneralChatFormat() { return generalChatFormat; }
    public String getPlayerNameTag() { return playerNameTag; }
    public List<String> getPlayerRoles() { return playerRoles; }
    public List<String> getMessageFilter() { return messageFilter; }

    public Location getSpawnLocation() { return spawnLocation; }
}
