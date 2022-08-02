package me.zbrunooow.HyzardCore.Utils;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.concurrent.TimeUnit;

public class API {

    public void sendMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    public boolean cooldownPlayer(String nomecooldown, int tempominutos, Player player) {
        if (!player.hasMetadata("cooldown" + nomecooldown)) {
            player.setMetadata("cooldown" + nomecooldown, new FixedMetadataValue(Core.getInstance(), System.currentTimeMillis()));
            return true;
        }
        long time = System.currentTimeMillis() - (long) player.getMetadata("cooldown" + nomecooldown).get(0).value();
        if (time >= 60000* (long) tempominutos) player.removeMetadata("cooldown" + nomecooldown, Core.getInstance());
        if (player.hasMetadata("cooldown" + nomecooldown)) {
            player.sendMessage("§cVocê precisa aguardar §4" + (30 - TimeUnit.MILLISECONDS.toMinutes(time)) + " §cminutos para " + nomecooldown + " §cnovamente.");
        }
        return false;
    }

    public static API get(){
        return Core.getInstance().getApi();
    }

}
