package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.main;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class TorchHandler implements Listener {
    public TorchHandler(main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    /** EVENT PRIORITY
     * Lowest
     * Low
     * Normal
     * High
     * Highest
     * -----
     * Monitor
     *
     */

    @EventHandler(priority = EventPriority.LOW)
    public void onTorchPlace_Low(BlockPlaceEvent event){
        if (event.getBlock().getType() == Material.TORCH){
            //event.getBlock().setType(Material.DIAMOND_BLOCK);
            //Bukkit.getLogger().info("CANT PLACE TORCHES");
            // Cancels only this priority event
            //event.setCancelled(true);
        }
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.NORMAL)
    public void onTorchPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.getType() != Material.TORCH) {
            return;
        }

        Bukkit.getLogger().info(block.getType().toString());
    }
}
