package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Echest implements CommandExecutor {

    public Echest(Core core) {
        core.getCommand("echest").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("echest")) {
            if (p.hasPermission("hyzardcore.echest") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(p.getEnderChest());
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }

}
