package com.breathofdawn.breathofdawn.objects;

import com.google.gson.Gson;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class BoDPlayer implements Serializable {

    private boolean _mineMode, _chopMode;
    private UUID _uuid;

    public BoDPlayer(Player p){
        _uuid = p.getUniqueId();
        _mineMode = false;
        _chopMode = false;
    }

    public UUID getUUID() { return _uuid; }

    public boolean getMineMode(){
        return _mineMode;
    }

    public boolean getChopMode(){
        return _chopMode;
    }

    public void setMineMode(boolean active){
        _mineMode = active;
    }

    public void setChopMode(boolean active){
        _chopMode = active;
    }
}
