package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.utils.Messages;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.commandrod.commandffa.Main.plugin;

public class Revive implements CommandExecutor {

    Game game = new Game();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")){
                if (!sender.hasPermission("commandffa.revive")){
                    sender.sendMessage(Utils.color(Messages.PERMISSION));
                    Utils.fail(sender);
                    return true;
                }
                if (!game.isGame()){
                    sender.sendMessage(Utils.color(""));
                    return true;
                }
                if (args.length == 0){
                    if (!(sender instanceof Player)){
                        sender.sendMessage(cmd.getDescription() + "\n" + cmd.getUsage());
                        return true;
                    }
                    Player p = (Player) sender;
                    game.revive(p);
                } else if (args.length >= 1){
                    Player t = Bukkit.getPlayer(args[0]);
                    if (t == null){
                        sender.sendMessage(Utils.color("&c" + args[0] + " is not a valid player!"));
                        return true;
                    }
                    game.revive(t);
                }
        }
        return true;
    }
}