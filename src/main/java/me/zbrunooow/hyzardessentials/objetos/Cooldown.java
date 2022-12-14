package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Config;
import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.TimeUnit;

public class Cooldown {

    private String nomecooldown;
    private int tempominutos;
    private long temporestante;
    private Player player;

    public Cooldown(String nomecooldown, int tempominutos, Player player) {
        this.nomecooldown = nomecooldown;
        this.tempominutos = tempominutos;
        this.player = player;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void createCooldown() {
        getPlayer().setMetadata("cooldown" + this.nomecooldown, new FixedMetadataValue(Core.getInstance(), System.currentTimeMillis()));
    }

    public long getTime() {
        return this.temporestante = this.tempominutos - TimeUnit.MILLISECONDS.toMinutes(temporestante);
    }

    public boolean hasCooldown() {
        if (this.player.hasMetadata("cooldown" + this.nomecooldown)) {
            long time;
            try {
                time = System.currentTimeMillis() - (Long) ((MetadataValue) player.getMetadata("cooldown" + nomecooldown).get(0)).value();
            } catch (Exception ignored) {
                return false;
            }
            if (time >= 60000L * tempominutos) {
                return removeCooldown();
            } else {
                this.temporestante = System.currentTimeMillis() - (Long) ((MetadataValue) player.getMetadata("cooldown" + nomecooldown).get(0)).value();
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean removeCooldown() {
        long time = System.currentTimeMillis() - (Long) ((MetadataValue) player.getMetadata("cooldown" + nomecooldown).get(0)).value();
        if (time >= 60000L * tempominutos) {
            player.removeMetadata("cooldown" + nomecooldown, Core.getInstance());
        }
        return false;
    }

    public void getCooldownTicket(HyzardCommand cmd) {
        player.sendMessage(cmd.getMensagens().getMsg("Cooldown").replace("{tempo}", String.valueOf(this.getTime())).replace("{tipocooldown}", "Ajuda"));
    }

    public void getCooldown(HyzardCommand cmd) {
        player.sendMessage(cmd.getMensagens().getMsg("Cooldown").replace("{tempo}", String.valueOf(this.getTime())).replace("{tipocooldown}", "Divulgar"));
    }

}
