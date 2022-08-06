package me.zbrunooow.HyzardCore.Listeners;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

public class InvseeListener implements Listener {

    @EventHandler
    public void aoClicarInvsee(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if(inv.getType() == InventoryType.PLAYER) {
            Player p = (Player) e.getWhoClicked();
            if(p.hasMetadata("invsee")) {
                if(!p.hasPermission("hyzardcore.invsee.outros") || !p.hasPermission("hyzardcore.*")) {
                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);
                }
            }
        }

    }

    @EventHandler
    public void quitInvsee(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for(Player all : Bukkit.getOnlinePlayers()){
            if (all.hasMetadata("invsee")){
                String nome = (String) all.getMetadata("invsee").get(0).value();
                if (nome.equals(p.getName())) {
                    all.closeInventory();
                    all.removeMetadata("invsee", Core.getInstance());
                }
            }
        }
    }

    @EventHandler
    public void aoFecharInvsee(InventoryCloseEvent e) {
        Inventory i = e.getInventory();
        if (i.getType() == InventoryType.PLAYER) {
            Player p = (Player) e.getPlayer();
            if(p.hasMetadata("invsee")){
                p.removeMetadata("invsee", Core.getInstance());
            }
        }
    }

}
