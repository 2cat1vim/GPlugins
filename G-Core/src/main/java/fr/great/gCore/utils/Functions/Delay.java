package fr.great.gCore.utils.Functions;

import fr.great.gCore.utils.Definitions.BasicDef;
import org.bukkit.Bukkit;

import java.util.function.Function;

public class Delay {
    public static void Set(Runnable f, long delay) {
        Bukkit.getScheduler().runTaskLater(BasicDef.PLUGIN, f, delay);
    }
}
