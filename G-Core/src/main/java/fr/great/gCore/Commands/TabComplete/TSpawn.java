package fr.great.gCore.Commands.TabComplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TSpawn implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        List<String> list = new ArrayList<>();
        if (args.length == 1) {
            list.add("set");
            list.add("get");
            return StringUtil.copyPartialMatches(args[0].toLowerCase(), list, new ArrayList<String>());
        }
        return Collections.emptyList();
    }
}
