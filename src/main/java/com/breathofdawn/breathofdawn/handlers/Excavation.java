package com.breathofdawn.breathofdawn.handlers;

import com.breathofdawn.breathofdawn.GLOBALS;
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
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class Excavation implements Listener {
    private final ArrayList<Material> _validMineTools = new ArrayList<>();
    private final ArrayList<Material> _validChopTools = new ArrayList<>();
    private final ArrayList<Material> _validOres = new ArrayList<>();
    private final ArrayList<Material> _validLogs = new ArrayList<>();

    public Excavation(main plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        MinerInitialize();
        ChopperInitialize();
    }

    void MinerInitialize()
    {
        _validMineTools.add(Material.WOODEN_PICKAXE);
        _validMineTools.add(Material.STONE_PICKAXE);
        _validMineTools.add(Material.GOLDEN_PICKAXE);
        _validMineTools.add(Material.IRON_PICKAXE);
        _validMineTools.add(Material.DIAMOND_PICKAXE);
        _validMineTools.add(Material.NETHERITE_PICKAXE);

        _validOres.add(Material.COAL_ORE);
        _validOres.add(Material.COPPER_ORE);
        _validOres.add(Material.IRON_ORE);
        _validOres.add(Material.GOLD_ORE);
        _validOres.add(Material.REDSTONE_ORE);
        _validOres.add(Material.LAPIS_ORE);
        _validOres.add(Material.DIAMOND_ORE);
        _validOres.add(Material.EMERALD_ORE);
        _validOres.add(Material.ANCIENT_DEBRIS);
        _validOres.add(Material.DEEPSLATE_COAL_ORE);
        _validOres.add(Material.DEEPSLATE_COPPER_ORE);
        _validOres.add(Material.DEEPSLATE_IRON_ORE);
        _validOres.add(Material.DEEPSLATE_GOLD_ORE);
        _validOres.add(Material.DEEPSLATE_REDSTONE_ORE);
        _validOres.add(Material.DEEPSLATE_LAPIS_ORE);
        _validOres.add(Material.DEEPSLATE_DIAMOND_ORE);
        _validOres.add(Material.DEEPSLATE_EMERALD_ORE);
        _validOres.add(Material.NETHER_GOLD_ORE);
        _validOres.add(Material.NETHER_QUARTZ_ORE);
    }

    void ChopperInitialize()
    {
        _validChopTools.add(Material.WOODEN_AXE);
        _validChopTools.add(Material.STONE_AXE);
        _validChopTools.add(Material.IRON_AXE);
        _validChopTools.add(Material.GOLDEN_AXE);
        _validChopTools.add(Material.DIAMOND_AXE);
        _validChopTools.add(Material.NETHERITE_PICKAXE);

        _validLogs.add(Material.ACACIA_LOG);
        _validLogs.add(Material.BIRCH_LOG);
        _validLogs.add(Material.JUNGLE_LOG);
        _validLogs.add(Material.MANGROVE_LOG);
        _validLogs.add(Material.OAK_LOG);
        _validLogs.add(Material.SPRUCE_LOG);
        _validLogs.add(Material.DARK_OAK_LOG);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        // Get the player that fired this event
        Player currentPlayer = e.getPlayer();

        // Find this player's BoDPlayer object entry in the list
        BoDPlayer boDPlayer = FindMatchingPlayer(currentPlayer);

        if(Objects.isNull(boDPlayer)){
            e.setCancelled(true);
        }

        EquipmentSlot hand = e.getHand(); // Get the hand that fired the event
        ItemStack mainHand = e.getPlayer().getInventory().getItemInMainHand(); // Get the mainhand item
        Action action = e.getAction(); // Get the action (key press)

        if(action.isRightClick() && hand == EquipmentSlot.HAND){

            // IF MINE TOOL (PICKAXE)
            if(_validMineTools.contains(mainHand.getType())){

                ToggleMineMode(boDPlayer);
            }

            // IF CHOP TOOL (AXE)
            if(_validChopTools.contains(mainHand.getType())){

                ToggleChopMode(boDPlayer);
            }
        }
    }

    public BoDPlayer FindMatchingPlayer(Player target)
    {
        AtomicReference<BoDPlayer> res = new AtomicReference<BoDPlayer>(null);
        GLOBALS.PLAYERS.forEach((boDPlayer) -> {
            if(boDPlayer.getUUID().equals(target.getUniqueId()))
            {
                res.set(boDPlayer);
            }
        });

        return res.get();
    }

    public void ToggleMineMode(BoDPlayer boDPlayer)
    {
        boDPlayer.setMineMode(!boDPlayer.getMineMode());

        Player player = boDPlayer.getPlayer();

        String msg = boDPlayer.getMineMode() ? "Vein Miner activated!" : "Vein Miner deactivated";
        ChatColor col = boDPlayer.getMineMode() ? ChatColor.GREEN : ChatColor.RED;
        Sound sound = boDPlayer.getMineMode() ? Sound.BLOCK_NOTE_BLOCK_CHIME : Sound.BLOCK_STONE_BREAK;

        // Sound effect
        player.getWorld().playSound(player.getLocation(), sound, 5, 1);

        // Tell player
        player.sendMessage(col + msg);
    }

    public void ToggleChopMode(BoDPlayer boDPlayer)
    {
        boDPlayer.setChopMode(!boDPlayer.getChopMode());

        Player player = boDPlayer.getPlayer();

        String msg = boDPlayer.getChopMode() ? "Tree Chopper activated!" : "Tree Chopper deactivated";
        ChatColor col = boDPlayer.getChopMode() ? ChatColor.GREEN : ChatColor.RED;
        Sound sound = boDPlayer.getChopMode() ? Sound.BLOCK_NOTE_BLOCK_CHIME : Sound.BLOCK_WOOD_BREAK;

        // Sound effect
        player.getWorld().playSound(player.getLocation(), sound, 5, 1);

        // Tell player
        player.sendMessage(col + msg);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event){
        Block block = event.getBlock();
        Player currentPlayer = event.getPlayer();
        BoDPlayer boDPlayer = FindMatchingPlayer(currentPlayer);
        World world = block.getWorld();

        // Check item held
        PlayerInventory inventory = currentPlayer.getInventory();
        ItemStack mainHand = inventory.getItemInMainHand();

        // If valid mine tool
        if(_validMineTools.contains(mainHand.getType()) && boDPlayer.getMineMode()){

            // Replace ore blocks
            ReplaceMineBlock(currentPlayer, block, isOre(block), world, mainHand);
        }

        // If valid chop tool
        if(_validChopTools.contains(mainHand.getType()) && boDPlayer.getChopMode()){
            // Replace ore blocks
            ReplaceChopBlock(currentPlayer, block, isOre(block), world, mainHand);
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

    public boolean isLog(Block block){
        return _validLogs.contains(block.getBlockData().getMaterial());
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

    public void ReplaceMineBlock(Player player, Block blck, boolean mine, World world, ItemStack tool){
        if (tool.getAmount() == 0){
            return;
        }
        if (mine){
            // Break the current log
            blck.breakNaturally();
            DamageItem(player, tool, blck.getBlockData().getMaterial());

            // Check for valid logs in each direction
            Block nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ()); // XLeft
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ()); // XRight
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY(), blck.getZ() - 1); // ZBack
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY(), blck.getZ() + 1); // ZForward
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY() + 1, blck.getZ()); // Up
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY() - 1, blck.getZ()); // Down
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ() + 1);
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ() - 1);
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ() + 1);
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ() - 1);
            ReplaceMineBlock(player, nextBlock, isOre(nextBlock), world, tool);
        }
    }

    public void ReplaceChopBlock(Player player, Block blck, boolean log, World world, ItemStack tool){
        if (tool.getAmount() == 0){
            return;
        }
        if (log){
            // Break the current log
            blck.breakNaturally();
            DamageItem(player, tool, blck.getBlockData().getMaterial());

            // Check for valid logs in each direction
            Block nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ()); // XLeft
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ()); // XRight
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY(), blck.getZ() - 1); // ZBack
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY(), blck.getZ() + 1); // ZForward
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX(), blck.getY() + 1, blck.getZ()); // Up
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ() + 1);
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() + 1, blck.getY(), blck.getZ() - 1);
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ() + 1);
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);

            nextBlock = world.getBlockAt(blck.getX() - 1, blck.getY(), blck.getZ() - 1);
            ReplaceChopBlock(player, nextBlock, isLog(nextBlock), world, tool);
        }
    }

}
