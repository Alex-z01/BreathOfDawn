package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class TreeChopper implements Listener {
    private ArrayList<Material> _validTools = new ArrayList<Material>();

    public TreeChopper(main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        _validTools.add(Material.WOODEN_AXE);
        _validTools.add(Material.STONE_AXE);
        _validTools.add(Material.IRON_AXE);
        _validTools.add(Material.GOLDEN_AXE);
        _validTools.add(Material.DIAMOND_AXE);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Material material = block.getBlockData().getMaterial();
        Player player = event.getPlayer();

        // Check item held
        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();

        if(_validTools.contains(mainHand.getType())){
            Bukkit.getLogger().info("PLAYER USED AXE!");
            Bukkit.getLogger().info(block.toString());

            World world = block.getWorld();
            if(world.getBlockAt(block.getX(), block.getY() + 1, block.getZ()) != null){
                Block b = world.getBlockAt(block.getX(), block.getY() + 1, block.getZ());
                b.setType(Material.AIR);
            }
        }


        /* Permission check
        if(!player.hasPermission("bod.treechop")){
            return;
        }
        */

    }
}
