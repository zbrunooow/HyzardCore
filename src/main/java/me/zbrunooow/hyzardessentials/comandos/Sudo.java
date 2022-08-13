package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Sudo {

    public Sudo(Core core) {

        HyzardCommand command = new HyzardCommand(core, "sudo", "fazer um player executar um comando", "", new ArrayList<>());

        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!s.hasPermission("hyzardcore.sudo") && !s.hasPermission("hyzardcore.*")) {
                    s.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length < 2) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                String executar = "";
                for(int i = 1; i < args.length; i++) {
                    executar += args[i] + " ";
                }

                if(args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("@a")) {
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        p.chat(executar);
                    }
                    s.sendMessage(command.getMensagens().getMsg("Sucesso"));
                    return true;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                    return false;
                }

                p2.chat(executar);
                s.sendMessage(command.getMensagens().getMsg("Sucesso"));
                return true;

            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/sudo [player|*] [comando]!");
            config.set("Jogador_Offline", "&cJogador offline!");

            config.set("Sucesso", "&aSudo enviado com sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
