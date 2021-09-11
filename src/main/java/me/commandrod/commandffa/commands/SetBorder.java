package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.utils.Messages;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static me.commandrod.commandffa.Main.plugin;

public class SetBorder implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setborder")){
            if (sender.hasPermission("commandffa.setborder")){
                sender.sendMessage(Utils.color(Messages.PERMISSION));
                Utils.fail(sender);
                return true;
            }
            if (args.length <= 2){
                sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
                return true;
            }
            if (Integer.valueOf(args[1]) != null){
                sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
                return true;
            }
            int number = Integer.parseInt(args[1]);
            if (args[0].equalsIgnoreCase("seconds") || args[0].equalsIgnoreCase("distance") || args[0].equalsIgnoreCase("warning")){
                plugin().getConfig().set("border." + args[0], number);
                plugin().saveConfig();
                plugin().reloadConfig();
                sender.sendMessage(Utils.color("&3Successfully updated the &b" + args[0] + " &3value."));
            }
        }
        return true;
    }
}