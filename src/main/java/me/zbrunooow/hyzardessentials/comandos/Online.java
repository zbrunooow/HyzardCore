package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;

public class Online {

    public Online(Core core) {
        HyzardCommand command = new HyzardCommand(core, "online", "Ver a quantidade de players online", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender p, Command cmd, String lb, String[] args) {
                if(args.length == 0) {
                    for(String s : command.getMensagens().getLista("Online")) {
                        p.sendMessage(s.replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{onlinemax}", String.valueOf(Bukkit.getMaxPlayers())));
                    }
                } else {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            List<String> online = new ArrayList<>();
            online.add(" ");
            online.add("&aJogadores online no &2FullPvP&7: &f{online}/{onlinemax}");
            online.add(" ");
            config.set("Online", online);
            config.set("Como_Usar", "&cUse (/online)!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
