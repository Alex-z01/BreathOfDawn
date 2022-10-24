package com.breathofdawn.breathofdawn.objects;

import org.bukkit.inventory.ItemStack;

public class ChanceItem {
    ItemStack item;
    float chance;

    public ChanceItem(ItemStack item, float chance){
        this.item = item;
        this.chance = chance;
    }
}
