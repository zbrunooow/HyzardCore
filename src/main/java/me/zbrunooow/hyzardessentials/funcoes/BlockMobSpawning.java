package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.entity.Animals;
import org.bukkit.entity.Monster;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class BlockMobSpawning implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void aoSpawnarMob(CreatureSpawnEvent e) {
        if(Config.get().getBloquearmonstros()) {
            if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
                if(e.getEntity() instanceof Monster) {
                    e.setCancelled(true);
                }
            }
        }
        if(Config.get().getBloquearanimais()) {
            if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
                if(e.getEntity() instanceof Animals) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
