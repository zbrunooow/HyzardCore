package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Enchant {

    public Enchant(Core core) {
        HyzardCommand command = new HyzardCommand(core, "enchant", "encante seus itens", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;

                if(!s.hasPermission("hyzardcore.enchant") && !s.hasPermission("hyzardcore.*")) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length != 1) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                String[] enchant = args[0].split(",");

                String typeenchant = null;
                String multiplier;
                int enchantid = 0;
                int enchantmultiplier = 1;

                Player p = (Player) s;
                ItemStack item = p.getItemInHand();

                if(item.getType() == Material.AIR) {
                    s.sendMessage(command.getMensagens().getMsg("Item_Inexistente"));
                    return false;
                }

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
                                    item.addUnsafeEnchantment(Enchantment.getById(enchantid), enchantmultiplier);
                                } else {
                                    s.sendMessage(command.getMensagens().getMsg("Encantamento_Inexistente").replace("{enchant}", String.valueOf(enchantid)));
                                    return false;
                                }
                            } else {
                                typeenchant = enchanttype[0];
                                if(API.get().isEnchant(typeenchant.toUpperCase())) {
                                    item.addUnsafeEnchantment(Enchantment.getByName(typeenchant.toUpperCase()), enchantmultiplier);
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

                if(typeenchant != null) {
                    s.sendMessage(command.getMensagens().getMsg("Encantamento_Aplicado").replace("{enchant}", Arrays.toString(enchant).toUpperCase()));
                } else {
                    s.sendMessage(command.getMensagens().getMsg("Encantamento_Aplicado").replace("{enchant}", Arrays.toString(enchant)));
                }

                return false;

            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/enchant {enchant:multiplier})!");
            config.set("Encantamento_Inexistente", "&cO encantamento &4{enchant} &cnão existe!");
            config.set("Item_Inexistente", "&cVocê não pode encantar o ar!");

            config.set("Encantamento_Aplicado", "&aVocê aplicou o encantamento &2{enchant} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
