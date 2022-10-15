package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.objects.EconomyPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class EconomyCommands implements CommandExecutor {

    CommandSender sender;
    Command command;
    String label;
    String[] args;

    Player player;
    EconomyPlayer econPlayer;

    public void init(CommandSender sender, Command command, String label, String[] args){
        this.sender = sender;
        this.command = command;
        this.label = label;
        this.args = args;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        init(sender, command, label, args);

        player = GetPlayer();
        if(player != null){
            econPlayer = GetEconPlayer();
        }

        if (command.getName().equalsIgnoreCase("balance")) {
            if(player == null){
                return false;
            }
            Balance();
            return true;
        }

        if (command.getName().equalsIgnoreCase("setbal")) {
            if(args.length != 2){
                return false;
            }
            double amount = Double.parseDouble(args[1]);
            SetBalance(amount);
            return true;
        }

        if (command.getName().equalsIgnoreCase("ubal")) {
            if(args.length != 2){
                return false;
            }
            double amount = Double.parseDouble(args[1]);
            UpdateBalance(amount);
            return true;
        }

        if (command.getName().equalsIgnoreCase("randbal")){
            if(player == null){
                return false;
            }
            RandomBalance();
            return true;
        }

        return false;
    }

    // Returns the current player, sender or args
    public Player GetPlayer(){
        // Check if sender is literal player
        if(sender instanceof Player){
            // Empty args means sender is player
            if(args.length == 0){
                return (Player) sender;
            } else {
                // Otherwise player must be 1st arg
                if(Bukkit.getServer().getPlayer(args[0]) != null){
                    return Bukkit.getServer().getPlayer(args[0]);
                }else{
                    // If 1st arg is not player
                    sender.sendMessage(ChatColor.RED + "Bad arg");
                    return null;
                }
            }
        // Otherwise sender is non-player
        }else{
            // Player arg must be filled
            if(args.length >= 1){
                // 1st arg must be player
                if(Bukkit.getServer().getPlayer(args[0]) != null){
                    return Bukkit.getServer().getPlayer(args[0]);
                }else{
                    // If 1st arg is not player
                    sender.sendMessage(ChatColor.RED + "Not valid player or player offline");
                    return null;
                }
            }else{
                // Otherwise return null
                sender.sendMessage(ChatColor.RED + "Missing args");
                return null;
            }
        }
    }

    // Returns the current EconomyPlayer object
    public EconomyPlayer GetEconPlayer(){
        // Go through EconomyPlayer list and look for matching player
        for(EconomyPlayer ep : Economy.economyPlayerList){
            // On match
            if(ep.getPlayer() == player){
                return ep;
            }
        }
        // If not found
        return null;
    }

    public void Balance(){
        if(GetEconPlayer() != null){
            sender.sendMessage(ChatColor.BLUE + "Balance: " + GetEconPlayer().getBalance());
            return;
        }
        sender.sendMessage(ChatColor.RED + "Player " + args[0] + " not found.");
    }

    public void SetBalance(double amount){
        if(econPlayer != null){
            econPlayer.setBalance(amount);
            sender.sendMessage(ChatColor.BLUE + econPlayer.getPlayer().getName() + "'s set to " + amount);
        }
    }

    public void UpdateBalance(double amount){
        econPlayer.setBalance(econPlayer.getBalance() + amount);
        sender.sendMessage(ChatColor.BLUE + "" + econPlayer.getBalance());
    }

    public void RandomBalance(){
        Random rand = new Random();
        double amount = Math.round(rand.nextDouble(100000));
        econPlayer.setBalance(amount);
    }
}
