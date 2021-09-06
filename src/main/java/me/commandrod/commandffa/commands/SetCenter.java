package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.utils.Messages;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetCenter implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setcenter")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (p.hasPermission("commandffa.setcenter")){
                    Utils.setConfigLocation("center-location", p);
                    p.sendMessage(Utils.color("&3Successfully updated the &bcenter &3location."));
                } else {
                    sender.sendMessage(Utils.color(Messages.PERMISSION));
                    Utils.fail(sender);
                }
            } else {
                sender.sendMessage(Utils.color("&a[CommandFFA] Only players may execute this command!"));
            }
        }
        return true;
    }
}