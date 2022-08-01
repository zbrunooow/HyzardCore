package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hat implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("hat")) {
            if (p.hasPermission("hyzardcore.hat") || p.hasPermission("hyzardcore.*")) {
                if(p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(p.getItemInHand());
                    p.setItemInHand(null);
                    p.sendMessage("§aVocê equipou um novo chapéu!");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    p.sendMessage("§cVocê já tem um chapéu equipado!");
                }
            }else{
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }
}
