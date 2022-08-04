package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Invsee implements CommandExecutor {

    public Invsee(Core core) {
        core.getCommand("invsee").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("invsee")) {
            if (p.hasPermission("hyzardcore.invsee") || p.hasPermission("hyzardcore.*")) {
                if(args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 != null) {
                        p.openInventory(p2.getInventory());
                        p.sendMessage("§aVocê abriu o inventário de §2" + p2.getName());
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } else {
                        p.sendMessage("§cJogador offline!");
                    }
                }
            }
        }

        return false;
    }

}
