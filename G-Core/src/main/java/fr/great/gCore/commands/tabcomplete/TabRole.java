package fr.great.gCore.commands.tabcomplete;

import fr.great.gCore.database.DataRole;
import fr.great.gCore.di.Context;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TabRole implements TabCompleter {

    private final DataRole dr = Context.getInstance().getDataRole();

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            List<String> list = new ArrayList<>();
            list.add("set");
            list.add("get");
            list.add("list");
            return StringUtil.copyPartialMatches(args[0].toLowerCase(), list, new ArrayList<>());
        }
        if (args.length == 2
                && (args[0].equalsIgnoreCase("set")
                || args[0].equalsIgnoreCase("get")
        )) {
            List<String> names = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[1].toLowerCase(), names, new ArrayList<>());
        }
        if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
            List<String> roles = new ArrayList<>();
            int size = dr.getRoles().size();
            for (int i = 0; i < size; ++i) {
                roles.add(dr.getRoleByValue(i));
            }
            return StringUtil.copyPartialMatches(args[2].toLowerCase(), roles, new ArrayList<>());
        }
        return Collections.emptyList();
    }
}