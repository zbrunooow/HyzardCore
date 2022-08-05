package me.zbrunooow.HyzardCore.Listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class FlyListener implements Listener {

    @EventHandler
    public void aoTeleportar(PlayerTeleportEvent e) {
        if(e.getPlayer().getAllowFlight()) {
            if(!e.getPlayer().hasPermission("hyzardcore.teleportfly.bypass") || !e.getPlayer().hasPermission("hyzardcore.*")) {
                e.getPlayer().setAllowFlight(false);
            }
        }
    }


}
