package me.commandrod.commandffa.utils;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.game.GameState;
import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;


public class Utils {

    private static JavaPlugin plugin = Main.getInstance();
    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void fail(CommandSender sender) {
        if (!(sender instanceof Player)) return;
        if (!Utils.getConfigBoolean("settings.sound")) return;
        Player p = (Player) sender;
        p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
    }

    public static void setConfigLocation(String path, Player player) {
        plugin.getConfig().set(path + ".world", player.getLocation().getWorld().getName());
        plugin.getConfig().set(path + ".x", player.getLocation().getX());
        plugin.getConfig().set(path + ".y", player.getLocation().getY());
        plugin.getConfig().set(path + ".z", player.getLocation().getZ());
        plugin.getConfig().set(path + ".yaw", player.getLocation().getYaw());
        plugin.getConfig().set(path + ".pitch", player.getLocation().getPitch());
        plugin.saveConfig();
        plugin.reloadConfig();
    }
    public static Location getConfigLocation(String path){
        Location loc = new Location(Bukkit.getWorld(plugin.getConfig().getString(path + ".world")), plugin.getConfig().getDouble(path + ".x"), plugin.getConfig().getDouble(path + ".y"), plugin.getConfig().getDouble(path + ".z"), (float) plugin.getConfig().getDouble(path + ".yaw"), (float) plugin.getConfig().getDouble(path + ".pitch"));
        return loc;
    }

    public static String getConfigString(String path) {
        return plugin.getConfig().getString(path);
    }

    public static Boolean getConfigBoolean(String path) {
        if (!plugin.getConfig().isSet(path)) return false;
        return plugin.getConfig().getBoolean(path);
    }

    public static boolean isGame(Game game){
        return game.getGameState() != GameState.PRE_GAME;
    }
}