package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;

public class Invsee {

    public Invsee(Core core) {
        HyzardCommand command = new HyzardCommand(core, "invsee", "veja o inventario dos amigos!", "",  new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;


                if (p.hasPermission("hyzardcore.invsee") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 1) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if(p2 != null) {
                            if(p2 != p) {
                                if (p.hasMetadata("invsee")) {
                                    p.removeMetadata("invsee", Core.getInstance());
                                }

                                p.setMetadata("invsee", new FixedMetadataValue(Core.getInstance(), p2.getName()));
                                p.openInventory(p2.getInventory());
                                p.sendMessage(command.getMensagens().getMsg("Abriu_Inventario").replace("{player}", p2.getName()));
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);

                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                            }
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
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
            config.set("Como_Usar", "&cUse (/invsee [player])");
            config.set("Voce_Mesmo", "&cVocê não pode ver seu próprio inventário.");
            config.set("Jogador_Offline", "&cJogador offline!");
            config.set("Abriu_Inventario", "&aVocê abriu o inventário de &2{player}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
