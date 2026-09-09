package fr.great.gCore.commands.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TabMute implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sdr,
                                                @NotNull Command cmd,
                                                @NotNull String lbl,
                                                @NotNull String @NotNull [] args) {
        return List.of();
    }
}
