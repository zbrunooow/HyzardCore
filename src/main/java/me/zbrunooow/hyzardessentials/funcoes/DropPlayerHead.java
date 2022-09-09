package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import me.zbrunooow.hyzardessentials.utils.Item;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Random;

public class DropPlayerHead implements Listener {

    @EventHandler
    public void aoMorrer(PlayerDeathEvent e) {
        Random i = new Random();
        int chance = i.nextInt(101);
        if(chance <= Config.get().getChanceCabeca()) {
            Item cabeca = new Item(Material.SKULL_ITEM);
            cabeca.setOwner(e.getEntity().getName());
            cabeca.setDisplayName("§a" + e.getEntity().getName());
            World world = e.getEntity().getWorld();
            world.dropItem(e.getEntity().getLocation(), cabeca.build().clone());
        }
    }

}
