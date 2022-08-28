package me.zbrunooow.hyzardessentials.listeners;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.material.Directional;

import java.util.Set;

public class AnvilListener implements Listener {

    @EventHandler
    public void fecharBigorna(InventoryOpenEvent e) {
        if(e.getInventory().getName().contains("Repair")) {
            if(e.getPlayer().getTargetBlock((Set<Material>) null, 5).getType() == Material.ANVIL) {
                Block targetBlock = e.getPlayer().getTargetBlock((Set<Material>) null, 5);
// fzr dps

            }
        }
    }

//    @EventHandler
//    public void aplicarBigorna()

}
