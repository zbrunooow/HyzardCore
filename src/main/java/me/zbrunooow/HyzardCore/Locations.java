package me.zbrunooow.HyzardCore;

import me.zbrunooow.HyzardCore.Utils.LocsFile;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

public class Locations {

    public Location spawnNormal;
    public Location spawnVip;

    public Locations() {
        try {
            spawnNormal = replace("Normal");
        } catch (Exception ignored) {}

        try {
            spawnVip = replace("VIP");
        } catch (Exception ignored) {}
    }

    private Location replace(String loc) {
        FileConfiguration locs = LocsFile.get().getLocs();
        return Core.getInstance().getApi().unserialize(locs.getString("Spawn." + loc));
    }

    public Location getSpawnNormal() {
        if(spawnNormal != null) {
            return spawnNormal;
        } else {
            return null;
        }
    }

    public Location getSpawnVip() {
        if(spawnVip != null) {
            return spawnVip;
        } else {
            return null;
        }
    }

    public static Locations get(){
        return Core.getInstance().getLocations();
    }

    public void Spawn(Location loc) {
        spawnNormal = loc;
        load();
    }

    public void save() {

    }

    public void load() {

    }

    public Location getLoc() {
        return spawnNormal;
    }

    public void setLoc(Location loc) {
        spawnNormal = loc;
        save();
    }

}
