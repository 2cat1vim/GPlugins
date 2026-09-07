package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.di.Context;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.great.gCore.utils.Logger.*;

public class CmdSpawn implements CommandExecutor {

    private final ConfigManager cm = Context.getInstance().getConfigManager();

    public String getLocAsString(Location loc) {
        return (loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sendErrorServer("This command require to be a player", cm.getPlugin());
            return true;
        }
        Player p = (Player)sdr;
        switch (args.length) {
            case 0:
                p.teleport(cm.getSpawnLocation());
                break ;
            case 1:
                if (args[0].equals("set")) {
                    if (!p.hasPermission("gcore.spawn.set")) {
                        sendError("You do not have the permission", p);
                        break;
                    }
                    cm.setSpawnLocation(cm.getPlugin().getConfig(), p.getLocation());
                    sendSuccess("Spawn location is now: " + getLocAsString(p.getLocation()), p);
                    break ;
                }
                if (args[0].equals("get")) {
                    if (!p.hasPermission("gcore.spawn.get")) {
                        sendError("You do not have the permission", p);
                        break;
                    }
                    sendSuccess(getLocAsString(cm.getSpawnLocation()), p);
                    break ;
                }
            default:
                sendError("Usage: /spawn <null> : <set> : <get>", p);
        }
        return true;
    }
}
