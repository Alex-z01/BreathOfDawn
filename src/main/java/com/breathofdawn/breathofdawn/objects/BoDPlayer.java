package com.breathofdawn.breathofdawn.objects;

import com.google.gson.Gson;
import org.bukkit.configuration.serialization.SerializableAs;
import org.bukkit.entity.Player;
import org.json.simple.JSONObject;

import java.io.Serializable;
import java.util.UUID;

public class BoDPlayer implements Serializable {

    private Player _player;
    private boolean _mineMode, _chopMode;
    private UUID _uuid;

    public BoDPlayer(Player p){
        _player = p;
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

    public Player getPlayer(){
        return _player;
    }

    public void setMineMode(boolean active){
        _mineMode = active;
    }

    public void setChopMode(boolean active){
        _chopMode = active;
    }

    public JSONObject toJsonObject(){
        JSONObject obj = new JSONObject();

        obj.put("Player", _player.getName());
        obj.put("UUID", _uuid + "");
        obj.put("minemode", _mineMode + "");
        obj.put("chopmode", _chopMode + "");

        return obj;
    }

    public String toGson(){
        Gson gson = new Gson();

        return gson.toJson(this, BoDPlayer.class);
    }
}
