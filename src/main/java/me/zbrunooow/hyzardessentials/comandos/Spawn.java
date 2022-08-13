package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Locations;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
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

                if(args.length == 0) {
                    if (p.hasPermission("hyzardcore.spawn.vip") || p.hasPermission("hyzardcore.*")) {
                        if (Locations.get().getSpawn("VIP") != null) {
                            if (!Locations.get().getSpawn("VIP").getChunk().isLoaded()) {
                                Locations.get().getSpawn("VIP").getChunk().load();
                            }
                            p.teleport(Locations.get().getSpawn("VIP"));
                            p.sendMessage(command.getMensagens().getMsg("Teleportado_Vip"));
                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar_Vip")));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            if (Locations.get().getSpawn("NORMAL") != null) {
                                if (!Locations.get().getSpawn("NORMAL").getChunk().isLoaded()) {
                                    Locations.get().getSpawn("NORMAL").getChunk().load();
                                }
                                p.teleport(Locations.get().getSpawn("NORMAL"));
                                p.sendMessage(command.getMensagens().getMsg("Teleportado"));
                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar")));
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Sem_Spawn"));
                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                            }
                        }
                    } else {
                        if (Locations.get().getSpawn("NORMAL") != null) {
                            if (!Locations.get().getSpawn("NORMAL").getChunk().isLoaded()) {
                                Locations.get().getSpawn("NORMAL").getChunk().load();
                            }
                            p.teleport(Locations.get().getSpawn("NORMAL"));
                            p.sendMessage(command.getMensagens().getMsg("Teleportado"));
                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar")));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Sem_Spawn"));
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                        }
                    }
                } else if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("vip")) {
                        if (p.hasPermission("hyzardcore.spawn.vip") || p.hasPermission("hyzardcore.*")) {
                            if (Locations.get().getSpawn("VIP") != null) {
                                if (!Locations.get().getSpawn("VIP").getChunk().isLoaded()) {
                                    Locations.get().getSpawn("VIP").getChunk().load();
                                }
                                p.teleport(Locations.get().getSpawn("VIP"));
                                p.sendMessage(command.getMensagens().getMsg("Teleportado_Vip"));
                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar_Vip")));
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Sem_Spawn_Vip"));
                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                            }
                        } else {
                            p.sendMessage(Mensagens.get().getSemPerm());
                        }
                    } else if (args[0].equalsIgnoreCase("normal")) {
                        if (Locations.get().getSpawn("NORMAL") != null) {
                            if (!Locations.get().getSpawn("NORMAL").getChunk().isLoaded()) {
                                Locations.get().getSpawn("NORMAL").getChunk().load();
                            }
                            p.teleport(Locations.get().getSpawn("NORMAL"));
                            p.sendMessage(command.getMensagens().getMsg("Teleportado"));
                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar")));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Sem_Spawn"));
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 10);
                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Sem_Spawn_Errado").replace("{tipo}", args[0]));
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                    }
                }
                return false;

            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/spawn)");
            config.set("Sem_Spawn", "&cO Spawn não foi setado.");
            config.set("Sem_Spawn_Vip", "&cO Spawn VIP não foi setado.");
            config.set("Sem_Spawn_Errado", "&cO tipo de Spawn &4{tipo} &cnão existe.");
            config.set("Teleportado", "&aVocê foi teleportado para o Spawn!");
            config.set("Teleportado_Vip", "&aVocê foi teleportado para o Spawn (VIP)!");

            config.set("Teleportado_ActionBar", "&aVocê foi teleportado para o Spawn!");
            config.set("Teleportado_ActionBar_Vip", "&aVocê foi teleportado para o Spawn (VIP)!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
