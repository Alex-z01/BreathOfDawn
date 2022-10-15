package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.objects.EconomyPlayer;
import com.breathofdawn.breathofdawn.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.plugin.Plugin;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class Economy implements Listener {
    private Plugin plugin = main.getPlugin(main.class);
    static List<EconomyPlayer> economyPlayerList = new ArrayList<EconomyPlayer>();
    JSONObject obj = new JSONObject();

    public Economy(main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        // Check if player listed in config
        if(plugin.getConfig().contains(player.getName())){
            // If player exists add accordingly
            Double balance = (Double) plugin.getConfig().get(player.getName());
            EconomyPlayer economyPlayer = new EconomyPlayer(balance, player);

            economyPlayerList.add(economyPlayer);
            obj.put(economyPlayer.getPlayer().getName(), economyPlayer.getBalance());

            player.sendMessage(ChatColor.BLUE + "Welcome back!");
        }
        else{
            EconomyPlayer economyPlayer = new EconomyPlayer(0, player);

            economyPlayerList.add(economyPlayer);
            obj.put(economyPlayer.getPlayer().getName(), economyPlayer.getBalance());

            player.sendMessage(ChatColor.BLUE + "Welcome" + player.getName() + "!");
        }


    }

    @EventHandler
    public void onShutDown(PluginDisableEvent event) throws IOException {
        Bukkit.getLogger().info(ChatColor.RED + "SHUTTING DOWN");
        StringWriter out = new StringWriter();
        obj.writeJSONString(out);

        FileWriter file = new FileWriter("plugins/Economy/economy.json");
        file.write(out.toString());
        file.close();
    }
}
