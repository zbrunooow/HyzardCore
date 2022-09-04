package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class BlockCommand implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void AoExecutarComandoBloqueado(PlayerCommandPreprocessEvent e) {
        for(String str : Config.get().getBlockedcommands()) {
            if(e.getMessage().startsWith(str)) {
                e.setCancelled(true);
                e.getPlayer().sendMessage(Config.get().getBlockedcommandmsg().replace("{comando}", e.getMessage()));
            }
        }
    }

}
