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

    public static void setConfigLocation(String locationName, Player player) {
        plugin().getConfig().set(locationName + ".world", player.getLocation().getWorld().getName());
        plugin().getConfig().set(locationName + ".x", player.getLocation().getX());
        plugin().getConfig().set(locationName + ".y", player.getLocation().getY());
        plugin().getConfig().set(locationName + ".z", player.getLocation().getZ());
        plugin().getConfig().set(locationName + ".pitch", player.getLocation().getPitch());
        plugin().getConfig().set(locationName + ".yaw", player.getLocation().getYaw());
        plugin().saveConfig();
        plugin().reloadConfig();
    }
    public static Location getConfigLocation(String path){
        return new Location(Bukkit.getWorld(plugin().getConfig().getString(path + ".world")), plugin().getConfig().getDouble(path + ".x"), plugin().getConfig().getDouble(path + ".y"), plugin().getConfig().getDouble(path + ".z"), (float) plugin().getConfig().getDouble(path + ".pitch"), (float) plugin().getConfig().getDouble(path + ".yaw"));
    }
}