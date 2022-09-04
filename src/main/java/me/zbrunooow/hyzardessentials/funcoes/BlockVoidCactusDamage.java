package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import me.zbrunooow.hyzardessentials.Locations;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class BlockVoidCactusDamage implements Listener {

    @EventHandler
    public void aoTomarDanoVoid(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            if(e.getCause() == EntityDamageEvent.DamageCause.VOID) {
                if(Locations.get().getSpawn("NORMAL") != null) {
                    e.getEntity().teleport(Locations.get().getSpawn("NORMAL"));
                }
                e.setCancelled(true);
            }
            if(e.getCause() == EntityDamageEvent.DamageCause.CONTACT) {
                if(Config.get().getBloqueardanocacto()) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
