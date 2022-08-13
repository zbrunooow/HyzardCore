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

public class Kill {

    public Kill(Core core) {

        HyzardCommand command = new HyzardCommand(core, "kill", "se mate ou os mate", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!s.hasPermission("hyzardcore.kill") && !s.hasPermission("hyzardcore.*")) {
                    s.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length > 1) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length == 1) {
                    if(!s.hasPermission("hyzardcore.kill.outros") || !s.hasPermission("hyzardcore.*")) { s.sendMessage(Mensagens.get().getSemPerm()); return false; }
                    Player p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == s) {
                        s.sendMessage(command.getMensagens().getMsg("Usa_Direito"));
                        return false;
                    }
                    p2.setHealth(0);
                    s.sendMessage(command.getMensagens().getMsg("Sucesso_Outro").replace("{player}", p2.getName()));
                    return true;
                }

                if(!(s instanceof Player)) return false;
                Player p = (Player) s;
                p.setHealth(0);
                p.sendMessage(command.getMensagens().getMsg("Sucesso"));
                return true;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/kill [player])!");
            config.set("Usa_Direito", "&cPara se matar, basta utilizar (/kill)!!");

            config.set("Sucesso", "&aVocê se matou!");
            config.set("Sucesso_Outro", "&aVocê matou o player &2{player} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });


    }

}
