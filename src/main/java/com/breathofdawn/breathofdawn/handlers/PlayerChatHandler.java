package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.main;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerChatHandler implements Listener {
    public PlayerChatHandler(main plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onChat(AsyncChatEvent event){
        Bukkit.getLogger().info(event.message().toString());
        Bukkit.getLogger().info("Someone typed in chat");
    }

}
