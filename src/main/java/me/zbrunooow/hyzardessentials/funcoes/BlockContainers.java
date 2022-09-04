package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockContainers implements Listener {

    @EventHandler
    public void aoInteragir(PlayerInteractEvent e) {
        for(String blocked : Config.get().getBlockedcontainers()) {
            if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if(e.getPlayer().hasPermission("hyzardcore.bypasscontainers") || e.getPlayer().hasPermission("hyzardcore.*")) return;
                if(e.getClickedBlock().getType() == Material.valueOf(blocked)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
