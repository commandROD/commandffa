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

public class Start implements CommandExecutor {

    Game game = new Game();

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("start")){
            if (sender instanceof Player){
                Player p = (Player) sender;
                if (!p.hasPermission("commandffa.start")){
                    sender.sendMessage(Utils.color(Messages.PERMISSION));
                    Utils.fail(sender);
                    return true;
                }
                Location centerLoc = Utils.getConfigLocation("center-location");
                double size = plugin().getConfig().getDouble("border.distance");
                long seconds = plugin().getConfig().getLong("border.seconds");
                int warningTime = plugin().getConfig().getInt("border.warning");
                WorldBorder border = centerLoc.getWorld().getWorldBorder();
                border.setSize(size, seconds);
                border.setWarningTime(warningTime);
                border.setCenter(centerLoc);
                for (Player players : Bukkit.getOnlinePlayers()){
                    game.heal(players, true);
                    game.giveKit(players);
                    game.getAlivePlayers().add(players.getUniqueId());
                    players.setGameMode(GameMode.SURVIVAL);
                    players.teleport(centerLoc);
                }
                game.setGame(true);
                game.countdown(plugin().getConfig().getInt("settings.countdown"));
            } else {
                sender.sendMessage(Utils.color("&a[CommandFFA] Only players may execute this command!"));
            }
        }
        return true;
    }
}