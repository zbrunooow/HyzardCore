package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import me.zbrunooow.HyzardCore.Utils.API;
import me.zbrunooow.HyzardCore.Utils.Item;
import net.minecraft.server.v1_8_R3.MaterialLiquid;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Give {

    int itemid;
    int itemdata;
    int itemquantidade = 1;
    Item item;
    Material material;

    public Give(Core core) {
        HyzardCommand command = new HyzardCommand(core, "give", "dar itens para um player", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args){
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.give") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 3) {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        if(p2 != null) {

                            String[] splitado = args[1].split(":");

                            if(splitado.length >= 1) {
                                if(splitado.length == 2) {
                                    if(API.get().isInt(splitado[1])) {
                                        itemdata = Integer.parseInt(splitado[1]);
                                    } else {
                                        p.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                        return false;
                                    }
                                    if(API.get().isInt(splitado[0])) {
                                        itemid = Integer.parseInt(splitado[0]);
                                    } else {
                                        p.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                        return false;
                                    }
                                    item = new Item(itemid, itemdata);
                                } else if(splitado.length == 1) {
                                    if (API.get().isInt(splitado[0])) {
                                        itemid = Integer.parseInt(splitado[0]);
                                        item = new Item(itemid, 0);
                                    } else {
                                        if (API.get().isMaterial(splitado[0])) {
                                            material = Material.valueOf(splitado[0].toUpperCase());
                                            item = new Item(material, 0);
                                        } else {
                                            p.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                            return false;
                                        }
                                    }
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                                }

                                if(API.get().isInt(args[2])) {
                                    itemquantidade = Integer.parseInt(args[2]);
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Quantidade_Invalida"));
                                    return false;
                                }
                            }

                            item.setAmount(itemquantidade);

                            if(API.get().getFreeSlots(p) >= 1) {
                                p.getInventory().addItem(item.build());
                                p.sendMessage(command.getMensagens().getMsg("Givado").replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Sem_Espaco"));
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
            config.set("Como_Usar", "&cUse (/give [player] [item:id] [quantidade])!");
            config.set("Jogador_Offline", "&cJogador offline!");
            config.set("Sem_Espaco", "&cVocê não tem espaço no inventário para receber esse item!");
            config.set("Item_Inexistente", "&cEsse item não existe!");
            config.set("Quantidade_Invalida", "&cColoque uma quantidade válida!");

            config.set("Givado", "&aVocê recebeu o item &2{quantidade}x &l{item}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
