package me.commandrod.commandffa;

import lombok.Getter;
import me.commandrod.commandffa.commands.*;
import me.commandrod.commandffa.game.Game;
import me.commandrod.commandffa.listeners.Events;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private static Game game;
    @Getter
    private static Main instance;

    @Override
    public void onEnable() {
        instance = this;
        game = new Game();
        Bukkit.getPluginManager().registerEvents(new Events(), this);
        this.getCommand("admin").setExecutor(new Admin());
        this.getCommand("admin").setTabCompleter(new Admin());
        this.getCommand("setborder").setExecutor(new SetBorder());
        this.getCommand("setlobby").setExecutor(new SetLobby());
        this.getCommand("setcenter").setExecutor(new SetCenter());
        this.getCommand("start").setExecutor(new Start());
        this.getCommand("revive").setExecutor(new Revive());
        this.saveDefaultConfig();
    }

    @Override
    public void onDisable() {
        instance = null;
        this.saveConfig();
    }
}