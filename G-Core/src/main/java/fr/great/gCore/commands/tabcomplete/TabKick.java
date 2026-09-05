package fr.great.gCore.commands.tabcomplete;

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

public class TabKick implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sdr, @NotNull Command cmd, @NotNull String lbl, @NotNull String @NotNull [] args) {
        if (args.length == 1) {
            List<String> names = new ArrayList<>();
            for (Player player : Bukkit.getOnlinePlayers()) {
                names.add(player.getName());
            }
            return StringUtil.copyPartialMatches(args[0].toLowerCase(), names, new ArrayList<>());
        }
        if (args.length == 2) {
            List<String> exemple = new ArrayList<>();
            exemple.add("Spam");
            exemple.add("Insult");
            exemple.add("Racism");
            return StringUtil.copyPartialMatches(args[1].toLowerCase(), exemple, new ArrayList<>());
        }
        return Collections.emptyList();
    }
}
