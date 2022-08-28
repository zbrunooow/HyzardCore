package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.Cooldown;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Ticket;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ajuda {

    public Ajuda(Core core) {
        HyzardCommand command = new HyzardCommand(core, "ajuda", "peça ajuda", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.ticket") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length == 0) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args[0].equalsIgnoreCase("sair")) {
                    p.removeMetadata("respondendoticket", core);
                    return false;
                }

                if(args[0].equalsIgnoreCase("entrar")) {
                    p.removeMetadata("respondendoticket", core);
                    p.setMetadata("respondendoticket", new FixedMetadataValue(core, true));
                    return false;
                }

                int i = 0;
                List<Player> staffs = new ArrayList<>();
                for(Player all : Bukkit.getOnlinePlayers()) {
                    if(all.hasMetadata("respondendoticket")) i++;
                    staffs.add(all);
                }

                if(i == 0) {
                    p.sendMessage(command.getMensagens().getMsg("Nenhum_Staff_Online"));
                    return false;
                }

                if(args[0].equalsIgnoreCase("listar")) {
                    p.sendMessage(command.getMensagens().getMsg("Staffs_Online").replace("{staffs}", String.valueOf(i)));
                    return false;
                }

                Cooldown cooldown = new Cooldown("Ajuda", 5, p);
                if(cooldown.hasCooldown()) {
                    p.sendMessage("ta no cooldown, é foda :c " + cooldown.getTime());
                    return false;
                }

                if(Manager.get().getTicketsId().size() >= 20) {
                    p.sendMessage(command.getMensagens().getMsg("Muitos_Tickets"));
                    return false;
                }

                Random r = new Random();
                int id = r.nextInt(100);
                while(Manager.get().getTicketsId().contains(id)) {
                    id = r.nextInt(100);
                }

                StringBuilder sb = new StringBuilder();
                for (int o = 0; o < args.length; o++) {
                    sb.append(args[o]).append(" ");
                }
                String duvida = sb.toString().trim();
                Ticket tc = new Ticket(p, id, duvida);
                Manager.get().getTickets().add(tc);

                for(Player receber : staffs) {
                    receber.sendMessage(command.getMensagens().getMsg("Ticket_Recebido").replace("{id}", String.valueOf(id)));
                }

                p.sendMessage(command.getMensagens().getMsg("Ticket_Enviado"));

                cooldown.createCooldown();

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/ajuda [sua dúvida])");
            config.set("Muitos_Tickets", "&cJá tem muitos tickets criados, aguarde até que possa criar um novo.");
            config.set("Ticket_Enviado", "&aSeu ticket foi enviado, aguarde por uma resposta!");
            config.set("Ticket_Recebido", "&aUm novo ticket foi recebido, use &2/verticket &l{id} &apara ver, ou &2/listartickets &apara lista-los!");
            config.set("Staffs_Online", "&aNo momento, temos &2{staffs} &arespondendo a tickets.");
            config.set("Nenhum_Staff_Online", "&aNenhum staff atendendo tickets no momento!");
            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
