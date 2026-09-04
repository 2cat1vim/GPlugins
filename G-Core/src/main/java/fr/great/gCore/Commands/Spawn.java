package fr.great.gCore.Commands;

import fr.great.gCore.Utils.Definitions.BasicDef;
import fr.great.gCore.Utils.Definitions.WorldDef;
import fr.great.gCore.Utils.Functions.Global.Success;
import fr.great.gCore.Utils.Functions.Global.WorldFtn;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import fr.great.gCore.Utils.Functions.Global.Error;

public class Spawn implements CommandExecutor {

    public String getLocAsString(Location loc) {
        return (loc.getX() + ", " + loc.getY() + ", " + loc.getZ() + ", " + loc.getYaw() + ", " + loc.getPitch());
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            Error.sendErrorServer("This command require to be a player");
            return true;
        }
        Player p = (Player)sdr;
        switch (args.length) {
            case 0:
                p.teleport(WorldDef.LOCATION);
                break ;
            case 1:
                if (args[0].equals("set")) {
                    if (!p.hasPermission("gcore.spawn.set")) {
                        Error.sendError("You do not have the permission", p);
                        break;
                    }
                    WorldDef.LOCATION = p.getLocation();
                    WorldFtn.setLocToConfig(BasicDef.CONFIG, WorldDef.LOCATION);
                    Success.sendSuccess("Spawn location is now: " + getLocAsString(WorldDef.LOCATION), p);
                    break ;
                }
                if (args[0].equals("get")) {
                    if (!p.hasPermission("gcore.spawn.get")) {
                        Error.sendError("You do not have the permission", p);
                        break;
                    }
                    Success.sendSuccess(getLocAsString(WorldDef.LOCATION), p);
                    break ;
                }
            default:
                Error.sendError("Usage: /spawn <null> : <set> : <get>", p);
        }
        sdr.sendMessage(args);
        return true;
    }
}
