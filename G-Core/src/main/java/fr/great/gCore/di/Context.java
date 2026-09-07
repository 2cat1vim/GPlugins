package fr.great.gCore.di;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.database.DataRole;

public class Context {
    private static final Context INSTANCE = new Context();

    private DataManager dataManager;
    private DataRole dataRole;
    private ConfigManager configManager;

    private Context() {}

    public static Context getInstance() {
        return INSTANCE;
    }

    public void createContext(DataManager dataManager,
                              DataRole dataRole,
                              ConfigManager configManager) {
        this.dataManager = dataManager;
        this.dataRole = dataRole;
        this.configManager = configManager;
    }

    public DataManager getDataManager() {
        return dataManager;
    }
    public DataRole getDataRole() {
        return dataRole;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
}
