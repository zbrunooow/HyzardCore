package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLocs implements CommandExecutor {

    public SetLocs(Core core) {
        core.getCommand("setspawn").setExecutor(this);
    }


    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("setspawn")) {
            if (p.hasPermission("hyzardcore.setspawn") || p.hasPermission("hyzardcore.*")) {
                // ainda não

                p.sendMessage("Setado Spawn");


            }
        }


        return false;
    }
}
