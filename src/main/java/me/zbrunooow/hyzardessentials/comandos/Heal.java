package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Heal {

    public Heal(Core core) {
        HyzardCommand command = new HyzardCommand(core, "heal", "Se cure imediatamente!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (p.hasPermission("hyzardcore.heal") || p.hasPermission("hyzardcore.*")) {
                        if(args.length == 0) {
                            if(p.getHealth() < 20) {
                                p.setHealth(20);
                                p.setFoodLevel(20);
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Curou")));
                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Curou_ActionBar")));
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                            } else {
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Vida_Cheia")));
                            }
                        } else if (args.length == 1){
                            Player p2 = Bukkit.getPlayerExact(args[0]);
                            if(p2 != null) {
                                if(p2 != p) {
                                    if (p2.getHealth() < 20) {
                                        p2.setHealth(20);
                                        p2.setFoodLevel(20);
                                        if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                            p2.sendMessage(command.getMensagens().getMsg("Curado_Outro").replace("{player}", p.getName()));
                                            API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Curado_ActionBar_Outro").replace("{player}", p.getName())));
                                        }
                                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Curou_Outro").replace("{player}", p2.getName())));
                                        API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Curou_ActionBar_Outro").replace("{player}", p2.getName())));
                                    } else {
                                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Vida_Cheia_Outro")));
                                    }
                                } else {
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Voce_Mesmo")));
                                }
                            } else {
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                            }
                        } else {
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                        }
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, Mensagens.get().getSemPerm()));
                    }
                } else {
                    if (args.length == 1) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if (p2 != null) {
                            if (p2.getHealth() < 20) {
                                p2.setHealth(20);
                                p2.setFoodLevel(20);
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                    p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Curado_Outro").replace("{player}", "CONSOLE")));
                                    API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Curado_ActionBar_Outro").replace("{player}", "CONSOLE")));
                                }
                                s.sendMessage(command.getMensagens().getMsg("Curou_Outro").replace("{player}", p2.getName()));
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Vida_Cheia_Outro"));
                            }
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        }
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }
                }

                return false;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            config.set("Avisar_Outro", true);
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Sem_Permissao_Outros", "&cVocê não curar outros jogadores!");
            config.set("Como_Usar", "&cUse (/heal [player])");
            config.set("Voce_Mesmo", "&cPara se curar, basta utilizar /heal.");
            config.set("Jogador_Offline", "&cJogador offline!");

            config.set("Vida_Cheia", "&cVocê não precisa de vida.");
            config.set("Curou", "&aVocê se curou com sucesso!");

            config.set("Vida_Cheia_Outro", "&cO jogador desejado não precisa de vida!");
            config.set("Curou_Outro", "&aVocê curou a vida de &2{player}&a!");
            config.set("Curado_Outro", "&aVocê foi curado por {player}!");

            config.set("Curou_ActionBar", "&aVocê se curou com sucesso!");
            config.set("Curou_ActionBar_Outro", "&aVocê curou a vida de &2{player}&a!");
            config.set("Curado_ActionBar_Outro", "&aVocê foi curado por {player}!");


            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
