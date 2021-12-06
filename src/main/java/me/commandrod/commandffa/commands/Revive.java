package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.game.GameState;
import me.commandrod.commandffa.utils.MessageUtils;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Revive implements CommandExecutor {

    Game game = Main.getGame();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("revive")){
            if (!sender.hasPermission("commandffa.revive")){
                sender.sendMessage(Utils.color(Utils.getConfigString("messages.permission")));
                Utils.fail(sender);
                return true;
            }
            if (!game.getGameState().equals(GameState.GAME)){
                sender.sendMessage(Utils.color("&cThere are no games running!"));
                Utils.fail(sender);
                return true;
            }
            if (args.length == 0){
                if (!(sender instanceof Player)){
                    MessageUtils.cmdUsage(cmd, sender);
                    return true;
                }
                Player p = (Player) sender;
                if (game.getAlivePlayers().contains(p.getUniqueId())) {
                    sender.sendMessage(Utils.color("&c" + p.getName() + " is already alive!"));
                    Utils.fail(sender);
                    return true;
                }
                game.revive(p);
                p.sendMessage(Utils.color("&3You have been revived."));
            } else {
                Player t = Bukkit.getPlayer(args[0]);
                if (t == null || !t.isOnline()){
                    sender.sendMessage(MessageUtils.INVALID_PLAYER);
                    return true;
                }
                if (game.getAlivePlayers().contains(t.getUniqueId())) {
                    sender.sendMessage(Utils.color("&c" + t.getName() + " is already alive!"));
                    Utils.fail(sender);
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