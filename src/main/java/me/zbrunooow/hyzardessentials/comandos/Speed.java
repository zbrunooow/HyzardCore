package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Speed {

    public Speed(Core core){
        HyzardCommand command = new HyzardCommand(core, "speed", "", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.speed") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(!API.get().isInt(args[0])) {
                    p.sendMessage(command.getMensagens().getMsg("Numero_Invalido"));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return false;
                }

                int vel = Integer.parseInt(args[0]);
                if(vel < 1 || vel > 5) {
                    p.sendMessage(command.getMensagens().getMsg("Numero_Invalido"));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return false;
                }

                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                p.setWalkSpeed((float) vel/5);
                p.sendMessage(command.getMensagens().getMsg("Velocidade_Alterada").replace("{velocidade}", String.valueOf(vel)));
                API.get().sendActionBarMessage(p, command.getMensagens().getMsg("Velocidade_Alterada_ActionBar").replace("{velocidade}", String.valueOf(vel)));

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/speed [speed])");
            config.set("Numero_Invalido", "&cInsira um n??mero v??lido (0-5)");

            config.set("Velocidade_Alterada", "&aVoc?? alterou sua velocidade de caminhar para &2{velocidade}!");
            config.set("Velocidade_Alterada_ActionBar", "&aVoc?? alterou sua velocidade para &2{velocidade}!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
