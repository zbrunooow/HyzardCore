package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Perfil implements CommandExecutor {

    public Perfil(Core core) {
        core.getCommand("perfil").setExecutor(this);
    }

    Inventory perfil = Bukkit.createInventory(null, 54, "§0§lSeu Perfil:");
    ItemStack teia = new ItemStack(Material.WEB);
    ItemStack xp = new ItemStack(Material.EXP_BOTTLE);
    ItemMeta teiameta = teia.getItemMeta();
    ItemMeta xpmeta = xp.getItemMeta();

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        List<String> lorexp = new ArrayList<>();
        lorexp.add("");
        lorexp.add("§6" + p.getName() + " §etem");
        lorexp.add("§6" + p.getLevel() + " §eníveis de XP.");
        lorexp.add("");

        xpmeta.setDisplayName("§eExperiência");
        xpmeta.setLore(lorexp);
        xp.setItemMeta(xpmeta);
        teiameta.setDisplayName("§7Nada aqui...");
        teia.setItemMeta(teiameta);
        if(p.getInventory().getHelmet() != null) {
            perfil.setItem(16, p.getInventory().getHelmet());
        } else {
            perfil.setItem(16, teia);
        }
        if(p.getInventory().getChestplate() != null) {
            perfil.setItem(25, p.getInventory().getChestplate());
        } else {
            perfil.setItem(25, teia);
        }
        if(p.getInventory().getLeggings() != null) {
            perfil.setItem(34, p.getInventory().getLeggings());
        } else {
            perfil.setItem(34, teia);
        }
        if(p.getInventory().getBoots() != null) {
            perfil.setItem(43, p.getInventory().getBoots());
        } else {
            perfil.setItem(43, teia);
        }
        perfil.setItem(23, xp);

        p.openInventory(perfil);

        return false;
    }

}
