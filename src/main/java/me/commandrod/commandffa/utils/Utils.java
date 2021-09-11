package me.commandrod.commandffa.utils;

import org.bukkit.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.commandrod.commandffa.Main.plugin;

public class Utils {

    public static String color(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static void fail(CommandSender sender) {
        if (sender instanceof Player){
            Player p = (Player) sender;
            p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, SoundCategory.MASTER, 1, 1);
        }
    }

    public static void setConfigLocation(String path, Player player) {
        plugin().getConfig().set(path + ".world", player.getLocation().getWorld().getName());
        plugin().getConfig().set(path + ".x", player.getLocation().getX());
        plugin().getConfig().set(path + ".y", player.getLocation().getY());
        plugin().getConfig().set(path + ".z", player.getLocation().getZ());
        plugin().getConfig().set(path + ".pitch", player.getLocation().getPitch());
        plugin().getConfig().set(path + ".yaw", player.getLocation().getYaw());
        plugin().saveConfig();
        plugin().reloadConfig();
    }
    public static Location getConfigLocation(String path){
        try {
            Location loc = new Location(Bukkit.getWorld(plugin().getConfig().getString(path + ".world")), plugin().getConfig().getDouble(path + ".x"), plugin().getConfig().getDouble(path + ".y"), plugin().getConfig().getDouble(path + ".z"), (float) plugin().getConfig().getDouble(path + ".pitch"), (float) plugin().getConfig().getDouble(path + ".yaw"));
            return loc;
        } catch (Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().info("An error occured trying to get " + path + ". Is the line empty?");
        }
        return null;
    }
}