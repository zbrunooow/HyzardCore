package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import me.zbrunooow.HyzardCore.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Head {

    public Head(Core core) {
        HyzardCommand command = new HyzardCommand(core, "head", "pegue a cabeça dos amigos!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.head") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 0) {
                        Item cabeca = new Item(Material.SKULL_ITEM);
                        cabeca.setOwner(p.getName());
                        cabeca.setDisplayName("§a" + p.getName());
                        cabeca.setLore("", "§7 - Informações:", "");
                        p.getInventory().addItem(new ItemStack(cabeca.build()));
                        p.updateInventory();
                        p.sendMessage(command.getMensagens().getMsg("Pegou_Head"));
                    } else if(args.length == 1){
                        String p2 = args[0];
                        if(!p2.equals(p.getName())) {
                            Item cabeca = new Item(Material.SKULL_ITEM);
                            cabeca.setOwner(p2);
                            cabeca.setDisplayName("§7Cabeça de " + p2);
                            p.getInventory().addItem(new ItemStack(cabeca.build()));
                            p.updateInventory();
                            p.sendMessage(command.getMensagens().getMsg("Pegou_Head_Outro").replace("{player}", p2));
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Voce_Mesmo"));
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
            config.set("Como_Usar", "&cUse (/head [player])!");
            config.set("Voce_Mesmo", "&cPara pegar sua cabeça, basta utilizar /head!");
            config.set("Jogador_Offline", "&cJogador offline!");
            config.set("Pegou_Head", "&aVocê pegou sua cabeça!");
            config.set("Pegou_Head_Outro", "&aVocê pegou a cabeça de {player}!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
