package com.breathofdawn.breathofdawn.objects;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class BoDPlayer implements Serializable {

    private String _name;
    private boolean _mineMode, _chopMode;
    private UUID _uuid;

    public BoDPlayer(Player p){
        _name = p.getName();
        _uuid = p.getUniqueId();
        _mineMode = false;
        _chopMode = false;
    }

    public UUID getUUID() { return _uuid; }

    public String getName() { return _name; }

    public boolean getMineMode(){
        return _mineMode;
    }

    public boolean getChopMode(){
        return _chopMode;
    }

    public Player getPlayer()
    {
        return Bukkit.getPlayer(_uuid);
    }

    public void setMineMode(boolean active){
        _mineMode = active;
    }

    public void setChopMode(boolean active){
        _chopMode = active;
    }
}
