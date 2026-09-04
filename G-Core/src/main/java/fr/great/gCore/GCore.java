package fr.great.gCore;

import fr.great.gCore.Database.Manager;
import fr.great.gCore.Commands.Spawn;
import fr.great.gCore.Commands.TabComplete.TSpawn;
import fr.great.gCore.Events.Chat;
import fr.great.gCore.Events.Join;
import fr.great.gCore.Utils.Definitions.WorldDef;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class GCore extends JavaPlugin {

    public void setEnvironmentSettings() {
        World world = WorldDef.WORLD;
        System.out.println(world.toString());
        world.setDifficulty(Difficulty.HARD);
        for (GameRule<?> rule : Registry.GAME_RULE) {
            if (rule.getType() == Boolean.class) {
                @SuppressWarnings("unchecked")
                GameRule<Boolean> boolRule = (GameRule<Boolean>) rule;
                world.setGameRule(boolRule, false);
            }
        }
    }
    private Manager manager;
    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            manager = new Manager(
                    getDataFolder().getAbsolutePath() + "/database.db"
            );
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Join(manager), this);
        getServer().getPluginManager().registerEvents(new Chat(), this);
        this.getCommand("spawn").setExecutor(new Spawn());
        this.getCommand("spawn").setTabCompleter(new TSpawn());
        setEnvironmentSettings();
    }

    @Override
    public void onDisable() {
        try {
            if (manager != null) {
                manager.closeConnection();
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        // Plugin shutdown logic
    }
}
