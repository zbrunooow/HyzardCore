package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;

public class Luz {

    public Luz(Core core) {
        HyzardCommand command = new HyzardCommand(core, "luz", "ative a luz!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.luz") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 0) {
                        if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 1, false, false));
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Ativou")));
                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Ativou_ActionBar")));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Desativou")));
                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Desativou_ActionBar")));
                        }
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    }
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/luz)!");
            config.set("Ativou", "&aVocê ativou a luz!");
            config.set("Desativou", "&cVocê desativou a luz!");

            config.set("Ativou_ActionBar", "&aVocê ativou a luz!");
            config.set("Desativou_ActionBar", "&cVocê desativou a luz!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
