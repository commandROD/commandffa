package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.utils.Messages;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.commandrod.commandffa.Main.plugin;

public class Admin implements CommandExecutor {

    Game game = Main.getGame();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("admin")){
            if (!sender.hasPermission("commandffa.admin")){
                sender.sendMessage(Utils.color(Utils.getConfigString("messages.permission")));
                Utils.fail(sender);
                return true;
            }
            if (args.length >= 1){
                switch (args[0]) {
                    case "reloadconfig":
                        plugin().reloadConfig();
                        sender.sendMessage(Utils.color(Utils.getConfigString("messages.config")));
                        break;
                    case "countdown":
                        if (args.length >= 2) {
                            if (Integer.valueOf(args[1]) != null) {
                                int countdown = Integer.parseInt(args[1]);
                                plugin().getConfig().set("settings.countdown", countdown);
                                plugin().saveConfig();
                                plugin().reloadConfig();
                                sender.sendMessage(Utils.color("&3Successfully changed the &bcountdown &3value."));
                            }
                        } else {
                            sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
                        }
                        break;
                    case "forceend":
                        for (World world : Bukkit.getWorlds()) {
                            world.getWorldBorder().reset();
                        }
                        for (Player players : Bukkit.getOnlinePlayers()){
                            players.getInventory().clear();
                            players.setGameMode(GameMode.SURVIVAL);
                            players.teleport(Utils.getConfigLocation("lobby-location"));
                        }
                        game.setGame(false);
                        game.setPvP(false);
                        game.getAlivePlayers().clear();
                        game.getDeathLocations().clear();
                        game.getKills().clear();
                        sender.sendMessage(Utils.color("&cForce ended the game."));
                        break;
                }
            } else {
                sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
            }
        }
        return true;
    }
}