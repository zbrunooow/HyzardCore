package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Locations;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Back {

    public Back(Core core) {
        HyzardCommand command = new HyzardCommand(core, "back", "Volte para o lugar antes do teleporte!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.back") || p.hasPermission("hyzardcore.*")) {
                    if (args.length == 0) {
                        Location back;
                        try {
                            back = (Location) p.getMetadata("back").get(0).value();
                        } catch (Exception ignored) {
                            p.sendMessage(command.getMensagens().getMsg("Sem_Teleporte"));
                            return false;
                        }

                        if (!back.getChunk().isLoaded()) {
                            back.getChunk().load();
                        }
                        p.teleport(back);
                        p.sendMessage(command.getMensagens().getMsg("Teleportado"));
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
            config.set("Como_Usar", "&cUse (/back)");
            config.set("Sem_Teleporte", "&cNão encontrei nenhum teleporte antigo.");
            config.set("Teleportado", "&aVocê foi teleportado para seu ultimo ponto!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}