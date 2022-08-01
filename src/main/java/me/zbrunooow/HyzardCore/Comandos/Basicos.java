package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class Basicos implements CommandExecutor {

    public Basicos(Core core) {
        core.getCommand("craft").setExecutor(this);
        core.getCommand("bigorna").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if (cmd.getName().equalsIgnoreCase("craft")) {
            if (p.hasPermission("hyzardcore.craft") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(Bukkit.createInventory(p, InventoryType.WORKBENCH));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }
        }

        if (cmd.getName().equalsIgnoreCase("bigorna"))
            if (p.hasPermission("hyzardcore.bigorna") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(Bukkit.createInventory(p, InventoryType.ANVIL));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
            } else {
                p.sendMessage(Mensagens.get().getSemPerm());
            }

        return false;
    }
}
