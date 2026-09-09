package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataMute;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;

import static fr.great.gCore.utils.Logger.*;

public class CmdUnmute implements CommandExecutor {

    private final ConfigManager cm = Context.getInstance().getConfigManager();
    private final DataMute dm = Context.getInstance().getDataMute();

    @Override
    public boolean onCommand(@NotNull CommandSender sdr,
                             @NotNull Command cmd,
                             @NotNull String lbl,
                             @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sendErrorServer("This command require to be a player", cm.getPlugin());
            return true;
        }
        Player p = (Player)sdr;
        if (args.length != 1) {
            sendError("Usage: /unmute <player>", p);
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            sendError(args[0] + " is offline", p);
            return true;
        }
        if (!p.hasPermission("gcore.moderation.unmute")) {
            sendError("You do not have the permission", p);
            return true;
        }
        try {
            if (dm.isMute(t)) {
                dm.setMute(t, 0);
                sendSuccess(t.getName() + " is now unmuted", p);
            }
            else {
                sendError(t.getName() + " have no mute", p);
                return true;
            }
        } catch (SQLException e) {
            sendError("SQL Error, contact dev", p);
        }
        return true;
    }
}
