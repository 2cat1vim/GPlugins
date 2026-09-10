package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataRestriction;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Date;

import static fr.great.gCore.utils.Logger.sendError;
import static fr.great.gCore.utils.Logger.sendErrorServer;
import static java.lang.Character.isDigit;

public class CmdMute implements CommandExecutor {

    private final ConfigManager cm = Context.getInstance().getConfigManager();
    private final DataRestriction dr = Context.getInstance().getDataMute();
    private final long parseError = -2;

    public long parseTime(String time) {
        if (time.equals("!")) {
            return -1;
        }
        long value = 0;
        int last = -1;
        for (int i = 0; i < time.length(); i++) {
            if (!isDigit(time.charAt(i))) {
                String sub = time.substring(0, i);
                if (sub.length() > 7) {
                    return parseError;
                }
                try {
                    value = Long.parseLong(sub);
                }
                catch (NumberFormatException e) {
                    return parseError;
                }
                last = i;
                break;
            }
        }
        if (last == -1) {
            return (parseError);
        }
        switch (time.charAt(last)) {
            case 'h':
                return (value * (60 * 60000L));
            case 'd':
                return (value * (24 * 3600000L));
            case 'm':
                return (value * (30 * 86400000L));
            case 'y':
                return (value * (365 * 86400000L));
        }
        return parseError;
    }

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
        if (args.length < 3) {
            sendError("Usage: /mute <player> <time> <reason>", p);
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            sendError(args[0] + " is offline", p);
            return true;
        }
        if (!p.hasPermission("gcore.moderation.mute")) {
            sendError("You do not have the permission", p);
            return true;
        }
        long time = parseTime(args[1]);
        if (time == parseError) {
            sendError("Usage of time: <value><mesure> : 1h, 1d, 1m, 1y, !", p);
            return true;
        }
        try {
            dr.setRestriction(t, "mute", time);
        } catch (SQLException e) {
            sendError("SQL Error, contact dev [" + e.getMessage() + "]", p);
            return true;
        }
        Date date = new Date(System.currentTimeMillis() + time);
        String msg = cm.getBroadcastMuteMessage();
        msg = msg.replace("<player>", t.getName());
        msg = msg.replace("<time>", time == -1 ? "Sun Explode" : date.toLocaleString());
        msg = msg.replace("<reason>", String.join(", ", Arrays.copyOfRange(args, 2, args.length)));
        Bukkit.getServer().sendPlainMessage(cm.getBroadcastPrefixMessage() + msg);
        return true;
    }
}
