package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Save;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Viciado {

    public Viciado(Core core) {
        HyzardCommand command = new HyzardCommand(core, "viciado", "veja o viciadão da galera", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(args.length > 0){
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                for (String str : command.getMensagens().getLista("Viciado")) {
                    s.sendMessage(str.replace("{player}", Manager.get().getViciado()).replace("{tempo}", Manager.get().getViciadoTempo()));
                }

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/viciado)!");

            List<String> lista = new ArrayList<>();
            lista.add("");
            lista.add("&eO jogador com mais tempo online no servidor é: &7{player}");
            lista.add("&eTempo: &7{tempo}");
            lista.add("");
            config.set("Viciado", lista);

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
