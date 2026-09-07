package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataRole;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static fr.great.gCore.utils.Logger.*;

public class CmdRole implements CommandExecutor {
    private final DataRole dr = Context.getInstance().getDataRole();
    private final ConfigManager cm = Context.getInstance().getConfigManager();

    public void updateRoleAsync(Player p, Player t, int r) {
        Bukkit.getScheduler().runTaskAsynchronously(cm.getPlugin(), () -> {
            try {
                dr.setRole(t, r);
                dr.setNameTagColor(t, dr.getNameTagColor(t, r), r);
            } catch (SQLException e) {
                sendError("SQL Error", p);
            }
        });
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sendErrorServer("This command require to be a player", cm.getPlugin());
            return true;
        }
        final String usage = "Usage:\n* /role list\n* /role set <player> <role>\n* /role get <player>";
        Player p = (Player)sdr;
        if (args.length == 0) {
            sendError(usage, p);
            return true;
        }
        if (args.length == 1 && args[0].equals("list")) {
            sendSuccess(String.join(" : ", cm.getPlayerRoles()), p);
            return true;
        }
        Player t = Bukkit.getPlayer(args[1]);
        if (t == null) {
            sendError(args[1] + " is offline", p);
            return true;
        }
        switch (args.length) {
            case 2:
                if (args[0].equals("get")) {
                    if (!p.hasPermission("gcore.role.get")) {
                        sendError("You do not have the permission", p);
                        break;
                    }
                    int r = 0;
                    try {
                        r = dr.getRole(t);
                    } catch (SQLException e) {
                        sendError("SQL Error", p);
                    }
                    sendSuccess(p.getName() + " have role: " + dr.getRoleByValue(r), p);
                }
                else {
                    sendError(usage, p);
                }
                break;
            case 3:
                if (args[0].equals("set")) {
                    if (!p.hasPermission("gcore.role.set")) {
                        sendError("You do not have the permission", p);
                        break;
                    }
                    int r = dr.getValueByRole(args[2]);
                    updateRoleAsync(p, t, r);
                    sendSuccess(t.getName() + " role is set to: " + dr.getRoleByValue(r), p);
                }
                else {
                    sendError(usage, p);
                }
                break;
            default:
                sendError(usage, p);
                break;
        }
        return true;
    }
}
