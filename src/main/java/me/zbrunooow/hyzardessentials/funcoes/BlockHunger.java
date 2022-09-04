package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class BlockHunger implements Listener {

    @EventHandler
    public void aoFicarComFome(FoodLevelChangeEvent e) {
        if(Config.get().getBloquearfome()) {
            e.setCancelled(true);
        }
    }

}
