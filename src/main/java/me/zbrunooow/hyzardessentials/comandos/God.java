package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

public class God {

    public God(Core core) {
        HyzardCommand command = new HyzardCommand(core, "god", "entre no modo deus", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (p.hasPermission("hyzardcore.god") || p.hasPermission("hyzardcore.*")) {
                        if (args.length == 0) {
                            if(!p.hasMetadata("god") || !(Boolean) p.getMetadata("god").get(0).value()) {
                                p.setMetadata("god", new FixedMetadataValue(Core.getInstance(), true));
                                p.sendMessage(command.getMensagens().getMsg("Ativado"));
                            } else {
                                p.removeMetadata("god", Core.getInstance());
                                p.sendMessage(command.getMensagens().getMsg("Desativado"));
                            }
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else if (args.length == 1) {
                            Player p2 = Bukkit.getPlayerExact(args[0]);
                            if(p2 != null) {
                                if(p2 != p) {
                                    if (!p2.hasMetadata("god")) {
                                        p2.setMetadata("god", new FixedMetadataValue(Core.getInstance(), true));
                                        p.sendMessage(command.getMensagens().getMsg("Ativado_Outro").replace("{player}", p2.getName()));
                                        if(Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                            p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Ativou").replace("{player}", p.getName()));
                                        }
                                    } else {
                                        p2.removeMetadata("god", Core.getInstance());
                                        p.sendMessage(command.getMensagens().getMsg("Desativado_Outro").replace("{player}", p2.getName()));
                                        if(Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                            p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Desativou").replace("{player}", p.getName()));
                                        }
                                    }
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                                }
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                            }
                        }else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        }
                    }else {
                        p.sendMessage(Mensagens.get().getSemPerm());
                    }
                } else {
                    if (args.length == 1) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if (p2 != null) {
                            if (!p2.hasMetadata("god")) {
                                p2.setMetadata("god", new FixedMetadataValue(Core.getInstance(), true));
                                s.sendMessage(command.getMensagens().getMsg("Ativado_Outro").replace("{player}", p2.getName()));
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                    p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Ativou").replace("{player}", "CONSOLE"));
                                }
                            } else {
                                p2.removeMetadata("god", Core.getInstance());
                                s.sendMessage(command.getMensagens().getMsg("Desativado_Outro").replace("{player}", p2.getName()));
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                    p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Desativou").replace("{player}", "CONSOLE"));
                                }
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
            config.set("Como_Usar", "&cUse (/god [player])!");
            config.set("Voce_Mesmo", "&cPara ativar o seu modo god, basta utilizar (/god)!");
            config.set("Jogador_Offline", "&cJogador offline!");

            config.set("Ativado", "&aVocê ativou o modo god!");
            config.set("Ativado_Outro", "&aVocê ativou o god de &2{player}&a!");
            config.set("Desativado", "&aVocê desativou o modo god!");
            config.set("Desativado_Outro", "&aVocê desativou o god de &2{player}&a!");

            config.set("Player_Avisado_Ativou", "&aSeu modo god foi ativado por &2{player}&a!");
            config.set("Player_Avisado_Desativou", "&aSeu modo god foi desativado por &2{player}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}