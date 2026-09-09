package fr.great.gCore.di;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataBan;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.database.DataMute;
import fr.great.gCore.database.DataRole;

public class Context {
    private static final Context INSTANCE = new Context();

    private DataManager dataManager;
    private DataRole dataRole;
    private DataMute dataMute;
    private DataBan dataBan;
    private ConfigManager configManager;

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
    public void createDataMute(DataMute dataMute) {
        this.dataMute = dataMute;
    }
    public void createDataBan(DataBan dataBan) {
        this.dataBan = dataBan;
    }
    public void createConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public DataManager getDataManager() { return this.dataManager; }
    public DataRole getDataRole() { return this.dataRole; }
    public DataMute getDataMute() { return this.dataMute; }
    public DataBan getDataBan() { return this.dataBan; }
    public ConfigManager getConfigManager() { return this.configManager; }
}
