package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.GLOBALS;
import com.breathofdawn.breathofdawn.main;
import com.breathofdawn.breathofdawn.objects.BoDPlayer;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class PlayerLoginHandler implements Listener {
    
    public PlayerLoginHandler(main plugin) throws IOException {

        Bukkit.getPluginManager().registerEvents(this, plugin);

        LoadJSON();

        if(!Objects.isNull(GLOBALS.PLAYERS))
            DisplayPlayers();
    }

    public void DisplayPlayers()
    {
        GLOBALS.PLAYERS.forEach(boDPlayer -> {
            Bukkit.getLogger().info(boDPlayer.getUUID() + "");
        });
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) {
        BoDPlayer loggedInPlayer = new BoDPlayer(e.getPlayer());

        if(GLOBALS.PLAYERS.size() < 1)
        {
            Bukkit.getLogger().info("FIRST PLAYER");
            GLOBALS.PLAYERS.add(loggedInPlayer);
        }else{
            AtomicBoolean found = new AtomicBoolean(false);
            GLOBALS.PLAYERS.forEach((boDPlayer) -> {

                if(boDPlayer.getUUID().equals(e.getPlayer().getUniqueId()) && !found.get()){
                    // Already documented player
                    Bukkit.getLogger().info("KNOWN PLAYER");
                    found.set(true);
                }
            });
            Bukkit.getLogger().info(found + "");
            if(!found.get())
            {
                // New player
                Bukkit.getLogger().info("NEW PLAYER");
                GLOBALS.PLAYERS.add(loggedInPlayer);
            }
            }
        }

    @EventHandler void onCommand(ServerCommandEvent e)
    {
        if (e.getCommand().equalsIgnoreCase("blist")){
            if(!Objects.isNull(GLOBALS.PLAYERS))
            {
                GLOBALS.PLAYERS.forEach((player) -> {
                    Player p = Bukkit.getPlayer(player.getUUID());
                    OfflinePlayer oP = Bukkit.getOfflinePlayer(player.getUUID());

                    if(Objects.isNull(p)){
                        Bukkit.getLogger().info(oP.getName());
                    }else{
                        Bukkit.getLogger().info(p.getName());
                    }

                });
            }
        }
    }

    public void LoadJSON() {
        String path = "plugins/BreathOfDawn/players.json";
        File file = new File(path);

        // Check if file exists
        if(file.exists())
        {
            // Parse it
            try{
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                FileReader reader = new FileReader(path);

                ArrayList<BoDPlayer> temp = gson.fromJson(reader, new TypeToken<ArrayList<BoDPlayer>>() {}.getType()); // WORKS
                if(Objects.isNull(temp)) {
                    GLOBALS.PLAYERS = new ArrayList<>();
                }else{
                    GLOBALS.PLAYERS = temp;
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if(!Objects.isNull(GLOBALS.PLAYERS))
        {
            for(BoDPlayer player : GLOBALS.PLAYERS){
                Player p = Bukkit.getServer().getPlayer(player.getUUID());
                OfflinePlayer offlineP = Bukkit.getOfflinePlayer(player.getUUID());

                if (Objects.isNull(p))
                {
                    Bukkit.getLogger().info(offlineP.getName());
                }else{
                    Bukkit.getLogger().info(p.getName());
                }
            }
        }
    }

    public void WriteJSON() throws IOException {

        String path = ("plugins/BreathOfDawn/players.json");
        File file = new File(path);

        // Check if file exists
        if (file.exists() && !Objects.isNull(GLOBALS.PLAYERS))
        {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            // Write to file
            FileWriter fw = new FileWriter("plugins/BreathOfDawn/players.json");
            fw.write(gson.toJson(GLOBALS.PLAYERS));
            fw.flush();
            fw.close();
        }
    }

}
