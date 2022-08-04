package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Perfil implements CommandExecutor {

    public Perfil(Core core) {
        core.getCommand("perfil").setExecutor(this);
    }

    Item teia = new Item(Material.WEB);
    Item xp = new Item(Material.EXP_BOTTLE);
    Item cabeca = new Item(Material.SKULL_ITEM);
    Inventory perfil;
    Player p2;

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("perfil")) {
            if(args.length == 1) {
                p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    p.sendMessage("§cJogador offline!");
                    return false;
                }
            } else if (args.length == 0){
                p2 = p.getPlayer();
            } else {
                p.sendMessage("§cUse (/perfil [player])");
                return false;
            }

            if(p2 == p) {
                perfil = Bukkit.createInventory(null, 54, "§0Seu perfil:");
            } else {
                perfil = Bukkit.createInventory(null, 54, "§0Perfil - " + p2.getName() + "§0:");
            }

            cabeca.setOwner(p2);
            cabeca.setDisplayName("§a" + p2.getName());
            cabeca.setLore("", "§7 - Informações:", "");
            xp.setDisplayName("§eExperiência");
            xp.setLore("", "§6" + p2.getName() + " §etem", "§6" + p2.getLevel() + "§e níveis de XP.", "");

            teia.setDisplayName("§7Nada aqui...");

            perfil.setItem(4, cabeca.getItem());

            if (p2.getInventory().getHelmet() != null) {
                perfil.setItem(16, p2.getInventory().getHelmet());
            } else {
                perfil.setItem(16, teia.getItem());
            }
            if (p2.getInventory().getChestplate() != null) {
                perfil.setItem(25, p2.getInventory().getChestplate());
            } else {
                perfil.setItem(25, teia.getItem());
            }
            if (p2.getInventory().getLeggings() != null) {
                perfil.setItem(34, p2.getInventory().getLeggings());
            } else {
                perfil.setItem(34, teia.getItem());
            }
            if (p2.getInventory().getBoots() != null) {
                perfil.setItem(43, p2.getInventory().getBoots());
            } else {
                perfil.setItem(43, teia.getItem());
            }
            perfil.setItem(23, xp.getItem());

            p.openInventory(perfil);

        }

        return false;
    }

}
