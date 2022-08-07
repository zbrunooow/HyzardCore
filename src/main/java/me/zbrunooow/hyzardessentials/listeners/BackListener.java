package me.zbrunooow.hyzardessentials.listeners;

import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BackListener implements Listener {

    @EventHandler
    public void backTeleportListener(PlayerTeleportEvent e) {
        if(e.getPlayer().hasPermission("hyzardcore.back") || (e.getPlayer().hasPermission("hyzardcore.*"))) {
            e.getPlayer().setMetadata("back", new FixedMetadataValue(Core.getInstance(), e.getFrom()));
        }
    }

    @EventHandler
    public void backTeleportListener(PlayerDeathEvent e) {
        if(e.getEntity().getType().equals(EntityType.PLAYER)) {
            if(e.getEntity().hasPermission("hyzardcore.back") || (e.getEntity().hasPermission("hyzardcore.*"))) {
                e.getEntity().setMetadata("back", new FixedMetadataValue(Core.getInstance(), e.getEntity().getLocation()));
            }
        }
    }

}
