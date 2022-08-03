package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Utils.API;
import me.zbrunooow.HyzardCore.Utils.LocsFile;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Spawn implements CommandExecutor {

    public Spawn(Core core) {
        core.getCommand("spawn").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("spawn")) {
            if(p.hasPermission("hyzardcore.spawnvip")) {
                try {
                    Location spawnvip = API.get().unserialize(LocsFile.get().getLocs().getString("Spawn.VIP"));
                    p.teleport(spawnvip);
                    p.sendMessage("§aVocê foi teleportado para o Spawn (VIP).");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } catch (Exception ignored) {
                    try {
                        Location spawn = API.get().unserialize(LocsFile.get().getLocs().getString("Spawn.Normal"));
                        p.teleport(spawn);
                        p.sendMessage("§aVocê foi teleportado para o Spawn.");
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } catch (Exception ignored2) {
                        p.sendMessage("§cO spawn não foi setado.");
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                    }
                }
            } else {
                try {
                    Location spawn = API.get().unserialize(LocsFile.get().getLocs().getString("Spawn.Normal"));
                    p.teleport(spawn);
                    p.sendMessage("§aVocê foi teleportado para o Spawn.");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } catch (Exception ignored) {
                    p.sendMessage("§cO spawn não foi setado.");
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                }
            }
        }

        return false;
    }
}
