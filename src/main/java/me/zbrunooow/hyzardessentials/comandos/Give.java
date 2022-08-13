package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Give {

    public Give(Core core) {
        HyzardCommand command = new HyzardCommand(core, "give", "dar itens para um player", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {

                int itemid;
                int itemdata;
                int itemquantidade = 1;
                Item item = null;
                Material material;

                if (!s.hasPermission("hyzardcore.give") && !s.hasPermission("hyzardcore.*")) {
                    s.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if (args.length < 3) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                String[] id = args[1].split(":");

                if (id.length >= 1) {
                    if (id.length == 2) {
                        if (API.get().isInt(id[1])) {
                            itemdata = Integer.parseInt(id[1]);
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                            return false;
                        }
                        if (API.get().isInt(id[0])) {
                            itemid = Integer.parseInt(id[0]);
                            item = new Item(itemid, itemdata);
                        } else {
                            if (API.get().isMaterial(id[0])) {
                                Material mat = Material.valueOf(id[0].toUpperCase());
                                item = new Item(mat, itemdata);
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                return false;
                            }
                        }
                    } else if (id.length == 1) {
                        if (API.get().isInt(id[0])) {
                            itemid = Integer.parseInt(id[0]);
                            item = new Item(itemid, 0);
                        } else {
                            if (API.get().isMaterial(id[0])) {
                                material = Material.valueOf(id[0].toUpperCase());
                                item = new Item(material, 0);
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                return false;
                            }
                        }
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }

                    if (API.get().isInt(args[2])) {
                        itemquantidade = Integer.parseInt(args[2]);
                        item.setAmount(itemquantidade);
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Quantidade_Invalida"));
                        return false;
                    }
                }

                if (args.length == 4) {
                    String[] enchant = args[3].split(",");

                    String typeenchant;
                    String multiplier;
                    int enchantid;
                    int enchantmultiplier = 1;

                    if(enchant.length >= 1) {
                        for (String en : enchant) {
                            if (en.contains(":")) {
                                String[] enchanttype = en.split(":");
                                if(enchanttype.length <= 1) {
                                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                                    return false;
                                }

                                multiplier = enchanttype[1];
                                if(API.get().isInt(multiplier)) {
                                    enchantmultiplier = Integer.parseInt(multiplier);
                                } else {
                                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                                }
                                if(enchantmultiplier < 1) {
                                    enchantmultiplier = 0;
                                }

                                if (API.get().isInt(enchanttype[0])) {
                                    enchantid = Integer.parseInt(enchanttype[0]);
                                    if(API.get().isEnchant(enchantid)) {
                                        item.addEnchantment(enchantid, enchantmultiplier);
                                    } else {
                                        s.sendMessage(command.getMensagens().getMsg("Encantamento_Inexistente").replace("{enchant}", String.valueOf(enchantid)));
                                        return false;
                                    }
                                } else {
                                    typeenchant = enchanttype[0];
                                    if(API.get().isEnchant(typeenchant.toUpperCase())) {
                                        item.addEnchantment(typeenchant.toUpperCase(), enchantmultiplier);
                                    } else {
                                        s.sendMessage(command.getMensagens().getMsg("Encantamento_Inexistente").replace("{enchant}", typeenchant.toUpperCase()));
                                        return false;
                                    }
                                }
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                                return false;
                            }
                        }
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        return false;
                    }
                }

                if(args[0].equalsIgnoreCase("*") || args[0].equalsIgnoreCase("@a")) {
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        if (all.getInventory().firstEmpty() != -1) {
                            if (itemquantidade <= 64) {
                                if (item == null || item.getItem().getType().equals(Material.AIR)) {
                                    s.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                    return false;
                                }
                                all.getInventory().addItem(item.build());
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Limite_Excedido"));
                            }
                        }
                    }
                    s.sendMessage(command.getMensagens().getMsg("Givado_All").replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size())).replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                    return true;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 != null) {
                    if(p2.getInventory().firstEmpty() != -1) {
                        if(itemquantidade <= 64) {
                            if (item == null || item.getItem().getType().equals(Material.AIR)) {
                                p2.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                                return false;
                            }
                            p2.getInventory().addItem(item.build());
                            if(p2 == s) {
                                s.sendMessage(command.getMensagens().getMsg("Givado").replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                            } else {
                                s.sendMessage(command.getMensagens().getMsg("Givado_Outro").replace("{player}", p2.getName()).replace("{quantidade}", String.valueOf(itemquantidade)).replace("{item}", String.valueOf(item.getItem().getType())));
                            }
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Limite_Excedido"));
                        }
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Sem_Espaco_Outro").replace("{player}", p2.getName()));
                    }
                } else {
                    s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/give [player] [item:data] [quantidade] {enchant:multiplier})!");
            config.set("Jogador_Offline", "&cJogador offline!");
            config.set("Sem_Espaco", "&cVocê não tem espaço no inventário para receber esse item!");
            config.set("Sem_Espaco_Outro", "&4{player} &cnão tem espaço no inventário para receber esse item!");
            config.set("Item_Inexistente", "&cEsse item não existe!");
            config.set("Encantamento_Inexistente", "&cO encantamento &4{enchant} &cnão existe!");
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