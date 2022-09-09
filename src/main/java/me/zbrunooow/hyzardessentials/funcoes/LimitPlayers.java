package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class LimitPlayers implements Listener {

    @EventHandler
    public void aoEntrar(PlayerJoinEvent e) {
        if(!e.getPlayer().hasPermission("hyzardcore.vip") && !e.getPlayer().hasPermission("hyzardcore.*")) {
            if(Bukkit.getOnlinePlayers().size() > Bukkit.getMaxPlayers() - Config.get().getSlotsReservados()) {
                e.getPlayer().kickPlayer(Config.get().getKickMessage().replace("{nl}", "\n"));
            }
        }
    }

}
