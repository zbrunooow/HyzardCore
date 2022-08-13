package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Item;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Potion {

    public Potion(Core core){
        HyzardCommand command = new HyzardCommand(core, "potion", "crie poções maneiras", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.potion") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return  false;
                }

                if(args.length != 3) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                String[] tipos = args[1].split(",");

                if(tipos.length == 0) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(!API.get().isInt(args[2])) {
                    p.sendMessage(command.getMensagens().getMsg("Quantidade_Invalida"));
                    return false;
                }

                String typepotion;
                int potionid;
                int tempo;
                int potionamplifier;

                Item item = new Item(Material.POTION, 8268);

                for (String en : tipos) {
                    if (en.contains(":")) {
                        String[] potiontype = en.split(":");
                        if(potiontype.length <= 2) {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                            return false;
                        }

                        if(API.get().isInt(potiontype[1])) {
                            potionamplifier = Integer.parseInt(potiontype[1]);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                            return false;
                        }

                        if(API.get().isInt(potiontype[2])) {
                            tempo = Integer.parseInt(potiontype[2]);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                            return false;
                        }

                        if(potionamplifier < 1) {
                            potionamplifier = 1;
                        }

                        if(tempo < 1) {
                            tempo = 1;
                        }

                        if (API.get().isInt(potiontype[0])) {
                            potionid = Integer.parseInt(potiontype[0]);
                            if(API.get().isPotionType(potionid)) {
                                item.addPotionEffect(potionid, potionamplifier, tempo);
                                item.setAmount(Integer.parseInt(args[2]));
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Pocao_Inexistente").replace("{pocao}", String.valueOf(potionid)));
                                return false;
                            }
                        } else {
                            typepotion = potiontype[0];
                            if(API.get().isPotionType(typepotion.toUpperCase())) {
                                item.addPotionEffect(typepotion.toUpperCase(), potionamplifier, tempo);
                                item.setAmount(Integer.parseInt(args[2]));
                            } else {
                                p.sendMessage(command.getMensagens().getMsg("Pocao_Inexistente").replace("{pocao}", typepotion.toUpperCase()));
                                return false;
                            }
                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                        return false;
                    }
                }

                p.getInventory().addItem(item.build().clone());
                p.sendMessage(command.getMensagens().getMsg("Sucesso"));

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/potion criar [id:amplifier:tempo] [quantia])!");
            config.set("Quantidade_Invalida", "&cQuantidade inválida!");
            config.set("Pocao_Inexistente", "&cO tipo &4{pocao} &cde poção não existe!");

            config.set("Sucesso", "&aVocê criou uma poção!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
