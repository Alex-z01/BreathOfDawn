package com.breathofdawn.breathofdawn.objects;

import org.bukkit.entity.EntityType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LootTable implements Serializable {

    EntityType type;
    List<ChanceItem> chance_items;

    public LootTable(){
        this.type = EntityType.ZOMBIE;
        this.chance_items = new ArrayList<>();
    }

    public void setType(EntityType type){
        this.type = type;
    }

    public void addItem(ChanceItem item){
        chance_items.add(item);
    }
}
