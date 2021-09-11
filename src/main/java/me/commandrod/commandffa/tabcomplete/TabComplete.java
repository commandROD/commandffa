package me.commandrod.commandffa.tabcomplete;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

public class TabComplete implements TabCompleter {

    List<String> oneArgList = Arrays.asList("countdown", "forceend", "reloadconfig");

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String alias, @NotNull String[] args) {
        if (cmd.getName().equalsIgnoreCase("admin")){
            if (args.length == 1){
                return oneArgList;
            }
        }
        return null;
    }
}
