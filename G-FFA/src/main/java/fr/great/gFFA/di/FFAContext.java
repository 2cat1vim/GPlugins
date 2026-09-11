package fr.great.gFFA.di;


import fr.great.gCore.di.Context;
import fr.great.gFFA.manager.ManagerDuel;
import fr.great.gFFA.manager.ManagerDuelRequest;

public class FFAContext {

    private static final FFAContext INSTANCE = new FFAContext();

    private ManagerDuel managerDuel;
    private ManagerDuelRequest managerDuelRequest;

    private FFAContext() {
    }

    public static FFAContext getInstance() {
        return INSTANCE;
    }

    public void createManagerDuel(ManagerDuel managerDuel) { this.managerDuel = managerDuel; }
    public void createManagerDuelRequest(ManagerDuelRequest managerDuelRequest) { this.managerDuelRequest = managerDuelRequest; }

    public ManagerDuel getManagerDuel() { return this.managerDuel; }
    public ManagerDuelRequest getManagerDuelRequest() { return this.managerDuelRequest; }
}
