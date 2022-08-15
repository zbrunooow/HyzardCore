package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Aviso {

    public Aviso(Core core) {
        HyzardCommand command = new HyzardCommand(core, "aviso", "use para mandar um aviso no chat", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (s.hasPermission("hyzardcore.aviso") || s.hasPermission("hyzardcore.*")) {
                    if(args.length >= 1){
                        StringBuilder aviso = new StringBuilder();
                        for (int i = 0; i < args.length; i++) {
                            aviso.append(args[i]).append(" ");
                        }
                        String argumentos = aviso.toString().trim();
                        if(s instanceof Player) {
                            Player p = (Player) s;
                            API.get().broadcastMessageDestacada(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Prefix") + argumentos.replace('&', '§')));
                            API.get().broadcastActionBarMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Emitido_ActionBar").replace("{player}", p.getName())));
                        } else {
                            API.get().broadcastMessageDestacada(command.getMensagens().getMsg("Prefix") + argumentos.replace('&', '§'));
                            API.get().broadcastActionBarMessage(command.getMensagens().getMsg("Emitido_ActionBar").replace("{player}", "CONSOLE"));
                        }
                    } else {
                        if(s instanceof Player) {
                            Player p = (Player) s;
                            s.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        }
                    }
                } else {
                    s.sendMessage(Mensagens.get().getSemPerm());
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
            config.set("Prefix", "&c[AVISO]&7: ");
            config.set("Emitido_ActionBar", "&7{player} &eemitium um aviso!");
            config.set("Como_Usar", "&cUse (/aviso [aviso])");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
