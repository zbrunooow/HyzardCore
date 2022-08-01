package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Hat implements CommandExecutor {

    private final String semperm = Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§');

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("hat")) {
            if (p.hasPermission("hyzardcore.hat") || p.hasPermission("hyzardcore.*")) {
                if(p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(p.getItemInHand());
                    p.setItemInHand(null);
                    p.sendMessage("§aVocê equipou um novo chapéu!");
                } else {
                    p.sendMessage("§cVocê já tem um chapéu equipado!");
                }
            }else{
                p.sendMessage(semperm);
            }
        }

        return false;
    }
}
