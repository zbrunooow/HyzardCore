package me.zbrunooow.hyzardessentials.Comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class HyzardEssentials {

    private HyzardCommand command;

    public HyzardEssentials(Core core) {
        command = new HyzardCommand(Core.getInstance(), "hyzardessentials", "comando principal", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (!p.hasPermission("hyzardcore.reload") || !p.hasPermission("hyzardcore.*")) {
                        p.sendMessage(Mensagens.get().getSemPerm());
                        return false;
                    }
                }

                if(args.length == 1) {
                    if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
                        Core.getInstance().reloadPlugin();
                        s.sendMessage(command.getMensagens().getMsg("reload"));
                    }
                } else {
                    s.sendMessage("§cUse (/hyzardessentials reload)");
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
