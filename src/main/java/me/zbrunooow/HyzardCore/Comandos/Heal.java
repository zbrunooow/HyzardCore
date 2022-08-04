package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Heal implements CommandExecutor {

    public Heal(Core core) {
        core.getCommand("heal").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equals("heal")) {
            if (p.hasPermission("hyzardcore.heal") || p.hasPermission("hyzardcore.*")) {
                if(args.length == 0) {
                    p.setHealth(20);
                    p.setFoodLevel(20);
                    p.sendMessage("§aVocê se healou com sucesso.");
                } else if (args.length == 1){
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 != null) {
                        p2.setHealth(20);
                        p2.setFoodLevel(20);
                        p.sendMessage("§aVocê healou §2" + p2.getName() + "§a!");
                    } else {
                        p.sendMessage("§cJogador offline!");
                    }
                } else {
                    p.sendMessage("§cUse (/heal [player])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }
}
