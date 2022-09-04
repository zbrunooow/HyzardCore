package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.utils.AnvilAPI;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockAnvilBreak implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void aoQuebrar(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.ANVIL) {
                AnvilAPI.openAnvilInventory(e.getPlayer());
                e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.ORB_PICKUP, 1, 10);
            }
        }

    }

}
