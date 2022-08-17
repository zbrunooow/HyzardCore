package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.Home;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HomeDeletar {

    public HomeDeletar(Core core) {
        HyzardCommand command = new HyzardCommand(core, "delhome", "delete suas homes!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
                if(!(sender instanceof Player)) return false;
                Player p = (Player) sender;

                if(!p.hasPermission("hyzardcore.delhome") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length > 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length == 0) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                Home home = Manager.get().getJogador(p).getHome(args[0]);
                if(home == null) {
                    p.sendMessage(command.getMensagens().getMsg("Home_Inexistente").replace("{home}", args[0]));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return false;
                }

                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                p.sendMessage(command.getMensagens().getMsg("Home_Deletada").replace("{home}", home.getNome()));
                Manager.get().removeHome(p, home);

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/delhome [home])");
            config.set("Home_Inexistente", "&cA home &4{home} &cnão existe.");
            config.set("Home_Deletada", "&aVocê deletou a home &2{home} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
