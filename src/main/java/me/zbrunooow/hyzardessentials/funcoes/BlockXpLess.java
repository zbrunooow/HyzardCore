package me.zbrunooow.hyzardessentials.funcoes;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class BlockXpLess implements Listener {

    @EventHandler
    public void aoMorrer(PlayerDeathEvent e) {
        e.setKeepLevel(true);
    }

}
