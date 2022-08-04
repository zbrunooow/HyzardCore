package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Locations;
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
                if(Locations.get().getSpawnVip() != null) {
                    if (!Locations.get().getSpawnVip().getChunk().isLoaded()) {
                        Locations.get().getSpawnVip().getChunk().load();
                    }
                    p.teleport(Locations.get().getSpawnVip());
                    p.sendMessage("§aVocê foi teleportado para o Spawn (VIP).");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    if(Locations.get().getSpawnNormal() != null) {
                        if (!Locations.get().getSpawnNormal().getChunk().isLoaded()) {
                            Locations.get().getSpawnNormal().getChunk().load();
                        }
                        p.teleport(Locations.get().getSpawnNormal());
                        p.sendMessage("§aVocê foi teleportado para o Spawn.");
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } else {
                        p.sendMessage("§cO spawn não foi setado.");
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                    }
                }
            } else {
                if(Locations.get().getSpawnNormal() != null) {
                    if (!Locations.get().getSpawnNormal().getChunk().isLoaded()) {
                        Locations.get().getSpawnNormal().getChunk().load();
                    }
                    p.teleport(Locations.get().getSpawnNormal());
                    p.sendMessage("§aVocê foi teleportado para o Spawn.");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    p.sendMessage("§cO spawn não foi setado.");
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                }
            }
        }

        return false;
    }
}
