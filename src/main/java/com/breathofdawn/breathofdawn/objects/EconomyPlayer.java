package com.breathofdawn.breathofdawn.objects;

import com.breathofdawn.breathofdawn.main;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class EconomyPlayer {
    private Plugin plugin = main.getPlugin(main.class);
    private double balance;
    private Player player;

    public EconomyPlayer (double balance, Player player) {
        this.balance = balance;
        this.player = player;

        plugin.getConfig().set(this.player.getName(), this.balance);
        plugin.saveConfig();
    }

    public void setBalance(double balance){
        this.balance = balance;

        plugin.getConfig().set(this.player.getName(), this.balance);
        plugin.saveConfig();
    }

    public double getBalance(){
        return balance;
    }

    public Player getPlayer(){
        return player;
    }
}
