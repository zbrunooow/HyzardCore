package me.zbrunooow.hyzardessentials.listeners;

import com.sun.corba.se.impl.encoding.BufferManagerReadGrow;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.concurrent.TimeUnit;

public class PlaytimeListener implements Listener {

    @EventHandler
    public void aoEntrar(PlayerJoinEvent e) {
        new Jogador(e.getPlayer());
    }

    @EventHandler
    public void aoQuitar(PlayerQuitEvent e) {
        Jogador jogador = Manager.get().getJogador(e.getPlayer());
        jogador.setTempoTotal((int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-jogador.getTime()) + jogador.getTempoTotal()));
        jogador.save();
        Manager.get().removeJogador(Manager.get().getJogador(e.getPlayer()));
    }

}
