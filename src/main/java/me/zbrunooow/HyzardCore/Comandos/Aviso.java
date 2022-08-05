package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import me.zbrunooow.HyzardCore.Utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;

public class Aviso {

    public Aviso(Core core) {
        HyzardCommand command = new HyzardCommand(core, "aviso", "use para mandar um aviso no chat", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {

            @Override
            public boolean onCommand(CommandSender p, Command cmd, String label, String[] args) {
                if(cmd.getName().equalsIgnoreCase("aviso")) {
                    if (p.hasPermission("hyzardcore.aviso") || p.hasPermission("hyzardcore.*")) {
                        if(args.length >= 1){
                            StringBuilder aviso = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                aviso.append(args[i]).append(" ");
                            }
                            String argumentos = aviso.toString().trim();
                            API.get().broadcastMessage(command.getMensagens().getMsg("Prefix") + argumentos.replace('&', '§'));
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

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Prefix", "&c[AVISO]&7: ");
            config.set("Como_Usar", "&cUse (/aviso [aviso])");
            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
