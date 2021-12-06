package me.commandrod.commandffa.utils;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class MessageUtils {

    public static String NOT_PLAYER = Utils.color("&c[CommandFFA] This command may only be executed by a player!");
    public static String WRONG_ARGS = Utils.color("&cWrong usage!\n");
    public static String INVALID_NUMBER = Utils.color("You must provide a valid number!");
    public static String INVALID_PLAYER = Utils.color("You must provide a valid online player!");

    public static void cmdUsage(Command cmd, CommandSender sender) {
        sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
    }

    public static void wrongArgs(Command cmd, CommandSender sender) {
        sender.sendMessage(WRONG_ARGS + Utils.color("&c" + cmd.getUsage()));
    }
}