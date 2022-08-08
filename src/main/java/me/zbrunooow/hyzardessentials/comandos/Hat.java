package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Hat {

    public Hat(Core core) {
        HyzardCommand command = new HyzardCommand(core, "hat", "coloque um chapéu mt massa", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.hat") || p.hasPermission("hyzardcore.*")) {
                    if(args.length != 0) {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        return false;
                    }
                    if(p.getInventory().getHelmet() == null) {
                        p.getInventory().setHelmet(p.getItemInHand());
                        p.setItemInHand(null);
                        p.sendMessage(command.getMensagens().getMsg("Equipou"));
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Ja_Equipado"));
                    }
                }else{
                    p.sendMessage(Mensagens.get().getSemPerm());
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/hat)");
            config.set("Equipou", "&aVocê equipou um novo chapéu!");
            config.set("Ja_Equipado", "&cVocê já tem um chapéu equipado!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
