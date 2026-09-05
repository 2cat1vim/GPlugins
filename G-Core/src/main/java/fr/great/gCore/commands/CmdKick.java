package fr.great.gCore.commands;

import fr.great.gCore.Utils.Functions.Global.Error;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.great.gCore.Utils.Definitions.BasicDef.BROADCASTKICKMESSAGE;
import static fr.great.gCore.Utils.Definitions.BasicDef.KICKMESSAGE;

public class CmdKick implements CommandExecutor {
    private final String usage = "Usage:\n*/kick <player> <reason>";

    public String filterConfigKickMessage(String msg, String pName, String reason) {
        String newMsg;
        newMsg = msg.replace("<player>", pName);
        newMsg = newMsg.replace("<reason>", reason);
        return newMsg;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            Error.sendErrorServer("This command require to be a player");
            return true;
        }
        Player p = (Player)sdr;
        if (!p.hasPermission("gcore.moderation.kick")) {
            Error.sendError("You do not have the permission", p);
            return true;
        }
        if (args.length != 2) {
            Error.sendError(usage, p);
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            Error.sendError(args[0] + " is offline", p);
            return true;
        }
        t.kick(Component.text(KICKMESSAGE + "\nReason:\n" + args[1]));
        Bukkit.getServer().sendPlainMessage(filterConfigKickMessage(BROADCASTKICKMESSAGE, args[0], args[1]));
        return true;
    }
}
