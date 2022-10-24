package com.breathofdawn.breathofdawn;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CustomEntity implements Listener {

    public CustomEntity(main plugin)
    {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJump(PlayerJumpEvent e)
    {
        Player player = e.getPlayer();
        World world = player.getWorld();

        world.spawnEntity(player.getLocation(), EntityType.AXOLOTL);
    }

}
