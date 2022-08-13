package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.MsgCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Alerta {

    public Alerta(Core core) {
        HyzardCommand command = new HyzardCommand(core, "alerta", "", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                MsgCommand msg = command.getMensagens();

                if(cmd.getName().equalsIgnoreCase("alerta")) {
                    if (p.hasPermission("hyzardcore.alerta") || p.hasPermission("hyzardcore.*")) {
                        if(args.length >= 1){
                            StringBuilder title = new StringBuilder();
                            StringBuilder subtitle = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                if(args[i].equalsIgnoreCase("{nl}")) {
                                    i++;
                                    for (int j = i; j < args.length; j++) {
                                        subtitle.append(args[j]).append(" ");
                                    }
                                    break;
                                } else {
                                    title.append(args[i]).append(" ");
                                }
                            }
                            String argumentos = title.toString().trim();
                            String argumentos2 = subtitle.toString().trim();
                            for(Player e : Bukkit.getOnlinePlayers()) {
                                e.sendTitle(argumentos.replace('&', '§'), argumentos2.replace('&', '§'));
                                API.get().sendActionBarMessage(e, command.getMensagens().getMsg("Emitido_ActionBar").replace("{player}", p.getName()));
                            }
                            p.sendMessage(command.getMensagens().getMsg("Alerta_Enviado"));
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        }
                    } else {
                        p.sendMessage(Mensagens.get().getSemPerm());
                    }
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(()-> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/alerta [Title] {nl} [SubTitle])");
            config.set("Alerta_Enviado", "&aAlerta enviado!");
            config.set("Emitido_ActionBar", "&7{player} &eemitiu um alerta!");
            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
