package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.main;
import com.breathofdawn.breathofdawn.objects.BoDPlayer;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class VeinMiner implements Listener {
    private final ArrayList<Material> _validTools = new ArrayList<>();
    private final ArrayList<Material> _validOres = new ArrayList<>();
    private boolean _mineMode = false;

    public VeinMiner(main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        _validTools.add(Material.WOODEN_PICKAXE);
        _validTools.add(Material.STONE_PICKAXE);
        _validTools.add(Material.GOLDEN_PICKAXE);
        _validTools.add(Material.IRON_PICKAXE);
        _validTools.add(Material.DIAMOND_PICKAXE);
        _validTools.add(Material.NETHERITE_PICKAXE);

        _validOres.add(Material.COAL_ORE);
        _validOres.add(Material.COPPER_ORE);
        _validOres.add(Material.IRON_ORE);
        _validOres.add(Material.GOLD_ORE);
        _validOres.add(Material.REDSTONE_ORE);
        _validOres.add(Material.LAPIS_ORE);
        _validOres.add(Material.DIAMOND_ORE);
        _validOres.add(Material.ANCIENT_DEBRIS);
        _validOres.add(Material.DEEPSLATE_COAL_ORE);
        _validOres.add(Material.DEEPSLATE_GOLD_ORE);
        _validOres.add(Material.DEEPSLATE_IRON_ORE);
        _validOres.add(Material.DEEPSLATE_COPPER_ORE);
        _validOres.add(Material.DEEPSLATE_DIAMOND_ORE);
        _validOres.add(Material.DEEPSLATE_LAPIS_ORE);
        _validOres.add(Material.DEEPSLATE_REDSTONE_ORE);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        Player player = e.getPlayer();
        EquipmentSlot hand = e.getHand();
        ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand();
        Action action = e.getAction();

        if(action.isRightClick() && hand == EquipmentSlot.HAND){

            if(_validTools.contains(mainHand.getType())){
                // Switch mode
                Bukkit.getLogger().info("VEIN MINER ACTIVATED");
                _mineMode = !_mineMode;

                String msg = _mineMode ? "Vein Miner activated!" : "Vein Miner deactivated";
                ChatColor col = _mineMode ? ChatColor.GREEN : ChatColor.RED;
                Sound sound = _mineMode ? Sound.BLOCK_NOTE_BLOCK_CHIME : Sound.BLOCK_STONE_BREAK;

                // Sound effect
                player.getWorld().playSound(player.getLocation(), sound, 5, 1);

                // Tell player
                player.sendMessage(col + msg);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Player player = event.getPlayer();
        World world = block.getWorld();

        // Check item held
        PlayerInventory inventory = player.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();

        // If valid chop tool
        if(_validTools.contains(mainHand.getType()) && _mineMode == true){

            // Check for log blocks
            ReplaceBlock(player, block, isOre(block), world, mainHand);
        }


        /* Permission check
        if(!player.hasPermission("bod.treechop")){
            return;
        }
        */
    }

    public boolean isOre(Block block){
        return _validOres.contains(block.getBlockData().getMaterial());
    }

    public boolean DamageItem(Player player, ItemStack tool, Material material){
        ItemMeta meta = tool.getItemMeta(); // The tool meta
        Damageable damageable = (Damageable) meta;
        short maxDurability = tool.getType().getMaxDurability();
        int dmg = damageable.getDamage(); // Current amount of dmg taken
        damageable.setDamage(++dmg);
        tool.setItemMeta((ItemMeta) damageable);

        if(dmg >= maxDurability){
            // Destroy tool
            tool.setAmount(0);
            player.playSound(player.getLocation(), Sound.ENTITY_ITEM_BREAK, 1, 1);
            return false;
        }
        return true;
    }

    public void ReplaceBlock(Player player, Block blck, boolean log, World world, ItemStack tool){
        if (tool.getAmount() == 0){
            return;
        }
        if (log){
            // Break the current log
            blck.breakNaturally();
            DamageItem(player, tool, blck.getBlockData().getMaterial());

            // Check for valid logs in each direction
            Block nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ()); // XLeft
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ()); // XRight
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY(), blck.getZ() - 1); // ZBack
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY(), blck.getZ() + 1); // ZForward
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY() + 1, blck.getZ()); // Up
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY() - 1, blck.getZ()); // Down
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ() + 1);
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ() - 1);
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ() + 1);
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ() - 1);
            ReplaceBlock(player, nextBlock, isOre(nextBlock), world, tool);
        }
    }


}
