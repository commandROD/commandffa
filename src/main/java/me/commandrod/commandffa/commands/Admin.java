package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.game.GameState;
import me.commandrod.commandffa.utils.MessageUtils;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Admin implements CommandExecutor, TabCompleter {

    Game game = Main.getGame();
    JavaPlugin plugin = Main.getInstance();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if (cmd.getName().equalsIgnoreCase("admin")){
            if (!sender.hasPermission("commandffa.admin")){
                sender.sendMessage(Utils.color(Utils.getConfigString("messages.permission")));
                Utils.fail(sender);
                return true;
            }
            if (args.length == 0){
                sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
                return true;
            }
            switch (args[0]) {
                case "reloadconfig":
                    plugin.reloadConfig();
                    sender.sendMessage(Utils.color(Utils.getConfigString("messages.config")));
                    break;
                case "countdown":
                    if (args.length < 2) {
                        sender.sendMessage(MessageUtils.WRONG_ARGS + Utils.color("&c/admin countdown <seconds>"));
                        break;
                    }
                    if (Integer.valueOf(args[1]) == null) {
                        sender.sendMessage(MessageUtils.INVALID_NUMBER);
                        break;
                    }
                    int countdown = Integer.parseInt(args[1]);
                    plugin.getConfig().set("settings.countdown", countdown);
                    plugin.saveConfig();
                    plugin.reloadConfig();
                    sender.sendMessage(Utils.color("&3Successfully changed the &bcountdown &3value."));
                    break;
                case "forceend":
                    if (game.getGameState().equals(GameState.PRE_GAME)){
                        sender.sendMessage(Utils.color("&cThere are currently no running games!"));
                        Utils.fail(sender);
                        break;
                    }
                    for (World world : Bukkit.getWorlds()) {
                        world.getWorldBorder().reset();
                    }
                    for (Player players : Bukkit.getOnlinePlayers()){
                        players.getInventory().clear();
                        players.setGameMode(GameMode.SURVIVAL);
                        players.teleport(Utils.getConfigLocation("lobby-location"));
                    }
                    game.setGameState(GameState.PRE_GAME);
                    game.setPvP(false);
                    game.getAlivePlayers().clear();
                    game.getDeathLocations().clear();
                    game.getKills().clear();
                    sender.sendMessage(Utils.color("&cForce ended the game."));
                    Bukkit.getScheduler().cancelTasks(plugin);
                    break;
                default:
                    sender.sendMessage(Utils.color("&3" + cmd.getDescription() + "\n&b" + cmd.getUsage()));
                    break;
                }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        if (cmd.getName().equalsIgnoreCase("admin")) {
            if (!sender.hasPermission("commandffa.admin")) return Collections.EMPTY_LIST;
            final List<String> oneArgList = Arrays.asList("reloadconfig", "countdown", "forceend");
            final List<String> completions = new ArrayList<>();
            if (args.length == 1){
                StringUtil.copyPartialMatches(args[0], oneArgList, completions);
            }
            Collections.sort(completions);
            return completions;
        }
        return Collections.EMPTY_LIST;
    }
}