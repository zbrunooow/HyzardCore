package me.zbrunooow.hyzardessentials.Comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.EnderChestAPI;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class Echest {

    public Echest(Core core) {
        HyzardCommand command = new HyzardCommand(core, "echest", "abra seu enderchest de qualquer lugar", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.echest") || p.hasPermission("hyzardcore.*")) {
                    if(args.length >= 2) {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                        return false;
                    }
                    if(args.length == 1) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if(p2 != null) {
                            if(p2 != p) {
                                if (p.hasMetadata("echest")) {
                                    p.removeMetadata("echest", Core.getInstance());
                                }
                                EnderChestAPI ec = new EnderChestAPI(Core.getInstance(), p2);
                                ec.setupInventory();

                                p.openInventory((Inventory) p2.getMetadata("enderchest").get(0).value());
                            } else {
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Voce_Mesmo")));
                            }
                        } else {
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                        }
                    } else {
                        EnderChestAPI ec = new EnderChestAPI(Core.getInstance(), p);
                        ec.setupInventory();

                        p.openInventory((Inventory) p.getMetadata("enderchest").get(0).value());

                        p.playSound(p.getLocation(), Sound.CHEST_OPEN, 1, 10);
                    }
                } else {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, Mensagens.get().getSemPerm()));
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/echest [player])");
            config.set("Voce_Mesmo", "&cPara abrir seu enderchest, basta utilizar /echest!");
            config.set("Jogador_Offline", "&cJogador offline!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }



}
