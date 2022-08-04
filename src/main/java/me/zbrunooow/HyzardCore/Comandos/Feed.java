package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Feed implements CommandExecutor {

    public Feed(Core core) {
        core.getCommand("feed").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equals("feed")) {
            if (p.hasPermission("hyzardcore.feed") || p.hasPermission("hyzardcore.*")) {
                if(args.length == 0) {
                    p.setFoodLevel(20);
                    p.sendMessage("§aVocê se feedou com sucesso.");
                } else if (args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 != null) {
                        p2.setFoodLevel(20);
                        p.sendMessage("§aVocê feedou §2" + p2.getName() + "§a!");
                    } else {
                        p.sendMessage("§cJogador offline!");
                    }
                } else {
                    p.sendMessage("§cUse (/feed [player])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }
}
