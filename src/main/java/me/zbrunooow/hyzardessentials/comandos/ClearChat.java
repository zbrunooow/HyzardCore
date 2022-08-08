package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ClearChat {

    public ClearChat(Core core) {
        HyzardCommand command = new HyzardCommand(core, "clearchat", "Limpe o chat mágicamente!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (p.hasPermission("hyzardcore.clearchat") || p.hasPermission("hyzardcore.*")) {
                        if(args.length == 0) {
                            int i = 100;
                            while(i > 0){
                                i--;
                                API.get().broadcastMessage(" ");
                            }
                        } else if(args.length == 1){
                            if(args[0].equalsIgnoreCase("-s")){
                                int i = 100;
                                while(i > 0){
                                    i--;
                                    API.get().broadcastMessage(" ");
                                }
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                            }
                            return false;
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                            return false;
                        }
                        API.get().broadcastMessage(command.getMensagens().getMsg("Chat_Limpo").replace("{player}", p.getName()));
                    }
                } else {
                    if(args.length == 0) {
                        int i = 100;
                        while(i > 0){
                            i--;
                            API.get().broadcastMessage(" ");
                        }
                    } else if(args.length == 1){
                        if(args[0].equalsIgnoreCase("-s")){
                            int i = 100;
                            while(i > 0){
                                i--;
                                API.get().broadcastMessage(" ");
                            }
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        }
                        return false;
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        return false;
                    }
                    API.get().broadcastMessage(command.getMensagens().getMsg("Chat_Limpo").replace("{player}", "CONSOLE"));
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/clearchat [-s])");
            config.set("Chat_Limpo", "&6{player} &elimpou o chat.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
