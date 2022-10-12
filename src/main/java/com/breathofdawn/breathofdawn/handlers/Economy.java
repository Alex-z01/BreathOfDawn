package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.EconomyPlayer;
import com.breathofdawn.breathofdawn.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.ServerEvent;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;


public class Economy implements Listener {
    static List<EconomyPlayer> economyPlayerList = new ArrayList<EconomyPlayer>();
    JSONObject obj = new JSONObject();

    public Economy(main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerLoginEvent event){
        Player player = event.getPlayer();
        EconomyPlayer econPlayer = new EconomyPlayer(0, player);
        Bukkit.getLogger().info(player.getName() + " logged in");
        economyPlayerList.add(econPlayer);
        obj.put(econPlayer.getPlayer().getName(), econPlayer.getBalance());
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
