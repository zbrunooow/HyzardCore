package me.zbrunooow.hyzardessentials.listeners;

import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Item;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class CaixaListener implements Listener {

    @EventHandler
    public void aoClicarCaixa(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_AIR) {
            ItemStack item = e.getPlayer().getItemInHand();
            if(item.getItemMeta().hasDisplayName()) {
                if(item.getItemMeta().getDisplayName().equals("§cMensagem de morte personalizada:")) {
                    // setar msg
                }
                if(item.getType() == Material.SKULL_ITEM) {
                    if (item.getItemMeta().getDisplayName().equals("§cCaixa 'mensagens de morte'!")) {
                        API.get().abrirCaixa(e.getPlayer());
                        Item novo = new Item(item);
                        novo.setAmount(item.getAmount()-1);
                        e.getPlayer().getInventory().setItemInHand(item.getAmount() == 1 ? new ItemStack(Material.AIR) : novo.build().clone());
                    }
                }
            }
        }
    }

}
