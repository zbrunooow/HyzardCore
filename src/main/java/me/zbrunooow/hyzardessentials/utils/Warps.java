package me.zbrunooow.hyzardessentials.utils;

import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;

public class Warps {

    private Core core = Core.getInstance();
    private FileConfiguration config = core.getWarps().getConfig();



    private String[] getWarpsSection() {
        if(core.getWarps().getConfig().getConfigurationSection("Warps") != null) {
            return core.getWarps().getConfig().getConfigurationSection("Warps").getKeys(false).toArray(new String[0]);
        }
        return null;
    }

    public int getDelay(String nome) {
        return config.getInt("Warps." + nome.toLowerCase() + ".Delay");
    }

    private List<String> nomes = new ArrayList<>();
    {
        if(getWarpsSection() != null) {
            for(String nome : getWarpsSection()) {
                nomes.add(core.getWarps().getConfig().getString("Warps." + nome + ".Nome"));
            }
        }
    }

    private String listawarps;
    {
        for(String w : nomes) {
            if(listawarps == null) {
                listawarps = w;
            } else {
                listawarps = listawarps + ", " + w;
            }
        }
    }

    public String getWarps() {
        if(!nomes.isEmpty()) {
            return listawarps;
        }
        return "Nenhuma warp foi setada";
    }

    public static Warps get() {
        return Core.getInstance().getWarpAPI();
    }
}
