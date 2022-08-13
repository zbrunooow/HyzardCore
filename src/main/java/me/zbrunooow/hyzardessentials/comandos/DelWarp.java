package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Warp;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class DelWarp {

    public DelWarp(Core core){
        HyzardCommand command = new HyzardCommand(core, "deletarwarp", "", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.delwarp") && !p.hasPermission("hyzardcore.*")) {
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

                Warp warp = Manager.get().getWarp(args[0]);
                if(warp == null) {
                    p.sendMessage(command.getMensagens().getMsg("Warp_Inexistente").replace("{warp}", args[0]));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return false;
                }

                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                p.sendMessage(command.getMensagens().getMsg("Warp_Deletada").replace("{warp}", warp.getNome()));
                Manager.get().getWarps().remove(warp);
                warp.delete();

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/delwarp [warp])");
            config.set("Warp_Inexistente", "&cA warp &4{warp} &cnão existe.");
            config.set("Warp_Deletada", "&aVocê deletou a warp &2{warp} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });


    }
}
