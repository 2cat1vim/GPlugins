package fr.great.gCore.commands;

import fr.great.gCore.config.ConfigManager;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.ChunkGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static fr.great.gCore.utils.Logger.sendError;
import static fr.great.gCore.utils.Logger.sendErrorServer;

public class CmdWorld implements CommandExecutor {

    private ConfigManager cm = Context.getInstance().getConfigManager();

    @Override
    public boolean onCommand(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (!(sdr instanceof Player)) {
            sendErrorServer("This command require to be a player", cm.getPlugin());
            return true;
        }
        int len = args.length;
        Player p = (Player)sdr;
        if (len < 2) {
            sendError("Usage: /world <create> <type> <name> : <delete> <name> : <tp> <name>", p);
            return true;
        }
        List<String> usable = List.of("create", "delete", "tp");
        if (!usable.contains(args[0])) {
            sendError("Usage: /world <create> <type> <name> : <delete> <name> : <tp> <name>", p);
        }
        World world;
        if (len == 2) {
            world = Bukkit.getWorld(args[1]);
            if (world == null) {
                sendError("World " + args[1] + " does not exist", p);
                return true;
            }
        }
        if (!p.hasPermission("gcore.world.manage")) {
            sendError("You do not have the permission", p);
            return true;
        }
        if (args[0].equals("create")) {
            List<String> type = List.of("void", "flat", "normal");
            if (!type.contains(args[1])) {
                sendError("World type is not correct: <void>, <flat>, <normal>", p);
                return true;
            }
            if (args[1].equals("void")) {
            }
        }
        return true;
    }
}
