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

public class EchestListener implements Listener {

    @EventHandler
    public void aoClicarEchest(InventoryClickEvent e) {
        Inventory inv = e.getInventory();
        if(inv.getType() == InventoryType.PLAYER) {
            Player p = (Player) e.getWhoClicked();
            if(p.hasMetadata("echest")) {
                if(!p.hasPermission("hyzardcore.echest.outros") || !p.hasPermission("hyzardcore.*")) {
                    e.setCancelled(true);
                    e.setResult(Event.Result.DENY);
                }
            }
        }

    }

    @EventHandler
    public void quitEchest(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for(Player all : Bukkit.getOnlinePlayers()){
            if (all.hasMetadata("echest")){
                String nome = (String) all.getMetadata("echest").get(0).value();
                if (nome.equals(p.getName())) {
                    all.closeInventory();
                    all.removeMetadata("echest", Core.getInstance());
                }
            }
        }
    }

    @EventHandler
    public void aoFecharEchest(InventoryCloseEvent e) {
        Inventory i = e.getInventory();
        if (i.getType() == InventoryType.PLAYER) {
            Player p = (Player) e.getPlayer();
            if(p.hasMetadata("echest")){
                p.removeMetadata("echest", Core.getInstance());
            }
        }
    }

}