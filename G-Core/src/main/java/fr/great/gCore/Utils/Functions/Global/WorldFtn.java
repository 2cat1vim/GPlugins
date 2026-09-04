package fr.great.gCore.Utils.Functions.Global;

import fr.great.gCore.Utils.Definitions.BasicDef;
import fr.great.gCore.Utils.Definitions.WorldDef;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class WorldFtn {
    public static void setLocToConfig(FileConfiguration config, Location loc){
        config.set("spawnPosition.x", loc.getX());
        config.set("spawnPosition.y", loc.getY());
        config.set("spawnPosition.z", loc.getZ());
        config.set("spawnPosition.yaw", loc.getYaw());
        config.set("spawnPosition.pitch", loc.getPitch());
        BasicDef.PLUGIN.saveConfig();
    }

    public static Location getLocToConfig(FileConfiguration config) {
        double x = config.getDouble("spawnPosition.x");
        double y = config.getDouble("spawnPosition.y");
        double z = config.getDouble("spawnPosition.z");
        float yaw = (float) config.getDouble("spawnPosition.yaw");
        float pitch = (float) config.getDouble("spawnPosition.pitch");
        return new Location(WorldDef.WORLD, x, y, z, yaw, pitch);
    }
}
