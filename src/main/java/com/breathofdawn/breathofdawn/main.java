package com.breathofdawn.breathofdawn;

import com.breathofdawn.breathofdawn.handlers.*;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONArray;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public final class main extends JavaPlugin {

    PlayerLoginHandler playerLoginHandler;
    Economy economy;
    Excavation excavation;
    MobDropHandler mobDropHandler;
    CustomEntity customEntity;

    @Override
    public void onEnable(){
        // Plugin startup logic
        FileManager.CreateDir("plugins/BreathOfDawn");
        FileManager.CreateFile( getDataFolder().getPath() + "/loot_tables.json");
        FileManager.CreateFile(getDataFolder().getPath() + "/players.json");

        try {
            playerLoginHandler = new PlayerLoginHandler(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            mobDropHandler = new MobDropHandler(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        excavation = new Excavation(this);
        //economy = new Economy(this);

        this.getCommand("heal").setExecutor(new Commands());
        this.getCommand("feed").setExecutor(new Commands());
        this.getCommand("balance").setExecutor(new EconomyCommands());
        this.getCommand("setbal").setExecutor(new EconomyCommands());
        this.getCommand("ubal").setExecutor(new EconomyCommands());
        this.getCommand("randbal").setExecutor(new EconomyCommands());

        /*
        try {
            JSONTESTING();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
    }

    @Override
    public void onDisable(){
        // Plugin shutdown logic
        try {
            playerLoginHandler.WriteJSON();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Bukkit.getLogger().info("Shutting down");
    }

    public void JSONTESTING() throws IOException {
        Car car = new Car("honda", 100);
        User user = new User("Alex", 21, car);
        User user2 = new User("Billy", 17, car);
        ArrayList<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        JSONArray jsonArray = new JSONArray();
        //jsonArray.add(user);
        //jsonArray.add(user2);
        jsonArray.addAll(users);

        GsonBuilder builder = new GsonBuilder();
        builder.setPrettyPrinting();
        Gson gson = builder.create();
        // Write to file
        FileWriter fw = new FileWriter("plugins/BreathOfDawn/players.json");
        //jsonArray.writeJSONString(fw); // Using jsonarray method DOESNT WORK
        //fw.write(gson.toJson(jsonArray)); // Using GSON method and JSONArray, WORKS
        fw.write(gson.toJson(users)); // Using GSON method and ArrayList<User>,
        fw.flush();
        fw.close();
        //Bukkit.getLogger().info(gson.toJson(jsonArray));

        FileReader fr = new FileReader("plugins/BreathOfDawn/players.json");
        Bukkit.getLogger().info(fr.getClass() + "");
        Bukkit.getLogger().info(fr.toString());
        ArrayList<User> fromJson = gson.fromJson(fr, new TypeToken<ArrayList<User>>() {}.getType()); // WORKS
        //Bukkit.getLogger().info(fromJson.get(0).getName()); // WORKS
        fromJson.forEach((usr) -> Bukkit.getLogger().info(usr.getName()));
        //JSONArray jsonArray1 = gson.fromJson(fr, JSONArray.class); // Converts the json file into JSONArray - WORKS
        //Bukkit.getLogger().info(gson.toJson(jsonArray1)); // Works
        //jsonArray1.forEach((usr) -> Bukkit.getLogger().info(usr.toString())); // Works
    }

    public void loadConfig(){
        getConfig().options().copyDefaults(true);
        saveConfig();
    }
}

