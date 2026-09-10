package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.di.Context;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static fr.great.gCore.utils.Logger.*;

public class CmdKick implements CommandExecutor {
    private final ConfigManager cm = Context.getInstance().getConfigManager();

    public String filterConfigKickMessage(String msg, String pName, String reason) {
        String newMsg;
        newMsg = msg.replace("<player>", pName);
        newMsg = newMsg.replace("<reason>", reason);
        return (cm.getBroadcastPrefixMessage() + newMsg);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sendErrorServer("This command require to be a player", cm.getPlugin());
            return true;
        }
        Player p = (Player)sdr;
        if (!p.hasPermission("gcore.moderation.kick")) {
            sendError("You do not have the permission", p);
            return true;
        }
        if (args.length < 2) {
            sendError("Usage:\n*/kick <player> <reason>", p);
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            sendError(args[0] + " is offline", p);
            return true;
        }

        t.kick(Component.text(cm.getKickMessage() + "\nReason:\n" +
                String.join(", ", Arrays.copyOfRange(args, 1, args.length))));
        Bukkit.getServer().sendPlainMessage(filterConfigKickMessage(cm.getBroadcastKickMessage(), args[0], args[1]));
        return true;
    }
}
