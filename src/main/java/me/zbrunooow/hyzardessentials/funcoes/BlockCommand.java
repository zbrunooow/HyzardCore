package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/*
public class BlockCommand implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void AoExecutarComandoBloqueado(PlayerCommandPreprocessEvent e) {
        String cmd = e.getMessage().toLowerCase().split(" ")[0];
        for (String blockedCmd : Config.get().getBlockedcommands()) {
            Bukkit.broadcastMessage(blockedCmd + " / " + cmd);
            if (blockedCmd.equals(cmd) || (cmd.split(":").length > 1 && blockedCmd.equals(cmd.split(":")[1])) || cmd.split(":")[0].contains("*") && cmd.split(":")[1].equalsIgnoreCase(blockedCmd.split(":")[1])) {
                Bukkit.broadcastMessage("A");
    //            if (!e.getPlayer().hasPermission("hyzardcore.bypass.blockedcmds") && !e.getPlayer().hasPermission("hyzardcore.admin")) {
                    e.getPlayer().sendMessage(Config.get().getBlockedcommandmsg().replace("{comando}", e.getMessage()));
                    e.setCancelled(true);
                    return;
                }
      //      }
        }
    }

}
*/