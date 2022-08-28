package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Ticket;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class AjudaVer {

    public AjudaVer(Core core) {
        HyzardCommand command = new HyzardCommand(core, "verticket", "ver um ticket", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.vertickets") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(!API.get().isInt(args[0])) {
                    p.sendMessage(command.getMensagens().getMsg("ID_Invalido").replace("{id}", args[0]));
                    return false;
                }

                Ticket ticket = Manager.get().getTicket(Integer.parseInt(args[0]));
                if(ticket != null) {
                    p.sendMessage(command.getMensagens().getMsg("Ticket_Ver").replace("{ticket}", ticket.getDuvida()).replace("{id}", String.valueOf(ticket.getId())));
                    return false;
                }

                p.sendMessage("!");

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/verticket [id])");
            config.set("ID_Invalido", "&cO ticket de id &4{id} &cnão existe!");
            config.set("Ticket_Ver", "&aTicket &2{id}&a: &7{ticket}!");
            config.set("Ticket_Inexistente", "&cO ticket &4{od} &cnão existe!");
            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
