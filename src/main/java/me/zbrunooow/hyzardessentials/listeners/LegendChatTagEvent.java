package me.zbrunooow.hyzardessentials.listeners;

import br.com.devpaulo.legendchat.api.events.ChatMessageEvent;
import me.zbrunooow.hyzardessentials.Config;
import me.zbrunooow.hyzardessentials.Manager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class LegendChatTagEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    private void onChat(ChatMessageEvent e) {
        Player p = e.getSender();
        if (e.getTags().contains("viciado") && p.getName().equalsIgnoreCase(Manager.get().getViciado())) {
            e.setTagValue("viciado", Config.get().getTagViciado());
        }
    }
}
