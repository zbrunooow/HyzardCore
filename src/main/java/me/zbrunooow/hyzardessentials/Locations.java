package me.zbrunooow.hyzardessentials;

import org.bukkit.Location;

public class Locations {

    public Location getSpawn(String spawntype) {
        if(Core.getInstance().getLocs().getConfig().get("Spawn." + spawntype.toUpperCase()) == null) {
            return null;
        }
        return Core.getInstance().getApi().unserialize(Core.getInstance().getLocs().getConfig().getString("Spawn." + spawntype.toUpperCase()));
    }

    public static Locations get(){
        return Core.getInstance().getLocations();
    }

}
