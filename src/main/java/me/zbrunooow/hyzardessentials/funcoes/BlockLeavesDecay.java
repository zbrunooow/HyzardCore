package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.LeavesDecayEvent;

public class BlockLeavesDecay implements Listener {

    @EventHandler
    public void aoCairFolha(LeavesDecayEvent e) {
        if(Config.get().getBloquearquedafolhas()) {
            e.setCancelled(true);
        }
    }

}
