package fr.great.gCore;

import fr.great.gCore.cmds.Spawn;
import fr.great.gCore.cmds.TabComplete.TSpawn;
import fr.great.gCore.events.Join;
import fr.great.gCore.utils.Definitions.BasicDef;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;

import java.io.IOException;

public final class GCore extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Join(), this);
        this.getCommand("spawn").setExecutor(new Spawn());
        this.getCommand("spawn").setTabCompleter(new TSpawn());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
