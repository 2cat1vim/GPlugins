package fr.great.gCore.utils.Definitions;

import fr.great.gCore.cmds.Spawn;
import fr.great.gCore.utils.Functions.WorldFtn;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WorldDef {
    public static final org.bukkit.World WORLD = Bukkit.getWorld("World");
    public static Location LOCATION = WorldFtn.getLocToConfig(BasicDef.CONFIG);
}
