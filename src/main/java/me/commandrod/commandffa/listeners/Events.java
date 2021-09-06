package me.commandrod.commandffa.listeners;

import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class Events implements Listener {
    Game game = new Game();

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        if (game.isGame()) e.setCancelled(true);
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        if (game.isGame()) e.setCancelled(true);
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        Player p = e.getEntity();
        game.getAlivePlayers().remove(p.getUniqueId());
        if (game.isGame()){
            if (game.getAlivePlayers().size() == 1) game.stopGame(p);
            e.setDeathMessage(Utils.color("&c" + p.getName() + " has been eliminated!"));
            e.getDrops().clear();
        }
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        if (game.isGame()){
            p.setGameMode(GameMode.SPECTATOR);
            p.sendTitle(Utils.color("&cYou died!"), "", 5, 40 ,5);
            p.teleport(Utils.getConfigLocation("center-location"));
        }
    }
}