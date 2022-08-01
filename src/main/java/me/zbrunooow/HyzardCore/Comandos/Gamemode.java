package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode implements CommandExecutor {

    public Gamemode(Core core) {
        core.getCommand("gamemode").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;
        if(cmd.getName().equalsIgnoreCase("gamemode")) {
            if (p.hasPermission("hyzardcore.gamemode") || p.hasPermission("hyzardcore.*")) {
                if (args.length == 1) {
                    if(args[0].equalsIgnoreCase("0")) {
                        p.setGameMode(GameMode.SURVIVAL);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        p.sendMessage("§aVocê foi para o gamemode SOBREVIVÊNCIA!");
                    } else if (args[0].equalsIgnoreCase("1")) {
                        p.setGameMode(GameMode.CREATIVE);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        p.sendMessage("§aVocê foi para o gamemode CRIATIVO!");
                    } else if (args[0].equalsIgnoreCase("2")) {
                        p.setGameMode(GameMode.ADVENTURE);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        p.sendMessage("§aVocê foi para o gamemode AVENTURA!");
                    } else if (args[0].equalsIgnoreCase("3")) {
                        p.setGameMode(GameMode.SPECTATOR);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        p.sendMessage("§aVocê foi para o gamemode ESPECTADOR!");
                    } else {
                        p.sendMessage("§cUse (/gm [0/1/2/3])");
                    }
                } else {
                    p.sendMessage("§cUse (/gm [0/1/2/3])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }
}
