package me.zbrunooow.hyzardessentials.comandos;

import jdk.nashorn.internal.runtime.ListAdapter;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.Home;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class HomeSet {

    public HomeSet(Core core) {
        HyzardCommand command = new HyzardCommand(core, "sethome", "set suas homes", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.sethome") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(!command.getFromConfig("Mundos_Liberados").contains(p.getWorld().getName())) {
                    p.sendMessage(command.getMensagens().getMsg("Mundo_Bloqueado"));
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                Jogador j = Manager.get().getJogador(p);
                Home home = j.getHome(args[0]);
                if(home != null) {
                    p.sendMessage(command.getMensagens().getMsg("Ja_Existe").replace("{home}", home.getNome()));
                    return false;
                }

                p.sendMessage(command.getMensagens().getMsg("Home_Criada").replace("{home}", args[0]));
                Manager.get().addHome(p, args[0].toLowerCase());

                return false;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            List<String> lista = new ArrayList<>();
            lista.add("world");
            lista.add("mundo2");
            lista.add("mundo3");

            config.set("Mundos_Liberados", lista);

            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/sethome [home])");
            config.set("Mundo_Bloqueado", "&cVocê não pode criar uma home nesse mundo!");
            config.set("Ja_Existe", "&cA home &4{home} &cjá existe!");
            config.set("Home_Criada", "&aVocê criou a home &2{home} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
