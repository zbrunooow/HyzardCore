package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Locations;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Spawn {

    public Spawn(Core core) {
        HyzardCommand command = new HyzardCommand(core, "spawn", "vai para o ponto de spawn do servidor", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if(args.length != 0) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(p.hasPermission("hyzardcore.spawnvip")) {
                    if(Locations.get().getSpawnVip() != null) {
                        if (!Locations.get().getSpawnVip().getChunk().isLoaded()) {
                            Locations.get().getSpawnVip().getChunk().load();
                        }
                        p.teleport(Locations.get().getSpawnVip());
                        p.sendMessage(command.getMensagens().getMsg("Teleportado_Vip"));
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } else {
                        if(Locations.get().getSpawnNormal() != null) {
                            if (!Locations.get().getSpawnNormal().getChunk().isLoaded()) {
                                Locations.get().getSpawnNormal().getChunk().load();
                            }
                            p.teleport(Locations.get().getSpawnNormal());
                            p.sendMessage(command.getMensagens().getMsg("Teleportado"));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Sem_Spawn"));
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                        }
                    }
                } else {
                    if(Locations.get().getSpawnNormal() != null) {
                        if (!Locations.get().getSpawnNormal().getChunk().isLoaded()) {
                            Locations.get().getSpawnNormal().getChunk().load();
                        }
                        p.teleport(Locations.get().getSpawnNormal());
                        p.sendMessage(command.getMensagens().getMsg("Teleportado"));
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Sem_Spawn"));
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                    }
                }
                return false;

            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/renomear [nome])");
            config.set("Sem_Spawn", "&cO Spawn não foi setado.");
            config.set("Teleportado", "&aVocê foi teleportado para o Spawn!");
            config.set("Teleportado_Vip", "&aVocê foi teleportado para o Spawn (VIP)!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
