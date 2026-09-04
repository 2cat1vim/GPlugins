package fr.great.gCore.Utils.Definitions;

import fr.great.gCore.Utils.Functions.Global.WorldFtn;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WorldDef {
    public static final org.bukkit.World WORLD = Bukkit.getWorld("World");
    public static Location LOCATION = WorldFtn.getLocToConfig(BasicDef.CONFIG);
}
