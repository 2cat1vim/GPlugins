package fr.great.gFFA;

import fr.great.gCore.di.API;
import fr.great.gCore.di.Context;
import fr.great.gCore.events.EventDeath;
import fr.great.gFFA.commands.CmdDuel.CmdDuel;
import fr.great.gFFA.commands.cmdFreeDuel;
import fr.great.gFFA.di.FFAContext;
import fr.great.gFFA.events.EventDuelDeath;
import fr.great.gFFA.manager.ManagerDuel;
import fr.great.gFFA.manager.ManagerDuelRequest;
import org.bukkit.plugin.java.JavaPlugin;

public final class GFFA extends JavaPlugin {

    @Override
    public void onEnable() {
        API.register(Context.getInstance());
        FFAContext.getInstance().createManagerDuel(new ManagerDuel());
        FFAContext.getInstance().createManagerDuelRequest(new ManagerDuelRequest());
        this.getCommand("duel").setExecutor(new CmdDuel());
        this.getCommand("freeduel").setExecutor(new cmdFreeDuel());
        getServer().getPluginManager().registerEvents(new EventDuelDeath(), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
