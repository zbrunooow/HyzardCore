package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class Clear {

    public Clear(Core core) {
        HyzardCommand command = new HyzardCommand(core, "clear", "limpe o seu inventário ou o de players tercerizados", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.clear") || p.hasPermission("hyzardcore.*")) {
                    if(args.length >= 2) {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        return false;
                    }
                    if (args.length == 1) {
                        if(args[0].equalsIgnoreCase("*")) {
                            for (Player all : Bukkit.getOnlinePlayers()) {
                                all.getInventory().setArmorContents(new ItemStack[4]);
                                all.getInventory().setContents(new ItemStack[36]);
                                all.updateInventory();
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Player"))) {
                                    all.sendMessage(command.getMensagens().getMsg("Avisar_Player").replace("{playerlimpou}", p.getName()));
                                }
                            }
                            p.sendMessage(command.getMensagens().getMsg("Inventario_All"));
                            return true;
                        }
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if(p2 != null) {
                            if(p2 != p) {
                                p2.getInventory().setArmorContents(new ItemStack[4]);
                                p2.getInventory().setContents(new ItemStack[36]);
                                p2.updateInventory();
                                p.sendMessage(command.getMensagens().getMsg("Inventario_Player").replace("{player}", p2.getName()));
                                if (Boolean.valueOf(command.getFromConfig("Avisar_Player"))) {
                                    p2.sendMessage(command.getMensagens().getMsg("Avisar_Player").replace("{playerlimpou}", p.getName()));
                                }
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
                            }
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        }
                    } else {
                        p.getInventory().setArmorContents(new ItemStack[4]);
                        p.getInventory().setContents(new ItemStack[36]);
                        p.updateInventory();
                        p.sendMessage(command.getMensagens().getMsg("Inventario_Limpo"));
                    }
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }

                return false;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            config.set("Avisar_Player", true);
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/clear [player])");
            config.set("Avisar_Player", "&aSeu inventário foi limpo por &2{playerlimpou}&a!");
            config.set("Inventario_Limpo", "&aVocê limpou seu inventário com sucesso!");
            config.set("Inventario_Player", "&aVocê limpou o inventário de &2{player} &acom sucesso.");
            config.set("Inventario_All", "&aVocê limpou o inventário de todos os jogadores.");
            config.set("Jogador_Offline", "&cJogador offline!");
            config.set("Voce_Mesmo", "&cPara limpar seu inventário, basta utilizar /clear!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });


    }

}
