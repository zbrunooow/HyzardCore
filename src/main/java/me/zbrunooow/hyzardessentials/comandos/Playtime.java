package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Playtime {

    public Playtime(Core core) {
        HyzardCommand command = new HyzardCommand(core, "playtime", "veja seu tempo in-game", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(args.length > 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("top")) {
                        int i = 0;
                        for (String str : command.getMensagens().getLista("Playtime_Top")) {
                            List<String> lista = new ArrayList<>(Manager.get().getTopOffline());
                            if (str.contains("{Formatacao_Top}")) {
                                while(i < Manager.get().getPlayerstop()) {
                                    p.sendMessage(str.replace("{Formatacao_Top}", lista.get(i)));
                                    i++;
                                }
                                continue;
                            }
                            p.sendMessage(str);
                        }
                        return true;
                    }

                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == null) {
                        p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        return false;
                    }

                    if(p2 == p) {
                        p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                        return false;
                    }

                    for (String str : command.getMensagens().getLista("Playtime_Outro")) {
                        p.sendMessage(str.replace("{player}", p2.getName()).replace("{tempo}", Manager.get().getJogador(p2).getTempoOnlineFormatado()));
                    }
                    return true;
                }

                for (String str : command.getMensagens().getLista("Playtime")) {
                    p.sendMessage(str.replace("{player}", p.getName()).replace("{tempo}", Manager.get().getJogador(p).getTempoOnlineFormatado()));
                }

                return true;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            config.set("Players_Top", 5);
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/playtime [player])");
            config.set("Jogador_Offline", "&cJogador offline.");
            config.set("Voce_Mesmo", "&cPara ver seu playtime, basta utilizar (/playtime).");

            List<String> playtime = new ArrayList<>();

            playtime.add("");
            playtime.add("&eSeu tempo jogado:");
            playtime.add("&7 - &6{tempo}");
            playtime.add("");

            List<String> playtimeoutro = new ArrayList<>();
            playtimeoutro.add("");
            playtimeoutro.add("&eTempo jogado de &6{player}&e:");
            playtimeoutro.add("&7 - &6{tempo}");
            playtimeoutro.add("");

            List<String> playtimetop = new ArrayList<>();
            playtimetop.add("");
            playtimetop.add("&eRanking de jogadores online:");
            playtimetop.add("{Formatacao_Top}");
            playtimetop.add("");

            config.set("Playtime", playtime);
            config.set("Playtime_Outro", playtimeoutro);
            config.set("Playtime_Top", playtimetop);
            config.set("Formatacao_Top", "&e{pos}º &7- &6{player}: &7- {tempo}");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
