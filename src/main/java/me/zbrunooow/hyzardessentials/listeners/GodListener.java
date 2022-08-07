package me.zbrunooow.hyzardessentials.listeners;

import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class GodListener implements Listener {

    @EventHandler
    public void aoTomarDano(EntityDamageEvent e) {
        if(e.getEntity().getType().equals(EntityType.PLAYER)) {
            Player p = (Player) e.getEntity();
            if(p.hasMetadata("god")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void aoQuitar(PlayerQuitEvent e) {
        if(e.getPlayer().hasMetadata("god")) {
            e.getPlayer().removeMetadata("god", Core.getInstance());
        }
    }

}
