package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.main;
import com.breathofdawn.breathofdawn.objects.ChanceItem;
import com.breathofdawn.breathofdawn.objects.LootTable;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MobDropHandler implements Listener {

    private main plugin;

    private LootTable zombieLootTable = new LootTable();

    public MobDropHandler(main plugin) throws IOException {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        this.plugin = plugin;

        zombieLootTable.setType(EntityType.ZOMBIE);
        zombieLootTable.addItem(new ChanceItem(new ItemStack(Material.GOLD_INGOT), 50));
        WriteJSON();
        LoadJSON();

    }

    @EventHandler
    public void onMobKill(EntityDeathEvent e){
        if(e.getEntity().getKiller() instanceof Player)
        {
            if(e.getEntity().getType() == EntityType.ZOMBIE)
            {
                //ItemStack drop = DecideDrop();

            }
        }
    }

    public ItemStack DecideDrop(List<ItemStack> items){

        return null;
    }

    public void LoadJSON() {
        String path = plugin.getDataFolder() + "/loot_tables.json";
        File file = new File(path);

        // Check if file exists
        if (file.exists()) {
            // Parse it
            try {
                GsonBuilder builder = new GsonBuilder();
                builder.setPrettyPrinting();
                Gson gson = builder.create();

                FileReader reader = new FileReader(path);

                LootTable temp = gson.fromJson(reader, new TypeToken<ArrayList<LootTable>>() {}.getType()); // WORKS
                Bukkit.getLogger().info(temp.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void WriteJSON() throws IOException {

        String path = plugin.getDataFolder() + "/loot_tables.json";
        File file = new File(path);

        // Check if file exists
        if (file.exists() && !Objects.isNull(zombieLootTable))
        {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();

            // Write to file
            FileWriter fw = new FileWriter(path);
            fw.write(gson.toJson(zombieLootTable));
            Bukkit.getLogger().info(gson.toJson(zombieLootTable));
            fw.flush();
            fw.close();
        }
    }
}
