package me.zbrunooow.hyzardessentials.listeners;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

public class DesativarChuva implements Listener {

    @EventHandler
    public void aoChover(WeatherChangeEvent e) {
        if(e.toWeatherState()) {
            if(Config.get().getMundosChuva().contains(e.getWorld().getName())) {
                if(Config.get().getDesativarChuva()) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
