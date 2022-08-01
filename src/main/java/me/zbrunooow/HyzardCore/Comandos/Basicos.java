package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;

public class Basicos implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("craft")) {
            if (p.hasPermission("hyzardcore.craft") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(Bukkit.createInventory(p, InventoryType.WORKBENCH));
            }
        }

        if(cmd.getName().equalsIgnoreCase("bigorna"))
            if(p.hasPermission("hyzardcore.bigorna") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(Bukkit.createInventory(p, InventoryType.ANVIL));
            }else{
                p.sendMessage(Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§'));
            }

        if(cmd.getName().equalsIgnoreCase("echest")) {
            if (p.hasPermission("hyzardcore.echest") || p.hasPermission("hyzardcore.*")) {
                p.openInventory(p.getEnderChest());
            } else {
                p.sendMessage(Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§'));
            }
        }

        if(cmd.getName().equalsIgnoreCase("clear")) {
            if (p.hasPermission("hyzardcore.clear") || p.hasPermission("hyzardcore.*")) {
                if (args.length == 1) {
                    try {
                        Player p2 = Bukkit.getPlayerExact(args[0]);
                        p2.getInventory().clear();
                        p2.sendMessage("§aSeu inventário foi limpo por §2" + p.getName() + "§a!");
                        p.sendMessage("§aVocê limpou o inventário de §2" + p2.getName() + " §acom sucesso.");
                    } catch (Exception ignored) {
                        p.sendMessage("§cJogador offline!");
                    }
                } else {
                    p.getInventory().clear();
                    p.sendMessage("§aVocê limpou seu inventário com sucesso!");
                }
            } else {
                p.sendMessage(Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§'));
            }
        }

        if(cmd.getName().equalsIgnoreCase("hat")) {
            if (p.hasPermission("hyzardcore.hat") || p.hasPermission("hyzardcore.*")) {
                if(p.getInventory().getHelmet() == null) {
                    p.getInventory().setHelmet(p.getItemInHand());
                    p.setItemInHand(null);
                    p.sendMessage("§aVocê equipou um novo chapéu!");
                } else {
                    p.sendMessage("§cVocê já tem um chapéu equipado!");
                }
            }else{
                p.sendMessage(Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§'));
            }
        }

        return false;
    }
}
