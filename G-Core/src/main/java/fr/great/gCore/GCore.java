package fr.great.gCore;

import fr.great.gCore.di.Context;
import fr.great.gCore.commands.CmdKick;
import fr.great.gCore.commands.CmdRole;
import fr.great.gCore.commands.tabcomplete.TabKick;
import fr.great.gCore.commands.tabcomplete.TabRole;
import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.commands.CmdSpawn;
import fr.great.gCore.commands.tabcomplete.TabSpawn;
import fr.great.gCore.database.DataRole;
import fr.great.gCore.events.EventChat;
import fr.great.gCore.events.EventDeath;
import fr.great.gCore.events.EventLog;
import fr.great.gCore.events.EventState;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.sql.SQLException;
import java.util.List;
import java.util.Set;

import static fr.great.gCore.utils.Logger.*;
import static org.bukkit.GameRules.*;

public final class GCore extends JavaPlugin {
    private DataManager dataManager;
    private DataRole dataRole;
    private ConfigManager configManager;

    /* Main on */
    @Override
    public void onEnable() {
        if (!this.createDatabaseOrDefault()) {
            return;
        }
        this.regEvents();
        this.regCommands();
        setEnvironmentSettings();
        sendSuccessServer("GCore is started", this);
    }

    /* Main off */
    @Override
    public void onDisable() {
        dataRole.clearTeams();
        closeDatabase();
        sendSuccessServer("GCore is disabled", this);
    }

    /* Rest is helper for main on & off */
    private void regEvents() {
        getServer().getPluginManager().registerEvents(new EventLog(), this);
        getServer().getPluginManager().registerEvents(new EventChat(), this);
        getServer().getPluginManager().registerEvents(new EventState(), this);
        getServer().getPluginManager().registerEvents(new EventDeath(), this);
    }

    private void regCommands() {
        this.getCommand("spawn").setExecutor(new CmdSpawn());
        this.getCommand("spawn").setTabCompleter(new TabSpawn());
        this.getCommand("role").setExecutor(new CmdRole());
        this.getCommand("role").setTabCompleter(new TabRole());
        this.getCommand("gkick").setExecutor(new CmdKick());
        this.getCommand("gkick").setTabCompleter(new TabKick());
    }

    private void setEnvironmentSettings() {
        World world = configManager.getSpawnLocation().getWorld();
        System.out.println(world.toString());
        world.setDifficulty(Difficulty.HARD);
        for (GameRule<?> rule : Registry.GAME_RULE) {
            if (rule.getType() == Boolean.class) {
                @SuppressWarnings("unchecked")
                GameRule<Boolean> boolRule = (GameRule<Boolean>) rule;
                world.setGameRule(boolRule, false);
            }
        }
        world.setGameRule(IMMEDIATE_RESPAWN, true);
        world.setGameRule(PVP, true);
    }

    private boolean createDatabaseOrDefault() {
        configManager = new ConfigManager(this);
        Context.getInstance().createConfigManager(configManager);
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            dataManager = new DataManager(
                    getDataFolder().getAbsolutePath() + "/database.db"
            );
            Context.getInstance().createDataManager(dataManager);
            dataRole = new DataRole();
            Context.getInstance().createDataRole(dataRole);
            return true;
        } catch (SQLException e) {
            sendErrorServer("Failed to create or load database", this);
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }
    }

    private void closeDatabase() {
        try {
            if (dataManager != null) {
                dataManager.closeConnection();
            }
        }
        catch (SQLException e) {
            sendErrorServer("Failed to close database", this);
        }
    }
}
