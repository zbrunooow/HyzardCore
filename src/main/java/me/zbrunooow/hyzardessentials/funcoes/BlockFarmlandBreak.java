package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockFarmlandBreak implements Listener {

    @EventHandler
    public void aoQuebrarFarmland(PlayerInteractEvent e) {
        if(e.getAction() != Action.PHYSICAL) return;
        if(e.getClickedBlock().getType() == Material.SOIL) {
            if(Config.get().getBloquearestragarplantacao()) {
                e.setCancelled(true);
            }
        }
    }

}
