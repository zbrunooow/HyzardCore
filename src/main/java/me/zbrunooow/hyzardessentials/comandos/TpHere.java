package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class TpHere {

    public TpHere(Core core) {
        HyzardCommand command = new HyzardCommand(core, "tphere", "teleporte um player para você", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tphere") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 1) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                    return false;
                }

                p2.teleport(p);
                p.sendMessage(command.getMensagens().getMsg("Teleportado").replace("{player}", p2.getName()));
                p2.sendMessage(command.getMensagens().getMsg("Teleportado_Outro").replace("{player}", p.getName()));

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tphere [player])!");
            config.set("Jogador_Offline", "&cJogador offline.");

            config.set("Teleportado", "&aO player &2{player} &afoi teleportado até você!");
            config.set("Teleportado_Outro", "&aVocê foi teleportado para &2{player}!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
