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

public class TpCancel {

    public TpCancel(Core core) {
        HyzardCommand command = new HyzardCommand(core, "tpcancel", "solicite um teleport para um player", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tpcancel") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 0) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    return false;
                }

                if(!p.hasMetadata("tpa")) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Nenhum_Teleporte")));
                    return false;
                }
                Player p2 = Bukkit.getPlayerExact(String.valueOf(p.getMetadata("tpa").get(0).value()));

                p.removeMetadata("tpa", core);
                p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Cancelado").replace("{player}", p2.getName())));

                p2.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Cancelado_Outro").replace("{player}", p.getName())));
                API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Cancelado_ActionBar_Outro").replace("{player}", p.getName())));

                return true;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tpcancel)!");
            config.set("Nenhum_Teleporte", "&cVocê não tem nenhum pedido de teleporte ativo!");
            config.set("Cancelado", "&aVocê cancelou seu pedido de teleporte para &2{player} &acom sucesso!");
            config.set("Cancelado_Outro", "&4{player} &ccancelou sua solicitação de teleporte.");

            config.set("Cancelado_ActionBar_Outro", "&4{player} &ccancelou sua solicitação de teleporte.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
