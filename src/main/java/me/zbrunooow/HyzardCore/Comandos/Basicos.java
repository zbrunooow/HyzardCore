package me.zbrunooow.HyzardCore.Comandos;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Basicos implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;
        if(cmd.getName().equalsIgnoreCase("craft")) {
            p.openWorkbench(p.getLocation(), true);
        }


        return false;
    }
}
