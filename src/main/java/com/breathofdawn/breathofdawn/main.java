package com.breathofdawn.breathofdawn;

import com.breathofdawn.breathofdawn.handlers.*;
import com.sun.source.tree.Tree;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class main extends JavaPlugin {

    @Override
    public void onEnable(){
        // Plugin startup logic
        Bukkit.getLogger().info("Hello World");

        FileManager.CreateDir("plugins/Economy");
        FileManager.CreateFile("plugins/Economy/economy.json");

        new Economy(this);
        new TreeChopper(this);
        //new TorchHandler(this);
        //new PlayerChatHandler(this);

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
}
