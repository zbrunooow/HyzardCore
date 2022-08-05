package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
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
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.heal") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 0) {
                        if(p.getHealth() < 20) {
                            p.setHealth(20);
                            p.setFoodLevel(20);
                            p.sendMessage(command.getMensagens().getMsg("Curou"));
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Vida_Cheia"));
                        }
                    } else if (args.length == 1){
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if(p2 != null) {
                            if(p2 != p) {
                                if (p2.getHealth() < 20) {
                                    p2.setHealth(20);
                                    p2.setFoodLevel(20);
                                    p.sendMessage(command.getMensagens().getMsg("Como_Usar").replace("{player}", p2.getName()));
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Vida_Cheia_Outro"));
                                }
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                            }
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }


                return false;
            }
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
            config.set("Curou_Outro", "&aVocê curou a vida de &2{player}&a!!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
