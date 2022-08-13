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

public class Feed {

    public Feed(Core core) {
        HyzardCommand command = new HyzardCommand(core, "feed", "sacie sua fome rapidão", "", new ArrayList<>());

        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(s instanceof Player) {
                    Player p = (Player) s;
                    if (p.hasPermission("hyzardcore.feed") || p.hasPermission("hyzardcore.*")) {
                        if(args.length == 0) {
                            if(p.getFoodLevel() < 20) {
                                p.setFoodLevel(20);
                                p.sendMessage(command.getMensagens().getMsg("Saciou"));
                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Saciou_ActionBar")));
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Sem_Fome"));
                            }
                        } else if (args.length == 1) {
                            if (p.hasPermission("hyzardcore.feed.outros") || p.hasPermission("hyzardcore.*")) {
                                Player p2 = Bukkit.getPlayerExact(args[0]);
                                if (p2 != null) {
                                    if(p2 != p) {
                                        if (p2.getFoodLevel() < 20) {
                                            p2.setFoodLevel(20);
                                            p.sendMessage(command.getMensagens().getMsg("Saciou_Outro").replace("{player}", p2.getName()));
                                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Saciou_ActionBar_Outro").replace("{player}", p2.getName())));
                                            if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                                p2.sendMessage(command.getMensagens().getMsg("Saciado_Outro").replace("{player}", p.getName()));
                                                API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Saciado_ActionBar_Outro").replace("{player}", p.getName())));
                                            }
                                        } else {
                                            p.sendMessage(command.getMensagens().getMsg("Sem_Fome_Outro"));
                                        }
                                    } else {
                                        p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                                    }
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                                }
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Sem_Permissao_Outros"));
                            }
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        }
                    } else {
                        p.sendMessage(Mensagens.get().getSemPerm());
                    }
                } else {
                    if(args.length == 1) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if (p2 != null) {
                            if (p2.getFoodLevel() < 20) {
                                p2.setFoodLevel(20);
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                    p2.sendMessage(command.getMensagens().getMsg("Saciado_Outro").replace("{player}", "CONSOLE"));
                                    API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Saciado_ActionBar_Outro").replace("{player}", "CONSOLE")));
                                }
                                s.sendMessage(command.getMensagens().getMsg("Saciou_Outro").replace("{player}", p2.getName()));
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Sem_Fome_Outro"));
                            }
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        }
                    } else {
                        s.sendMessage("§cUse /feed (player)");
                    }
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Sem_Permissao_Outros", "&cVocê não pode saciar a fome de outros jogadores!");
            config.set("Como_Usar", "&cUse (/feed [player])");
            config.set("Voce_Mesmo", "&cPara saciar sua fome, basta utilizar /feed.");
            config.set("Jogador_Offline", "&cJogador offline!");

            config.set("Sem_Fome", "&cVocê não está com fome.");
            config.set("Saciou", "&aVocê saciou sua fome.");

            config.set("Sem_Fome_Outro", "&cO jogador desejado não está com fome!");
            config.set("Saciou_Outro", "&aVocê saciou a fome de &2{player}&a!");
            config.set("Saciado_Outro", "&aVocê foi saciado por {player}!");

            config.set("Saciou_ActionBar", "&aVocê saciou sua fome!");
            config.set("Saciou_ActionBar_Outro", "&aVocê saciou a fome de &2{player}&a!");
            config.set("Saciado_ActionBar_Outro", "&aVocê foi saciado por {player}!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
