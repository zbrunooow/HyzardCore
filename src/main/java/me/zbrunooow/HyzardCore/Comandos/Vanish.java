package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Vanish implements CommandExecutor {

    public Vanish(Core core) {
        core.getCommand("vanish").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        // ARRUMAR VANISH DEPOIS (p.hidePlayer();) (p.showPlayer();)
        if (cmd.getName().equalsIgnoreCase("vanish")) {
            if (p.hasPermission("hyzardcore.vanish") || p.hasPermission("hyzardcore.*")) {
                if (!p.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    p.hidePlayer(p);
                    p.sendMessage("§aVocê ativou o vanish!");
                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                } else {
                    p.removePotionEffect(PotionEffectType.INVISIBILITY);
                    p.sendMessage("§cVocê desativou o vanish!");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        return false;
    }
}
