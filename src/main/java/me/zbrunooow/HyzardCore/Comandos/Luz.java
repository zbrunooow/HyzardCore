package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
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
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 99999999, 1));
                            p.sendMessage(command.getMensagens().getMsg("Ativou"));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                            p.sendMessage(command.getMensagens().getMsg("Desativou"));
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
            config.set("Como_Usar", "&cUse (/luz)!");
            config.set("Ativou", "&aVocê ativou a luz!");
            config.set("Desativou", "&cVocê desativou a luz!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
