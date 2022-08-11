package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.hooks.VaultHook;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Fly {

    public Fly(Core core) {
        HyzardCommand command = new HyzardCommand(core, "fly", "voa passarinho voa", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (p.hasPermission("hyzardcore.fly") || p.hasPermission("hyzardcore.*")) {
                        if (args.length == 0) {
                            if (!p.getAllowFlight()) {
                                if (command.getConfig().getStringList("Config.Mundos_Permitidos").contains(p.getWorld().getName())) {
                                    p.setAllowFlight(true);
                                    p.sendMessage(command.getMensagens().getMsg("Ativado"));
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Mundo_Nao_Encontrado"));
                                }
                            } else {
                                p.setAllowFlight(false);
                                p.sendMessage(command.getMensagens().getMsg("Desativado"));
                            }
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else if (args.length == 1) {
                            if (p.hasPermission("hyzardcore.fly.outros") || p.hasPermission("hyzardcore.*")) {
                                Player p2 = Bukkit.getPlayerExact(args[0]);
                                if (p2 != null) {
                                    if (p2 != p) {
                                        if (!p2.getAllowFlight()) {
                                            if (command.getConfig().getStringList("Config.Mundos_Permitidos").contains(p2.getWorld().getName())) {
                                                p.sendMessage(command.getMensagens().getMsg("Ativado_Outro").replace("{player}", p2.getName()));
                                                p2.setAllowFlight(true);
                                                if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                                    p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Ativou").replace("{player}", p.getName()));
                                                }
                                            } else {
                                                p.sendMessage(command.getMensagens().getMsg("Mundo_Nao_Encontrado_Outro").replace("{player}", p2.getName()));
                                            }
                                        } else {
                                            p2.setAllowFlight(false);
                                            p.sendMessage(command.getMensagens().getMsg("Desativado_Outro").replace("{player}", p2.getName()));
                                            if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                                p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Desativou").replace("{player}", p.getName()));
                                            }
                                        }
                                    } else {
                                        p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                                    }
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                                }
                            } else {
                                p.sendMessage(Mensagens.get().getSemPerm());
                            }
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        }
                    }else {
                        p.sendMessage(Mensagens.get().getSemPerm());
                    }
                } else {
                    if (args.length == 1) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if (p2 != null) {
                            if (!p2.getAllowFlight()) {
                                if (command.getConfig().getStringList("Config.Mundos_Permitidos").contains(p2.getWorld().getName())) {
                                    s.sendMessage(command.getMensagens().getMsg("Ativado_Outro").replace("{player}", p2.getName()));
                                    p2.setAllowFlight(true);
                                    if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                        p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Ativou").replace("{player}", "CONSOLE"));
                                    }
                                } else {
                                    s.sendMessage(command.getMensagens().getMsg("Mundo_Nao_Encontrado_Outro").replace("{player}", p2.getName()));
                                }
                            } else {
                                p2.setAllowFlight(false);
                                s.sendMessage(command.getMensagens().getMsg("Desativado_Outro").replace("{player}", p2.getName()));
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Outro"))) {
                                    p2.sendMessage(command.getMensagens().getMsg("Player_Avisado_Desativou").replace("{player}", "CONSOLE"));
                                }
                            }
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        }
                    } else {
                        s.sendMessage("§cUse /fly (player)");
                    }
                }
                return false;
            }

        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            String[] mundos = {"world", "mundo2", "mundo3", ""};

            config.set("Mundos_Permitidos",mundos);
            config.set("Avisar_Outro", true);

        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Mundo_Nao_Encontrado", "&cVocê não pode ativar o fly neste mundo!");
            config.set("Mundo_Nao_Encontrado_Outro", "&cNão foi possível ativar o fly de &2{player}&a, ele está num mundo não liberado!");

            config.set("Como_Usar", "&cUse (/fly [player])!");
            config.set("Voce_Mesmo", "&cPara ativar o seu modo fly, basta utilizar (/fly)!");
            config.set("Jogador_Offline", "&cJogador offline!");

            config.set("Ativado", "&aVocê ativou o fly!");
            config.set("Ativado_Outro", "&aVocê ativou o fly de &2{player}&a!");
            config.set("Desativado", "&aVocê desativou o fly!");
            config.set("Desativado_Outro", "&aVocê desativou o fly de &2{player}&a!");

            config.set("Player_Avisado_Ativou", "&aSeu fly foi ativado por &2{player}&a!");
            config.set("Player_Avisado_Desativou", "&aSeu fly foi desativado por &2{player}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
