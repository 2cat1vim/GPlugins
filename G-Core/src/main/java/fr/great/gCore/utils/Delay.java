package fr.great.gCore.utils;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;

public class Delay {
    private static final ConfigManager cm = Context.getInstance().getConfigManager();
    public static void Set(Runnable f, long delay) {
        Bukkit.getScheduler().runTaskLater(cm.getPlugin(), f, delay);
    }
}
