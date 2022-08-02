package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class Ping implements CommandExecutor {

    public Ping(Core core) {
        core.getCommand("ping").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;
        if(cmd.getName().equalsIgnoreCase("ping")) {
            if(args.length == 0) {
                int ping = ((CraftPlayer)p).getHandle().ping;
                p.sendMessage("§aSeu ping é de: §2" + ping + "ms§a.");
            } else if (args.length == 1) {
                try {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    int ping = ((CraftPlayer)p).getHandle().ping;
                    p.sendMessage("§aO ping de §2" + p2.getName() + "§a é de: §2" + ping + "ms§a.");
                } catch (Exception ignored) {
                    p.sendMessage("§cJogador offline!");
                }
            } else {
                p.sendMessage("§cUse (/ping [player])");
            }
        }
        return false;
    }
}
