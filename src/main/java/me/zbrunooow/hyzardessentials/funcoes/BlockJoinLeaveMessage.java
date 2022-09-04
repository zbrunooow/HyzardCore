package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class BlockJoinLeaveMessage implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void aoEntrar(PlayerJoinEvent e) {
        if(Config.get().getBloquearmsgentrada()) {
            e.setJoinMessage(null);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void aoSair(PlayerQuitEvent e) {
        if(Config.get().getBloquearmsgsaida()) {
            e.setQuitMessage(null);
        }
    }

}
