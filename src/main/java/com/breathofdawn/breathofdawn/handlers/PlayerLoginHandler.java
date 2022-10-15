package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.main;
import com.breathofdawn.breathofdawn.objects.BoDPlayer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PlayerLoginHandler implements Listener {

    private ArrayList<BoDPlayer> _players = new ArrayList<>();

    public PlayerLoginHandler(main plugin) throws IOException {

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent e) throws IOException {
        BoDPlayer bodPlayer = new BoDPlayer(e.getPlayer());

        if(_players != null){
            if(_players.contains(bodPlayer)){
                Bukkit.getLogger().info("KNOWN PLAYER");
                return;
            }else{
                // Add the player
                Bukkit.getLogger().info("NEW PLAYER!");
                _players.add(bodPlayer);
                WriteJSON();
            }

        }

        if(_players == null){
            Bukkit.getLogger().info("FIRST PLAYER!!");
            _players.add(bodPlayer);
            WriteJSON();
            return;
        }
    }

    @EventHandler
    public void onCommand(ServerCommandEvent e){
        if (e.getCommand().equals("blist")){

            for(BoDPlayer player : _players){
                e.getSender().sendMessage(ChatColor.YELLOW + "" + player.getPlayer().getName());
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
                Gson gson = new Gson();

                FileReader reader = new FileReader(path);

                List<BoDPlayer> temp = gson.fromJson(reader, new TypeToken<List<BoDPlayer>>(){}.getType());
                for(BoDPlayer p : temp){
                    Bukkit.getLogger().info(p.getUUID()+ "");
                }

            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if(_players != null)
        {
            for(BoDPlayer player : _players){
                Bukkit.getLogger().info("" + player.getPlayer().getName());
            }
        }
    }

    public void WriteJSON() throws IOException {
        String path = ("plugins/BreathOfDawn/players.json");
        File file = new File(path);

        // Check if file exists
        if (file.exists())
        {
            JSONArray jsonArray = new JSONArray();

            for(BoDPlayer player : _players){
                jsonArray.add(player.toJsonObject());
            }

            String json = jsonArray.toJSONString();

            Bukkit.getLogger().info(json);

            FileWriter fw = new FileWriter(file);
            fw.write(json);
            fw.flush();
            fw.close();
        }
    }

}
