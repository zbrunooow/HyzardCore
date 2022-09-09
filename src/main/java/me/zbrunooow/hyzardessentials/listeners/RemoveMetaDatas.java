package me.zbrunooow.hyzardessentials.listeners;

import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class RemoveMetaDatas implements Listener {

    @EventHandler
    public void aoQuitar(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if(p.hasMetadata("tpa")) {
            p.removeMetadata("tpa", Core.getInstance());
        }
        if(p.hasMetadata("tpaloc")) {
            p.removeMetadata("tpaloc", Core.getInstance());
        }
        if(p.hasMetadata("warp_use")) {
            p.removeMetadata("warp_use", Core.getInstance());
        }
        if(p.hasMetadata("teleportandohome")) {
            p.removeMetadata("teleportandohome", Core.getInstance());
        }
        if(p.hasMetadata("god")) {
            p.removeMetadata("god", Core.getInstance());
        }
        if(p.hasMetadata("guardarxp")) {
            p.removeMetadata("guardarxp", Core.getInstance());
        }
    }

}
