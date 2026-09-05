package fr.great.gCore;

import fr.great.gCore.Utils.Functions.Global.Error;
import fr.great.gCore.Utils.Functions.Global.Success;
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
import fr.great.gCore.Utils.Definitions.WorldDef;
import org.bukkit.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

import static fr.great.gCore.Utils.Functions.Global.Success.sendSuccessServer;
import static org.bukkit.GameRules.*;

public final class GCore extends JavaPlugin {

    private DataManager dataManager;
    private DataRole dataRole;
    private ConfigManager configManager;

    /* Main on */
    @Override
    public void onEnable() {
        this.createDatabaseOrDefault();
        configManager = new ConfigManager(this);
        this.regEvents();
        this.regCommands();
        setEnvironmentSettings();
        Success.sendSuccessServer("GCore is started", this);
    }

    /* Main off */
    @Override
    public void onDisable() {
        closeDatabase();
        Success.sendSuccessServer("GCore is disabled", this);
    }

    /* Rest is helper for main on & off */
    private void regEvents() {
        getServer().getPluginManager().registerEvents(new EventLog(dataManager, dataRole), this);
        getServer().getPluginManager().registerEvents(new EventChat(dataRole), this);
        getServer().getPluginManager().registerEvents(new EventState(), this);
        getServer().getPluginManager().registerEvents(new EventDeath(), this);
    }

    private void regCommands() {
        this.getCommand("spawn").setExecutor(new CmdSpawn());
        this.getCommand("spawn").setTabCompleter(new TabSpawn());
        this.getCommand("role").setExecutor(new CmdRole(dataRole));
        this.getCommand("role").setTabCompleter(new TabRole(dataRole));
        this.getCommand("gkick").setExecutor(new CmdKick());
        this.getCommand("gkick").setTabCompleter(new TabKick());
    }

    private void setEnvironmentSettings() {
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
        world.setGameRule(IMMEDIATE_RESPAWN, true);
    }

    private void createDatabaseOrDefault() {
        try {
            if (!getDataFolder().exists()) {
                getDataFolder().mkdirs();
            }
            dataManager = new DataManager(
                    getDataFolder().getAbsolutePath() + "/database.db"
            );
            dataRole = new DataRole(dataManager);
        } catch (SQLException e) {
            Error.sendErrorServer("Failed to create or load database", this);
            Bukkit.getPluginManager().disablePlugin(this);
        }
    }

    private void closeDatabase() {
        try {
            if (dataManager != null) {
                dataManager.closeConnection();
            }
        }
        catch (SQLException e) {
            Error.sendErrorServer("Failed to close database", this);
        }
    }
}
