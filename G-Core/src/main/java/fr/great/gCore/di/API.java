package fr.great.gCore.di;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataInventory;
import fr.great.gCore.database.DataManager;
import fr.great.gCore.database.DataRestriction;
import fr.great.gCore.database.DataRole;

public class API {
    private static CoreDi instance;

    public static CoreDi get() {
        return instance;
    }

    public static void register(CoreDi impl) {
        instance = impl;
    }

    public interface CoreDi {
        DataManager dataManager();
        DataRole dataRole();
        DataRestriction dataMute();
        ConfigManager configManager();
        DataInventory dataInventory();
    }
}
