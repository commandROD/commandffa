package me.commandrod.commandffa.scoreboardmanager;

import me.commandrod.commandffa.commands.Start;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import static me.commandrod.commandffa.Main.plugin;

public class ScoreboardManager {
    public static void newScoreboard(Game game, Player player){
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = scoreboard.registerNewObjective("test", "dummy");
        obj.setDisplayName(Utils.color(Utils.getConfigString("settings.servername")));
        Score blank = obj.getScore(Utils.color("&c "));
        Score alivePlayersScore = obj.getScore(Utils.color(Utils.getConfigString("settings.remaining-players").replace("%remaining-players%", String.valueOf(game.getAlivePlayers().size()))));
        Score killScore = obj.getScore(Utils.color(Utils.getConfigString("settings.kills").replace("%kills%", game.getPlayerKills(player).toString())));
        Score blank1 = obj.getScore(Utils.color("&3 "));
        Score ip = obj.getScore(Utils.color(Utils.getConfigString("settings.serverip")));
        blank.setScore(5);
        alivePlayersScore.setScore(4);
        killScore.setScore(3);
        blank1.setScore(2);
        ip.setScore(1);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(scoreboard);
    }
    public static void scoreboardRunnable(Game game){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (game.isGame()){
                    for (Player players : Bukkit.getOnlinePlayers()){
                        newScoreboard(game, players);
                    }
                } else {
                    for (Player players : Bukkit.getOnlinePlayers()){
                        players.setScoreboard(Bukkit.getScoreboardManager().getMainScoreboard());
                    }
                }
            }
        };
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin(), runnable, 0, 20);
    }
}