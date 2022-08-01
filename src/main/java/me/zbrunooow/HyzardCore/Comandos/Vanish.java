package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Vanish implements CommandExecutor {

    private final String semperm = Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§');

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (p.hasPermission("hyzardcore.vanish") || p.hasPermission("hyzardcore.*")) {
                if (!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 99999999, 1));
                    p.sendMessage("§aVocê ativou o vanish!");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    p.removePotionEffect(PotionEffectType.INVISIBILITY);
                    p.sendMessage("§cVocê desativou o vanish!");
                }
            } else {
                p.sendMessage(semperm);
            }
        }

        return false;
    }
}
