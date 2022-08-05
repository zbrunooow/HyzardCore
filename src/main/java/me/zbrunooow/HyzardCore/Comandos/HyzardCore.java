package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HyzardCore {

    private HyzardCommand command;

    public HyzardCore(Core core) {
        command = new HyzardCommand(Core.getInstance(), "hyzardcore", "comando principal", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;


                if (p.hasPermission("hyzardcore.reload") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 1) {
                        if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                            Core.getInstance().reloadPlugin();
                            p.sendMessage(command.getMensagens().getMsg("reload"));
                        }
                    }
                }
                return false;
            }
        });
        createMsgs();

    }

    public void createMsgs(){
        command.getMensagens().createMensagens(()->{
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("reload", "&aConfiguração recarregada com sucesso.");
            command.saveConfig();
            command.getMensagens().loadMensagens();
        });
    }



}
