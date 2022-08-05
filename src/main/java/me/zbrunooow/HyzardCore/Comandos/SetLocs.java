package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Locations;
import me.zbrunooow.HyzardCore.Mensagens;
import me.zbrunooow.HyzardCore.Utils.API;
import me.zbrunooow.HyzardCore.Utils.LocsFile;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SetLocs {

    public SetLocs(Core core) {
        HyzardCommand command = new HyzardCommand(core, "setspawn", "setar o spawn do servidor", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.setspawn") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("normal")) {
                            LocsFile.get().getLocs().set("Spawn.Normal", API.get().serialize(p.getLocation()));
                            LocsFile.get().saveLocs();
                            p.sendMessage(command.getMensagens().getMsg("Setou_Spawn").replace("{tipo}", "NORMAL"));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);

                        } else if (args[0].equalsIgnoreCase("vip")) {
                            LocsFile.get().getLocs().set("Spawn.VIP", API.get().serialize(p.getLocation()));
                            LocsFile.get().saveLocs();
                            p.sendMessage(command.getMensagens().getMsg("Setou_Spawn").replace("{tipo}", "VIP"));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);

                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/renomear [nome])");
            config.set("Setou_Spawn", "§aVocê setou o spawn {tipo}!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
