package fr.great.gCore.Utils.Functions.Global;

import fr.great.gCore.Utils.Definitions.BasicDef;
import org.bukkit.Bukkit;

public class Delay {
    public static void Set(Runnable f, long delay) {
        Bukkit.getScheduler().runTaskLater(BasicDef.PLUGIN, f, delay);
    }
}
