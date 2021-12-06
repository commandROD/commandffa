package me.commandrod.commandffa.listeners;

import me.commandrod.commandffa.Main;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.game.GameState;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Events implements Listener {

    Game game = Main.getGame();

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (Utils.isGame(game) && !p.hasPermission("commandffa.admin") && !game.getAlivePlayers().contains(p.getUniqueId())){
            p.sendMessage(Utils.color("&cBlock interactions are disabled!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        if (Utils.isGame(game) && !p.hasPermission("commandffa.admin") && !game.getAlivePlayers().contains(p.getUniqueId())){
            p.sendMessage(Utils.color("&cBlock interactions are disabled!"));
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        if (game.getGameState().equals(GameState.GAME)){
            e.setDeathMessage("");
            Player p = e.getEntity();
            Player killer = e.getEntity().getKiller();
            if (killer != null){
                game.setPlayerKills(killer, game.getPlayerKills(killer) + 1);
                game.getDeathLocations().put(p, p.getLocation());
            }
            game.eliminate(p);
            e.getDrops().clear();
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    if (game.getAlivePlayers().size() == 1){
                        game.stopGame(Bukkit.getPlayer(game.getAlivePlayers().get(0)));
                    }
                }
            }, 60);
        }
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if (!Utils.isGame(game)) return;
        game.eliminate(p);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        if (!Utils.isGame(game)) return;
        game.eliminate(p);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (game.getGameState().equals(GameState.GAME) || game.getGameState().equals(GameState.ENDING)){
            p.getInventory().clear();
            p.sendTitle(Utils.color("&cYou died!"), "", 5, 40 ,5);
            // I actually despise this code and I'm sorry for putting it in. I had to because of essentials / other plugins that teleport you upon respawn.
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.teleport(Utils.getConfigLocation("center-location"));
                }
            }, 1);
            Bukkit.getScheduler().runTaskLater(Main.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.setGameMode(GameMode.SPECTATOR);
                }
            }, 1);
        }
    }

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent e){
        if (!game.getGameState().equals(GameState.PRE_GAME) && !game.isPvP() && e.getEntityType().equals(EntityType.PLAYER)) e.setCancelled(true);
    }
}