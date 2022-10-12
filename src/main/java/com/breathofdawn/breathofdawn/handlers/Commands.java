package com.breathofdawn.breathofdawn.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {

    int MAX_FOOD_LEVEL = 20;
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
        if (cmd.getName().equalsIgnoreCase("heal")){

            if(sender instanceof Player)
            {
                if(args.length == 0){
                    Player player = (Player) sender;
                    Heal(player);
                }
                else {
                    for(String name : args){
                        Player player = Bukkit.getServer().getPlayer(name);
                        if(Heal(player) == false)
                            return false;
                    }
                }
            }else{
                Bukkit.getLogger().info("Can't heal non-player!");
                return false;
            }
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("feed")){
            if(sender instanceof Player)
            {
                if(args.length == 0){
                    Player player = (Player) sender;
                    Feed(player);
                }
                else {
                    for(String name : args){
                        Player player = Bukkit.getServer().getPlayer(name);
                        if(Feed(player) == false)
                            return false;
                    }
                }
            }else{
                Bukkit.getLogger().info("Can't feed non-player!");
                return false;
            }
            return true;
        }

        return false;
    }
    public boolean Feed(Player player){
        if(player != null){
            player.setFoodLevel(MAX_FOOD_LEVEL);
        }else{
            player.sendMessage(ChatColor.RED + "Player not found or offline.");
            return false;
        }
        return true;
    }
    public boolean Heal(Player player){
        if(player != null){
            double max = player.getAttribute(Attribute.GENERIC_MAX_HEALTH).getValue();
            player.setHealth(max);
            player.sendMessage(ChatColor.GREEN + "Healed!");
        }else{
            player.sendMessage(ChatColor.RED + "Player not found or offline.");
            return false;
        }
        return true;
    }
}
