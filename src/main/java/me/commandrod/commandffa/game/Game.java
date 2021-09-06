package me.commandrod.commandffa.game;

import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static me.commandrod.commandffa.Main.plugin;

public class Game {

    private boolean game = false;
    public List<UUID> alivePlayers = new ArrayList<>();

    public void countdown(int seconds) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin(), new Runnable() {
            int countdown = seconds + 1;
            @Override
            public void run() {
                if (countdown >= -1) countdown--;
                for (Player players : Bukkit.getOnlinePlayers()){
                    UUID uuid = players.getUniqueId();
                    getAlivePlayers().add(uuid);
                }
                for (UUID uuid : getAlivePlayers()) {
                    Player players = Bukkit.getPlayer(uuid);
                    if (countdown > 0) players.sendTitle(Utils.color("&3Match starts in " + countdown), Utils.color("&bGood luck!"), 5, 20, 5);
                    if (countdown == 0){
                        players.sendTitle(Utils.color("&3Good luck!"), Utils.color("&bTry to be the last one standing!"), 5, 20, 5);
                    }
                }
            }
        }, 0, 20);
    }

    public void stopGame(Player player){
        setGame(false);
        for (UUID uuid : this.getAlivePlayers()){
            Player players = Bukkit.getPlayer(uuid);
            players.sendTitle(Utils.color("&3" + player.getName() + " won the game!"), Utils.color("&bGG!"), 5, 20 ,5);
            heal(players, true);
        }
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin(), new Runnable() {
            int fireworkCount = 20;
            @Override
            public void run() {
                if (fireworkCount != 0) {
                    player.getWorld().spawnEntity(player.getLocation(), EntityType.FIREWORK, CreatureSpawnEvent.SpawnReason.COMMAND);
                    fireworkCount--;
                } else {
                    getAlivePlayers().forEach(uuid -> {
                        Player players = Bukkit.getPlayer(uuid);
                        players.teleport(Utils.getConfigLocation("lobby-location"));
                    });
                    Bukkit.getScheduler().cancelTasks(plugin());
                }
            }
        }, 0 ,10);
        player.getWorld().getWorldBorder().reset();
        this.getAlivePlayers().clear();
        this.countdown(plugin().getConfig().getInt("settings.countdown"));
    }

    public void heal(Player player, boolean clearInventory) {
        player.setHealth(20);
        player.setFoodLevel(20);
        if (clearInventory) player.getInventory().clear();
    }

    public void giveKit(Player player) {
        player.getInventory().addItem(new ItemStack(Material.DIAMOND_AXE));
        player.getInventory().addItem(new ItemStack(Material.COOKED_BEEF));
        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
    }

    public boolean isGame() {
        return this.game;
    }
    public List<UUID> getAlivePlayers() {
        return this.alivePlayers;
    }

    public void setGame(boolean game) {
        this.game = game;
    }

    public void setAlivePlayers(List<UUID> alivePlayers) {
        this.alivePlayers = alivePlayers;
    }
}