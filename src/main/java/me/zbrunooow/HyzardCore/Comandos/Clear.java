package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Clear implements CommandExecutor {

    private final String semperm = Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§');

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("clear")) {
            if (p.hasPermission("hyzardcore.clear") || p.hasPermission("hyzardcore.*")) {
                if (args.length == 1) {
                    try {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        p2.getInventory().clear();
                        p2.sendMessage("§aSeu inventário foi limpo por §2" + p.getName() + "§a!");
                        p.sendMessage("§aVocê limpou o inventário de §2" + p2.getName() + " §acom sucesso.");
                    } catch (Exception ignored) {
                        p.sendMessage("§cJogador offline!");
                    }
                } else {
                    p.getInventory().clear();
                    p.sendMessage("§aVocê limpou seu inventário com sucesso!");
                }
            } else {
                p.sendMessage(semperm);
            }
        }

        return false;
    }

}
