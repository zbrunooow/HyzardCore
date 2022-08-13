package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.objetos.Cooldown;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Kit {

    public Kit(Core core) {
        HyzardCommand command = new HyzardCommand(core, "kit", "pegue um kit", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(args.length == 1) {
                    if(Manager.get().getKit(args[0]) == null) {
                        p.sendMessage(command.getMensagens().getMsg("Kit_Inexistente").replace("{kit}", args[0]));
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                        return false;
                    }
                    me.zbrunooow.hyzardessentials.objetos.Kit kit = Manager.get().getKit(args[0]);

                    Cooldown cooldown = new Cooldown(args[0], kit.getCooldown(), p);
                    if(cooldown.hasCooldown()) {
                        cooldown.getCooldownKit();
                        return false;
                    }

                    if(!kit.getPerm().equalsIgnoreCase("")) {
                        if(!p.hasPermission(kit.getPerm()) && !p.hasPermission("hyzardcore.*")) {
                            p.sendMessage(command.getMensagens().getMsg("Sem_Perm_Kit").replace("{kit}", args[0] + " " + kit.getPerm()));
                            p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                            return false;
                        }
                    }

                    if(!Manager.get().getKit(args[0]).getContents(p)) {
                        p.sendMessage(command.getMensagens().getMsg("Sem_Espaco_Kit").replace("{kit}", args[0]));
                        p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                        return false;
                    }

                    cooldown.createCooldown();
                    p.sendMessage(command.getMensagens().getMsg("Pegou_Kit").replace("{kit}", kit.getNome()));
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                    return true;
                }

                String kits = new String();
                for(me.zbrunooow.hyzardessentials.objetos.Kit kit : Manager.get().getKits()) {
                    Cooldown cooldown = new Cooldown(kit.getNome().toLowerCase(), kit.getCooldown(), p);
                    if(kit.getPerm().equalsIgnoreCase("")) {
                        if (kits.length() == 0) {
                            if(cooldown.hasCooldown()) {
                                kits = ChatColor.STRIKETHROUGH + kit.getNome() + ChatColor.WHITE;
                            } else {
                                kits = kit.getNome();
                            }
                        } else {
                            if(cooldown.hasCooldown()) {
                                kits = kits + ", " + ChatColor.STRIKETHROUGH + kit.getNome() + ChatColor.WHITE;
                            } else {
                                kits = kits + ", " + kit.getNome();
                            }
                        }
                    } else {
                        if (p.hasPermission(kit.getPerm())) {
                            if (kits.length() == 0) {
                                if(cooldown.hasCooldown()) {
                                    kits = ChatColor.STRIKETHROUGH + kit.getNome() + ChatColor.WHITE;
                                } else {
                                    kits = kit.getNome();
                                }
                            } else {
                                if(cooldown.hasCooldown()) {
                                    kits = kits + ", " + ChatColor.STRIKETHROUGH + kit.getNome() + ChatColor.WHITE;
                                } else {
                                    kits = kits + ", " + kit.getNome();
                                }
                            }
                        }
                    }
                }
                if(kits.length() == 0) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Kits").replace("{kits}", "&fNenhum kit setado.")));
                } else {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Kits").replace("{kits}", kits)));
                }
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 8);

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Kit_Inexistente", "&cO kit &4{kit} &cnão existe.");
            config.set("Sem_Perm_Kit", "&cVocê não tem permissão para pegar o kit &4{kit}.");
            config.set("Sem_Espaco_Kit", "&cVocê não tem espaço no inventário para pegar o kit &4{kit}.");
            config.set("Pegou_Kit", "&aVocê pegou o kit &2{kit} &acom sucesso!");
            config.set("Kits", "&aKits: &f{kits}&a");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
