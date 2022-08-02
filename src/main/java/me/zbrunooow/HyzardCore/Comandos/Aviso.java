package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Aviso implements CommandExecutor {

    public Aviso(Core core) {
        core.getCommand("aviso").setExecutor(this);
    }

    public boolean onCommand(CommandSender p, Command cmd, String lb, String[] args) {
        // §
        if(cmd.getName().equalsIgnoreCase("aviso")) {
            if (p.hasPermission("hyzardcore.aviso") || p.hasPermission("hyzardcore.*")) {
                if(args.length >= 1){
                    StringBuilder aviso = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        aviso.append(args[i]).append(" ");
                    }
                    String argumentos = aviso.toString().trim();
                    Bukkit.broadcastMessage("§c[AVISO]" + "§7: " + argumentos.replace('&', '§'));

                } else {
                    p.sendMessage("§cUse (/aviso [aviso])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }
        return false;
    }
}
