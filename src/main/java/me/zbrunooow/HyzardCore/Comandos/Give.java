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
import java.util.Locale;

public class Give {

    public Give(Core core) {
        HyzardCommand command = new HyzardCommand(core, "give", "dar itens para um player", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args){
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                int itemid;
                int itemdata;
                int itemquantidade = 1;
                Player p2 = null;
                Item item = null;
                Material material;

                if (p.hasPermission("hyzardcore.give") || p.hasPermission("hyzardcore.*")) {

                    if(args.length == 3) {

                        String[] id = args[1].split(":");

                        if(id.length >= 1) {
                            if(id.length == 2) {
                                if(API.get().isInt(id[1])) {
                                    itemdata = Integer.parseInt(id[1]);
                                } else {
                                    p.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                    return false;
                                }
                                if(API.get().isInt(id[0])) {
                                    itemid = Integer.parseInt(id[0]);
                                    item = new Item(itemid, itemdata);
                                } else {
                                    if(API.get().isMaterial(id[0])) {
                                        Material mat = Material.valueOf(id[0].toUpperCase());
                                        item = new Item(mat, itemdata);
                                    } else {
                                        p.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                        return false;
                                    }
                                }
                            } else if(id.length == 1) {
                                if (API.get().isInt(id[0])) {
                                    itemid = Integer.parseInt(id[0]);
                                    item = new Item(itemid, 0);
                                } else {
                                    if (API.get().isMaterial(id[0])) {
                                        material = Material.valueOf(id[0].toUpperCase());
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
                                item.setAmount(itemquantidade);
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Quantidade_Invalida"));
                                return false;
                            }
                        }

                        if(args[0].equalsIgnoreCase("*")) {
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                if(all.getInventory().firstEmpty() != -1) {
                                    if (itemquantidade <= 64) {
                                        if (item == null || item.getItem().getType().equals(Material.AIR)) {
                                            p.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                            return false;
                                        }
                                        all.getInventory().addItem(item.build());
                                    } else {
                                        p.sendMessage(command.getMensagens().getMsg("Limite_Excedido"));
                                    }
                                }
                            }
                            p.sendMessage(command.getMensagens().getMsg("Givado_All").replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                            return true;
                        } else {
                            p2 = Bukkit.getPlayerExact(args[0]);

                            if(p2 != null) {
                                if(p2.getInventory().firstEmpty() != -1) {
                                    if(itemquantidade <= 64) {
                                        if (item == null || item.getItem().getType().equals(Material.AIR)) {
                                            p2.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                            return false;
                                        }
                                        p2.getInventory().addItem(item.build());
                                        if(p2 == p) {
                                            p.sendMessage(command.getMensagens().getMsg("Givado").replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                                        } else {
                                            p.sendMessage(command.getMensagens().getMsg("Givado_Outro").replace("{player}", p2.getName()).replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                                        }
                                    } else {
                                        p2.sendMessage(command.getMensagens().getMsg("Limite_Excedido"));
                                    }
                                } else {
                                    p2.sendMessage(command.getMensagens().getMsg("Sem_Espaco"));
                                }
                            } else {
                                p2.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                            }
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
            config.set("Limite_Excedido", "&cVocê só pode pegar 64 itens por vez!");

            config.set("Givado", "&aVocê recebeu o item &2{quantidade}x &l{item}&a!");
            config.set("Givado_Outro", "&aVocê deu &2{quantidade}x &l{item} &apara &2{player}&a!");
            config.set("Givado_All", "&aVocê deu &2{quantidade}x &l{item} &apara &2{online} &aplayers diferentes!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
