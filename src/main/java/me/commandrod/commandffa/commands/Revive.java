package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.utils.Messages;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Revive implements CommandExecutor {

    Game game = Start.game;

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("revive")){
                if (!sender.hasPermission("commandffa.revive")){
                    sender.sendMessage(Utils.color(Utils.getConfigString("messages.permission")));
                    Utils.fail(sender);
                    return true;
                }
                if (!game.isGame()){
                    sender.sendMessage(Utils.color("&cThere is are no games running!"));
                    return true;
                }
                if (args.length == 0){
                    if (!(sender instanceof Player)){
                        sender.sendMessage(cmd.getDescription() + "\n" + cmd.getUsage());
                        return true;
                    }
                    Player p = (Player) sender;
                    game.revive(p);
                    p.sendMessage(Utils.color("&3You have been revived."));
                } else if (args.length >= 1){
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t == null){
                        sender.sendMessage(Utils.color("&c" + args[0] + " is not a valid player!"));
                        return true;
                    }
                    game.revive(t);
                    sender.sendMessage(Utils.color("&b" + t.getName() + " &3has been revived."));
                    t.sendMessage(Utils.color("&3You have been revived."));
                }
        }
        return true;
    }
}