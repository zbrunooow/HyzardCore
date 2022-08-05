package me.zbrunooow.HyzardCore.Listeners;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;

public class BackListener implements Listener {

    @EventHandler
    public void backTeleportListener(PlayerTeleportEvent e) {
        if(e.getPlayer().hasPermission("hyzardcore.back") || (e.getPlayer().hasPermission("hyzardcore.*"))) {
            e.getPlayer().setMetadata("back", new FixedMetadataValue(Core.getInstance(), e.getFrom()));
        }
    }

}
