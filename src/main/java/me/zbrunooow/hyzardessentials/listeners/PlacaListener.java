package me.zbrunooow.hyzardessentials.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

public class PlacaListener implements Listener {

    @EventHandler
    public void criarPlaca(SignChangeEvent e) {
        if(e.getPlayer().hasPermission("hyzardcore.placas")) {
            int linha = 0;
            while(linha <= e.getLines().length) {
                e.setLine(linha, e.getLine(linha).replace("&", "§"));
                linha++;
            }
        }
    }

}
