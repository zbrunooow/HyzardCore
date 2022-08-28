package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.AnvilAPI;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Basicos {

    public Basicos(Core core) {
        HyzardCommand command = new HyzardCommand(core, "craft", "abrir uma crafting table virtual", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.craft") || p.hasPermission("hyzardcore.*")) {
                    if(args.length != 0) {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        return false;
                    }
                        p.openWorkbench(null, true);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }
                return false;
            }
        });

        HyzardCommand command2 = new HyzardCommand(core, "bigorna", "abrir uma bigorna virtual", "", new ArrayList<>());
        command2.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if(args.length != 0) {
                    p.sendMessage(command2.getMensagens().getMsg("Como_Usar"));
                    return false;
                }
                if (p.hasPermission("hyzardcore.bigorna") || p.hasPermission("hyzardcore.*")) {
                    AnvilAPI.openAnvilInventory(p);
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/craft)");
        });

        command2.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command2.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command2.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/bigorna)");
        });

    }
}
