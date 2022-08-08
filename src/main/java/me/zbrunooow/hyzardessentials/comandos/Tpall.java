package me.zbrunooow.hyzardessentials.comandos;

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

public class Tpall {

    public Tpall(Core core) {
        HyzardCommand command = new HyzardCommand(core, "tpall", "teleporte todos para você", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tpall") || !p.hasPermission("hyzardcore.*")) {
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

                    for(Player teleportar : Bukkit.getOnlinePlayers()) {
                        teleportar.teleport(p2);
                        teleportar.sendMessage(command.getMensagens().getMsg("Teleportado").replace("{player}", p.getName()));
                    }
                    p.sendMessage(command.getMensagens().getMsg("Teleportou_Outros").replace("{player}", p2.getName()));
                    return true;
                }

                for(Player teleportar : Bukkit.getOnlinePlayers()) {
                    teleportar.teleport(p);
                    teleportar.sendMessage(command.getMensagens().getMsg("Teleportado").replace("{player}", p.getName()));
                }
                p.sendMessage(command.getMensagens().getMsg("Teleportou"));

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tpall [player])!");

            config.set("Jogador_Offline", "&cJogador offline.");

            config.set("Teleportado", "&aVocê foi teleportado para &2{player}&a!");
            config.set("Teleportou", "&aVocê teleportou todos players para sua localização!");
            config.set("Teleportou_Outros", "&aVocê teleporto todos os players para &2{player}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
