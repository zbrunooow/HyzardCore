package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class Basicos implements CommandExecutor {

    private final String semperm = Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§');

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if (cmd.getName().equalsIgnoreCase("craft")) {
            if (p.hasPermission("hyzardcore.craft") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(Bukkit.createInventory(p, InventoryType.WORKBENCH));
            } else {
                p.sendMessage(semperm);
            }
        }

        if (cmd.getName().equalsIgnoreCase("bigorna"))
            if (p.hasPermission("hyzardcore.bigorna") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(Bukkit.createInventory(p, InventoryType.ANVIL));
            } else {
                p.sendMessage(semperm);
            }

        return false;
    }
}
