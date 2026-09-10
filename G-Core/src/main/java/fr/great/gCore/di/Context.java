package fr.great.gCore.di;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataInventory;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.database.DataRestriction;
import fr.great.gCore.database.DataRole;

import javax.xml.crypto.Data;

public class Context {
    private static final Context INSTANCE = new Context();

    private DataManager dataManager;
    private DataRole dataRole;
    private DataRestriction dataMute;
    private ConfigManager configManager;
    private DataInventory dataInventory;

    private Context() {}

    public static Context getInstance() {
        return INSTANCE;
    }

    public void createDataManager(DataManager dataManager) {
        this.dataManager = dataManager;
    }
    public void createDataRole(DataRole dataRole) {
        this.dataRole = dataRole;
    }
    public void createDataMute(DataRestriction dataMute) {
        this.dataMute = dataMute;
    }
    public void createConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
    public void createDataInventory(DataInventory dataInventory) {
        this.dataInventory = dataInventory;
    }

    public DataManager getDataManager() { return this.dataManager; }
    public DataRole getDataRole() { return this.dataRole; }
    public DataRestriction getDataMute() { return this.dataMute; }
    public ConfigManager getConfigManager() { return this.configManager; }
    public DataInventory getDataInventory() { return this.dataInventory; }

}
