package fr.great.gCore.Commands;

import fr.great.gCore.Utils.Definitions.BasicDef;
import fr.great.gCore.Utils.Definitions.WorldDef;
import fr.great.gCore.Utils.Functions.Global.Error;
import fr.great.gCore.Utils.Functions.Global.Success;
import fr.great.gCore.Utils.Functions.Global.WorldFtn;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

public class Role implements CommandExecutor {
    private final fr.great.gCore.Database.Role role;
    public Role(fr.great.gCore.Database.Role role) {
        this.role = role;
    }

    public int getRoleAsync(Player p, Player t) {
        final int[] r = {0};
        Bukkit.getScheduler().runTaskAsynchronously(BasicDef.PLUGIN, () -> {
            try {
                r[0] = role.getRole(t);
            } catch (SQLException e) {
                Error.sendError("SQL Error", p);
            }
        });
        return r[0];
    }

    public void setRoleAsync(Player p, Player t, int r) {
        Bukkit.getScheduler().runTaskAsynchronously(BasicDef.PLUGIN, () -> {
            try {
                role.setRole(t, r);
            } catch (SQLException e) {
                Error.sendError("SQL Error", p);
            }
        });
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            fr.great.gCore.Utils.Functions.Global.Error.sendErrorServer("This command require to be a player");
            return true;
        }
        if (args.length == 0) {
            Error.sendError("Usage:\n* /role list\n* /role <set> <player> <role>\n* /role <get> <player>", ((Player) sdr).getPlayer());
            return true;
        }
        Player p = (Player)sdr;
        Player t = Bukkit.getPlayer(args[1]);
        if (t == null) {
            Error.sendError(args[1] + " is offline", p);
        }
        switch (args.length) {
            case 2:
                if (args[0].equals("get")) {
                    if (!p.hasPermission("gcore.role.get")) {
                        Error.sendError("You do not have the permission", p);
                        break;
                    }
                    int r = getRoleAsync(p, t);
                    Success.sendSuccess(p.getName() + " have role: " + role.getRoleByValue(r), p);
                    break;
                }
            case 3:
                if (args[0].equals("set")) {
                    if (!p.hasPermission("gcore.role.set")) {
                        Error.sendError("You do not have the permission", p);
                        break;
                    }
                    int r = role.getValueByRole(args[2]);
                    setRoleAsync(p, t, r);
                    Success.sendSuccess(t.getName() + " role is set to: " + role.getRoleByValue(r), p);
                    break ;
                }
            default:
                Error.sendError("Usage:\n* /role list\n* /role <set> <player> <role>\n* /role <get> <player>", p);
                break;
        }
        return true;
    }
}
