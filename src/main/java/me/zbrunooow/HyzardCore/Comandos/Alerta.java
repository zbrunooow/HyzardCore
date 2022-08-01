package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Alerta implements CommandExecutor {

    public Alerta(Core core) {
        core.getCommand("alerta").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true; // §
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("alerta")) {
            if (p.hasPermission("hyzardcore.alerta") || p.hasPermission("hyzardcore.*")) {
                if(args.length >= 1){
                    StringBuilder title = new StringBuilder();
                    StringBuilder subtitle = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        if(args[i].equalsIgnoreCase("{nl}")) {
                            i++;
                            for (int j = i; j < args.length; j++) {
                                subtitle.append(args[j]).append(" ");
                            }
                            break;
                        } else {
                            title.append(args[i]).append(" ");

                        }
                    }
                    String argumentos = title.toString().trim();
                    String argumentos2 = subtitle.toString().trim();
                    for(Player e : Bukkit.getOnlinePlayers()) {
                        e.sendTitle(argumentos.replace('&', '§'), argumentos2.replace('&', '§'));
                    }
                    p.sendMessage("§aVocê enviou um alerta para todos.");
                } else {
                    p.sendMessage("§cUse (/alerta [Title] {nl} [SubTitle])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }

}
