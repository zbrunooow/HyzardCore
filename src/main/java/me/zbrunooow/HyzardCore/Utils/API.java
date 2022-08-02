package me.zbrunooow.HyzardCore.Utils;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class API {

    public void sendMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    public static API get(){
        return Core.getInstance().getApi();
    }

}
