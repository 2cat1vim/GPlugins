package fr.great.gFFA.commands.CmdDuel;

import fr.great.gFFA.di.FFAContext;
import fr.great.gFFA.manager.ManagerDuel;
import fr.great.gFFA.manager.ManagerDuelRequest;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import org.jetbrains.annotations.NotNull;

public class CmdDuel implements CommandExecutor {

    private ManagerDuel md = FFAContext.getInstance().getManagerDuel();
    private ManagerDuelRequest mdr = FFAContext.getInstance().getManagerDuelRequest();

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sdr.sendMessage("Not instanceof Player");
            return true;
        }
        Player p = (Player)sdr;
        int len = args.length;
        if (len != 1 && len != 2) {
            p.sendMessage("Usage: /duel <player> : /duel <accept/deny> <player>");
            return true;
        }
        Player t = null;
        String targetName;
        if (len == 1) {
            targetName = args[0];
            if (targetName.equals("accept") || targetName.equals("deny")) {
                p.sendMessage("Usage: /duel <player> : /duel <accept/deny> <player>");
                return true;
            }
            t = Bukkit.getPlayer(targetName);
            if (t == null) {
                p.sendMessage(targetName + "is offline");
                return true;
            }
            if (p.equals(t)) {
                p.sendMessage("Humm ? You are not supposed to duel yourself");
                return true;
            }
            p.sendMessage("Duel request has been sent to " + t.getName() + "\n" +
                    "This request will expire in one minute");
            t.sendMessage(p.getName() + " sent you a duel request!\n" +
                    " - /duel accept " + p.getName() + "\n" +
                    " - /duel deny " + p.getName() + "\n" +
                    "This request will expire in one minute");
            mdr.addPendingRequest(p, t);
            mdr.timeoutRequest(p, t);
        }
        if (len == 2) {
            targetName = args[1];
            t = Bukkit.getPlayer(targetName);
            if (t == null) {
                p.sendMessage(targetName + "is offline");
                return true;
            }
            if (args[0].equals("accept")) {
                if (mdr.hasPendingRequest(t, p)) {
                    p.sendMessage("You accepted the duel request from" + t.getName());
                    t.sendMessage(p.getName() + " accepted the duel request");
                    mdr.removePendingRequest(t, p);
                    md.addDuel(t, p);
                    return true;
                }
                p.sendMessage("There is no pending request from" + t.getName());
                return true;
            }
            if (args[0].equals("deny")) {
                mdr.removePendingRequest(t, p);
                p.sendMessage("You denied the duel request from" + t.getName());
                t.sendMessage(p.getName() + " denied the duel request");
                return true;
            }
        }
        return true;
    }
}
