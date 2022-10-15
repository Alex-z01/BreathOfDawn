package com.breathofdawn.breathofdawn;

import com.breathofdawn.breathofdawn.handlers.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public final class main extends JavaPlugin {

    @Override
    public void onEnable(){
        // Plugin startup logic
        loadConfig();

        FileManager.CreateDir("plugins/BreathOfDawn");
        FileManager.CreateFile("plugins/BreathOfDawn/players.json");

        //new Economy(this);
        try {
            new PlayerLoginHandler(this).LoadJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        new TreeChopper(this);
        new VeinMiner(this);

        this.getCommand("heal").setExecutor(new Commands());
        this.getCommand("feed").setExecutor(new Commands());
        this.getCommand("balance").setExecutor(new EconomyCommands());
        this.getCommand("setbal").setExecutor(new EconomyCommands());
        this.getCommand("ubal").setExecutor(new EconomyCommands());
        this.getCommand("randbal").setExecutor(new EconomyCommands());
    }

    @Override
    public void onDisable(){
        // Plugin shutdown logic
        Bukkit.getLogger().info("Shutting down");
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}
