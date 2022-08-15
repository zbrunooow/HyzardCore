package me.zbrunooow.hyzardessentials.listeners;

import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class PerfilListener implements Listener {

    @EventHandler
    public void aoClicarPerfil(InventoryClickEvent e) {
        if(e.getInventory().getName().contains("Perfil")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
        }
    }

}
