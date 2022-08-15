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

public class TpDeny {

    public TpDeny(Core core) {
        HyzardCommand command = new HyzardCommand(core, "tpdeny", "negar uma solicitação de teleporte", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tpdeny") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    return false;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                    return false;
                }

                if(p2 == p) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                    return false;
                }

                if(!p2.hasMetadata("tpa") || p2.getMetadata("tpa").get(0).value() != p.getName()) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Nao_Recebeu_Tpa").replace("{player}", p2.getName())));
                    return false;
                }

                p2.removeMetadata("tpa", core);
                p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Negou").replace("{player}", p2.getName())));
                p2.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Negou_Outro").replace("{player}", p.getName())));
                API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Negou_ActionBar_Outro").replace("{player}", p.getName())));

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tpdeny [player])");
            config.set("Nao_Recebeu_Tpa", "&cVocê não recebeu um pedido de teleporte de &4{player}&c!");
            config.set("Jogador_Offline", "&cJogador offline.");

            config.set("Negou", "&aVocê negou o pedido de teleporte de &2{player}&a!");
            config.set("Negou_Outro", "&cSeu pedido de teleporte para &4{player} &cfoi negado.");

            config.set("Negou_ActionBar_Outro", "&cSeu pedido de teleporte para &4{player} &cfoi negado.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
