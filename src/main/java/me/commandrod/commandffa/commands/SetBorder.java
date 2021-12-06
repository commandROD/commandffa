package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.utils.MessageUtils;
import me.commandrod.commandffa.utils.Utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SetBorder implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("setborder")){
            if (!sender.hasPermission("commandffa.setborder")){
                sender.sendMessage(Utils.color(Utils.getConfigString("messages.permission")));
                Utils.fail(sender);
                return true;
            }
            if (args.length < 2){
                MessageUtils.wrongArgs(cmd, sender);
                return true;
            }
            if (Integer.valueOf(args[1]) == null) {
                sender.sendMessage(MessageUtils.INVALID_NUMBER);
                return true;
            }
            int number = Integer.parseInt(args[1]);
            if (args[0].equalsIgnoreCase("seconds") || args[0].equalsIgnoreCase("distance") || args[0].equalsIgnoreCase("warning")){
                Main.getInstance().getConfig().set("border." + args[0], number);
                Main.getInstance().saveConfig();
                Main.getInstance().reloadConfig();
                sender.sendMessage(Utils.color("&3Successfully changed the &b" + args[0] + " &3value to &b" + number + "&3."));
            } else {
                sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
            }
        }
        return true;
    }
}