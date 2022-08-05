package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Info {

    public Info(Core core){
        HyzardCommand command = new HyzardCommand(core, "info", "informações do servidor", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if(args.length == 0) {
                    for(String e : command.getConfig().getStringList("Mensagens.Info")) {
                        p.sendMessage(e.replace('&', '§'));
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                    }
                } else {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            List<String> info = new ArrayList<>();
            info.add("&6Site: &elink.com");
            info.add("&6Discord: &edc.com ");

            config.set("Info", info);

            config.set("Como_Usar", "&cUse (/info)!");
            config.set("Voce_Mesmo", "&cPara abrir seu enderchest, basta utilizar /echest!");
            config.set("Jogador_Offline", "&cJogador offline!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
