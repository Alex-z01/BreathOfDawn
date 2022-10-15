package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.User;
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

public class PlayerLoginHandler implements Listener {

    private ArrayList<BoDPlayer> _players = new ArrayList<>();

    public PlayerLoginHandler(main plugin) throws IOException {

        Bukkit.getPluginManager().registerEvents(this, plugin);

        LoadJSON();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) throws IOException {
        BoDPlayer loggedInPlayer = new BoDPlayer(e.getPlayer());

        if(_players == null)
        {
            Bukkit.getLogger().info("PLAYERS ARRAY WAS NULL");
            _players = new ArrayList<>();
            _players.add(loggedInPlayer);
            WriteJSON();
            return;
        }

        if(_players.size() == 0){
            Bukkit.getLogger().info("PLAYERS ARRAY WAS EMPTY");
            _players.add(loggedInPlayer);
            WriteJSON();
            return;
        }

        _players.forEach((player) -> {

            if(player.getUUID().equals(loggedInPlayer.getUUID())){
                // Already documented player
                Bukkit.getLogger().info("KNOWN PLAYER");
                return;
            }else{
                // New player
                Bukkit.getLogger().info("NEW PLAYER");
                _players.add(loggedInPlayer);
                try {
                    WriteJSON();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    @EventHandler void onCommand(ServerCommandEvent e)
    {
        if (e.getCommand().equalsIgnoreCase("blist")){
            if(_players != null)
            {
                _players.forEach((player) -> {
                    Player p = Bukkit.getPlayer(player.getUUID());
                    OfflinePlayer oP = Bukkit.getOfflinePlayer(player.getUUID());

                    if(p == null){
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

                _players = gson.fromJson(reader, new TypeToken<ArrayList<BoDPlayer>>() {}.getType()); // WORKS
                //Bukkit.getLogger().info(gson.toJson(_players));
                //_players.forEach((player) -> Bukkit.getLogger().info(player.getUUID() + ""));

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if(_players != null)
        {
            for(BoDPlayer player : _players){
                Player p = Bukkit.getServer().getPlayer(player.getUUID());
                OfflinePlayer offlineP = Bukkit.getOfflinePlayer(player.getUUID());

                if (p == null)
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
        if (file.exists() && _players != null)
        {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            // Write to file
            FileWriter fw = new FileWriter("plugins/BreathOfDawn/players.json");
            fw.write(gson.toJson(_players));
            fw.flush();
            fw.close();
        }
    }

}
