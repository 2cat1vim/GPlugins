package fr.great.gCore;

import fr.great.gCore.Commands.Role;
import fr.great.gCore.Commands.TabComplete.TRole;
import fr.great.gCore.Database.Manager;
import fr.great.gCore.Commands.Spawn;
import fr.great.gCore.Commands.TabComplete.TSpawn;
import fr.great.gCore.Events.Chat;
import fr.great.gCore.Events.Join;
import fr.great.gCore.Events.PlayerState;
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
    private fr.great.gCore.Database.Role role;
    @Override
    public void onEnable() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            manager = new Manager(
                    getDataFolder().getAbsolutePath() + "/database.db"
            );
            role = new fr.great.gCore.Database.Role(manager);
        } catch (SQLException e) {
            e.printStackTrace();
            Bukkit.getPluginManager().disablePlugin(this);
        }
        this.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new Join(manager, role), this);
        getServer().getPluginManager().registerEvents(new Chat(role), this);
        getServer().getPluginManager().registerEvents(new PlayerState(), this);
        this.getCommand("spawn").setExecutor(new Spawn());
        this.getCommand("spawn").setTabCompleter(new TSpawn());
        this.getCommand("role").setExecutor(new Role(role));
        this.getCommand("role").setTabCompleter(new TRole(role));
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
