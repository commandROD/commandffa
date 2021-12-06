package me.commandrod.commandffa.commands;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.game.GameState;
import me.commandrod.commandffa.utils.MessageUtils;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Start implements CommandExecutor {

    Game game = Main.getGame();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")){
            if (!(sender instanceof Player)) {
                sender.sendMessage(MessageUtils.NOT_PLAYER);
                return true;
            }
            Player p = (Player) sender;
            if (!p.hasPermission("commandffa.start")){
                sender.sendMessage(Utils.color(Utils.getConfigString("messages.permission")));
                Utils.fail(sender);
                return true;
            }
            if (Utils.isGame(game)){
                sender.sendMessage(Utils.color("&cThere is currently a game running!"));
                Utils.fail(sender);
                return true;
            }
            if (Bukkit.getOnlinePlayers().size() <= 1){
                sender.sendMessage(Utils.color("&cThere are not enough players online!"));
                Utils.fail(sender);
                return true;
            }
            game.getKills().clear();
            game.getAlivePlayers().clear();
            game.getDeathLocations().clear();
            p.getWorld().setGameRule(GameRule.DO_IMMEDIATE_RESPAWN, true);
            Location centerLoc = Utils.getConfigLocation("center-location");
            double size = Main.getInstance().getConfig().getDouble("border.distance");
            long seconds = Main.getInstance().getConfig().getLong("border.seconds");
            int warningTime = Main.getInstance().getConfig().getInt("border.warning");
            WorldBorder border = centerLoc.getWorld().getWorldBorder();
            border.setSize(size);
            border.setSize(10, seconds);
            border.setWarningTime(warningTime);
            border.setCenter(centerLoc);
            for (Player players : Bukkit.getOnlinePlayers()){
                game.heal(players, true);
                game.giveKit(players);
                game.getAlivePlayers().add(players.getUniqueId());
                game.getKills().put(players, 0);
                players.setGameMode(GameMode.SURVIVAL);
                players.teleport(centerLoc);
            }
            game.setGameState(GameState.GAME);
            game.countdown(Main.getInstance().getConfig().getInt("settings.countdown"));
        }
        return true;
    }
}