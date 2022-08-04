package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Renomear implements CommandExecutor {

    public Renomear(Core core) {
        core.getCommand("renomear").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equals("renomear")) {
            if (p.hasPermission("hyzardcore.renomear") || p.hasPermission("hyzardcore.*")) {
                if(args.length >= 1) {
                    StringBuilder renomeando = new StringBuilder();
                    for (int i = 0; i < args.length; i++) {
                        renomeando.append(args[i]).append(" ");
                    }
                    String argumentos = renomeando.toString().trim();
                    ItemStack item = p.getItemInHand();
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(argumentos.replace('&', '§'));
                    item.setItemMeta(meta);
                    p.sendMessage("§aVocê renomeou seu item para §f" + argumentos.replace('&', '§') + "§a!");
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                    p.updateInventory();
                } else {
                    p.sendMessage("§cUse (/renomear [nome])");
                }
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }



        return false;
    }
}
