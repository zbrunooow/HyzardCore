package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import me.zbrunooow.HyzardCore.Utils.API;
import me.zbrunooow.HyzardCore.Utils.LocsFile;
import org.bukkit.Sound;
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
                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("normal")) {
                        LocsFile.get().getLocs().set("Spawn.Normal", API.get().serialize(p.getLocation()));
                        LocsFile.get().saveLocs();
                        p.sendMessage("§aVocê setou o spawn NORMAL!");
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } else if (args[0].equalsIgnoreCase("vip")) {
                        LocsFile.get().getLocs().set("Spawn.VIP", API.get().serialize(p.getLocation()));
                        LocsFile.get().saveLocs();
                        p.sendMessage("§aVocê setou o spawn VIP!");
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    }
                } else {
                    p.sendMessage("§cUse (/setspawn [normal/vip])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }


        return false;
    }
}
