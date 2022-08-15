package me.zbrunooow.hyzardessentials.listeners;

import me.zbrunooow.hyzardessentials.Manager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EchestListener implements Listener {

    @EventHandler
    public void aoFecharEnderchest(InventoryCloseEvent e) {
        if(e.getInventory().getName().equalsIgnoreCase("Baú do Fim")) {
            if(e.getInventory().getHolder().equals(e.getPlayer())) {
                Manager.get().getJogador((Player) e.getPlayer()).getEnderchest().setContents(e.getInventory().getContents());
            } else {
                Manager.get().getJogador((Player) e.getInventory().getHolder()).getEnderchest().setContents(e.getInventory().getContents());
            }
        }
    }

    @EventHandler
    public void aoQuitarEnderChest(PlayerQuitEvent e) {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(p.getOpenInventory().getTopInventory().getHolder().equals(e.getPlayer())) {
                p.closeInventory();
            }
        }
    }

}