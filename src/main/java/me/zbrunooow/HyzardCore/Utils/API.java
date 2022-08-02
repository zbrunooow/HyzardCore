package me.zbrunooow.HyzardCore.Utils;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class API {

    Inventory perfil = Bukkit.createInventory(null, 54, "§0§lSeu Perfil:");
    ItemStack teia = new ItemStack(Material.WEB);
    ItemStack xp = new ItemStack(Material.EXP_BOTTLE);
    ItemMeta teiameta = teia.getItemMeta();
    ItemMeta xpmeta = xp.getItemMeta();

    public void sendMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    public boolean cooldownPlayer(String nomecooldown, int tempominutos, Player player) {
        if (!player.hasMetadata("cooldown" + nomecooldown)) {
            player.setMetadata("cooldown" + nomecooldown, new FixedMetadataValue(Core.getInstance(), System.currentTimeMillis()));
            return true;
        }
        long time = System.currentTimeMillis() - (long) player.getMetadata("cooldown" + nomecooldown).get(0).value();
        if (time >= 60000* (long) tempominutos) player.removeMetadata("cooldown" + nomecooldown, Core.getInstance());
        if (player.hasMetadata("cooldown" + nomecooldown)) {
            player.sendMessage("§cVocê precisa aguardar §4" + (30 - TimeUnit.MILLISECONDS.toMinutes(time)) + " §cminutos para " + nomecooldown + " §cnovamente.");
        }
        return false;
    }

    public void abrirPerfil(Player player) {
        List<String> lorexp = new ArrayList<>();
        lorexp.add("");
        lorexp.add("§6" + player.getName() + " §etem");
        lorexp.add("§6" + player.getLevel() + " §eníveis de XP.");
        lorexp.add("");

        xpmeta.setDisplayName("§eExperiência");
        xpmeta.setLore(lorexp);
        xp.setItemMeta(xpmeta);
        teiameta.setDisplayName("§7Nada aqui...");
        teia.setItemMeta(teiameta);
        if(player.getInventory().getHelmet() != null) {
            perfil.setItem(16, player.getInventory().getHelmet());
        } else {
            perfil.setItem(16, teia);
        }
        if(player.getInventory().getChestplate() != null) {
            perfil.setItem(25, player.getInventory().getChestplate());
        } else {
            perfil.setItem(25, teia);
        }
        if(player.getInventory().getLeggings() != null) {
            perfil.setItem(34, player.getInventory().getLeggings());
        } else {
            perfil.setItem(34, teia);
        }
        if(player.getInventory().getBoots() != null) {
            perfil.setItem(43, player.getInventory().getBoots());
        } else {
            perfil.setItem(43, teia);
        }
        perfil.setItem(23, xp);





        player.openInventory(perfil);
    }

    public static API get(){
        return Core.getInstance().getApi();
    }

}
