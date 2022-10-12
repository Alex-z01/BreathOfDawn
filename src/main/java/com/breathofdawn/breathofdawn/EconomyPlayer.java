package com.breathofdawn.breathofdawn;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class EconomyPlayer {
    private double balance;
    private Player player;

    public EconomyPlayer (double balance, Player player) {
        this.balance = balance;
        this.player = player;
    }

    public void setBalance(double balance){
        this.balance = balance;
    }

    public void setPlayer(Player player){
        this.player = player;
    }

    public double getBalance(){
        return balance;
    }

    public Player getPlayer(){
        return player;
    }
}
