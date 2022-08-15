package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Teleport {

    public Teleport(Core core) {
        HyzardCommand command = new HyzardCommand(core, "teleport", "teleporte você para outros players ou vice-versa", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tp") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length < 1 || args.length > 4) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    return false;
                }

                if(args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if (p2 == null) {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                        return false;
                    }
                    p.teleport(p2.getLocation());
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Teleportado").replace("{player}", p2.getName())));
                    return true;
                }

                if(args.length == 2) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    Player p3 = Bukkit.getPlayerExact(args[1]);

                    if(p2 == null || p3 == null) {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                        return false;
                    }
                    p2.teleport(p3.getLocation());
                    if(p2 == p) {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado").replace("{player}", p3.getName())));
                        return true;
                    }
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_Outro").replace("{target}", p2.getName()).replace("{target2}", p3.getName())));
                    return true;
                }

                if(args.length == 3) {
                    if(!API.get().isDouble(args[0])) { p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar_Coords"))); return false;}
                    if(!API.get().isDouble(args[1])) { p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar_Coords"))); return false;}
                    if(!API.get().isDouble(args[2])) { p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar_Coords"))); return false;}

                    Location loc = new Location(p.getWorld(), Double.parseDouble(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]));
                    if(!loc.getChunk().isLoaded()) {
                        loc.getChunk().load();
                    }

                    p.teleport(loc);
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_Location").replace("{location}", "x: " + args[0] + " / y: " + args[1] + " \\ z: " + args[2])));
                    return true;
                }

                if(args.length == 4) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == null) {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                        return false;
                    }

                    if(!API.get().isDouble(args[1])) { p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar_Coords_Player"))); return false;}
                    if(!API.get().isDouble(args[2])) { p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar_Coords_Player"))); return false;}
                    if(!API.get().isDouble(args[3])) { p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar_Coords_Player"))); return false;}

                    Location loc = new Location(p.getWorld(), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]));
                    if(!loc.getChunk().isLoaded()) {
                        loc.getChunk().load();
                    }

                    p2.teleport(loc);
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Teleportado_Location_Outro").replace("{player}", p2.getName()).replace("{location}", "x: " + args[1] + " / y: " + args[2] + " \\ z: " + args[3])));
                    return true;
                }

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tp [player])!");
            config.set("Como_Usar_Coords", "&cUse (/tp [x] [y] [z])!");
            config.set("Como_Usar_Coords_Player", "&cUse (/tp [player] [x] [y] [z])!");

            config.set("Jogador_Offline", "&cJogador offline.");

            config.set("Teleportado", "&aVocê foi teleportado para &2{player}&a!");
            config.set("Teleportado_Outro", "&aVocê teleportou &2{target} &apara &2{target2}&a!");
            config.set("Teleportado_Location", "&aVocê foi teleportado para &2{location}&a!");
            config.set("Teleportado_Location_Outro", "&aVocê teleportou &2{player} &apara &2{location}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
