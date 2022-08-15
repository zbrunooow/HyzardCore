package me.zbrunooow.hyzardessentials.utils;

import me.zbrunooow.hyzardessentials.Config;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class SempreDiaNoite {

    public SempreDiaNoite() {
        for(World world : Bukkit.getWorlds()) {
            if(Config.get().getMundosDia().contains(world.getName())) {
                if(Config.get().getSempredia()) {
                    world.setTime(1000);
                    world.setGameRuleValue("doDaylightCycle", "false");
                } else {
                    world.setTime(1000);
                    world.setGameRuleValue("doDaylightCycle", "true");
                }
            }
        }

    }

}
