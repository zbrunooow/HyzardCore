package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Echest {

    public Echest(Core core) {
        HyzardCommand command = new HyzardCommand(core, "echest", "abra seu enderchest de qualquer lugar", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (!p.hasPermission("hyzardcore.echest") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length > 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == null) {
                        p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        return false;
                    }

                    if(p2 == p) {
                        p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                        return false;
                    }

                    p.openInventory(Manager.get().getJogador(p2).getEnderchest());
                    return true;
                }

                p.openInventory(Manager.get().getJogador(p).getEnderchest());

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/echest [player])");
            config.set("Voce_Mesmo", "&cPara abrir seu enderchest, basta utilizar /echest!");
            config.set("Jogador_Offline", "&cJogador offline!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }



}
