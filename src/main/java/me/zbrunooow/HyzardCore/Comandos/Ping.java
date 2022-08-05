package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Ping {

    public Ping(Core core) {
        HyzardCommand command = new HyzardCommand(core, "ping", "veja seu ping!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if(args.length == 0) {
                    int ping = ((CraftPlayer)p).getHandle().ping;
                    p.sendMessage(command.getMensagens().getMsg("Ping").replace("{ping}", String.valueOf(ping)));
                } else if (args.length == 1) {
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 != null) {
                        if(p2 != p) {
                            int ping = ((CraftPlayer) p2).getHandle().ping;
                            p.sendMessage(command.getMensagens().getMsg("Ping_Player").replace("{player}", p2.getName()).replace("{ping}", String.valueOf(ping)));
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                    }
                } else {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/ping [player])");
            config.set("Voce_Mesmo", "&cPara ver seu ping, basta utilizar /ping.");
            config.set("Jogador_Offline", "&cJogador offline!");

            config.set("Ping", "&aSeu ping é de &2{ping}ms&a.");
            config.set("Ping_Player", "&aO ping de &2{player} &aé de &2{ping}ms&a.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
