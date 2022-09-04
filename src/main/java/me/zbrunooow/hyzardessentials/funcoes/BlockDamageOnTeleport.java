package me.zbrunooow.hyzardessentials.funcoes;

import me.zbrunooow.hyzardessentials.Config;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class BlockDamageOnTeleport implements Listener {

    @EventHandler
    public void aoTeleportar(PlayerTeleportEvent e) {
        EntityPlayer player = ((CraftPlayer)e.getPlayer()).getHandle();
        player.noDamageTicks = Config.get().getTempoSemDanoTeleportar()*20;
    }

}
