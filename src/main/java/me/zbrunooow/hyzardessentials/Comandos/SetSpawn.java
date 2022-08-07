package me.zbrunooow.hyzardessentials.Comandos;

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

public class SetSpawn {

    public SetSpawn(Core core) {
        HyzardCommand command = new HyzardCommand(core, "setspawn", "setar o spawn do servidor", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.setspawn") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("normal")) {
                            Core.getInstance().getLocs().getConfig().set("Spawn.NORMAL", API.get().serialize(p.getLocation()));
                            Core.getInstance().getLocs().saveConfig();
                            Core.getInstance().getLocs().reloadConfig();
                            p.sendMessage(command.getMensagens().getMsg("Setou_Spawn").replace("{tipo}", "NORMAL"));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else if (args[0].equalsIgnoreCase("vip")) {
                            Core.getInstance().getLocs().getConfig().set("Spawn.VIP", API.get().serialize(p.getLocation()));
                            Core.getInstance().getLocs().saveConfig();
                            Core.getInstance().getLocs().reloadConfig();
                            p.sendMessage(command.getMensagens().getMsg("Setou_Spawn").replace("{tipo}", "VIP"));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Tipo_Inexistente").replace("{tipo}", args[0].toUpperCase()));
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
            config.set("Como_Usar", "&cUse (/setspawn [normal/vip])");
            config.set("Tipo_Inexistente", "&cO tipo {tipo} de Spawn não existe.");
            config.set("Setou_Spawn", "&aVocê setou o Spawn {tipo}!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
