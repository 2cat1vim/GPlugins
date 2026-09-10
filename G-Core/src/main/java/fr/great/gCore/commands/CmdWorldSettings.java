package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.database.DataInventory;
import fr.great.gCore.di.Context;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static fr.great.gCore.utils.Logger.sendError;
import static fr.great.gCore.utils.Logger.sendErrorServer;

public class CmdWorldSettings implements CommandExecutor {

    private ConfigManager cm = Context.getInstance().getConfigManager();
    private DataInventory di = Context.getInstance().getDataInventory();

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sendErrorServer("This command require to be a player", cm.getPlugin());
            return true;
        }
        Player p = (Player)sdr;
        if (!p.hasPermission("gcore.settings.world")) {
            sendError("You do not have the permission", p);
            return true;
        }
        if (args.length != 0) {
            sendError("Usage: /worldsettings", p);
            return true;
        }
        p.openInventory(di.getGameruleInventory().getFirst());
        return true;
    }
}
