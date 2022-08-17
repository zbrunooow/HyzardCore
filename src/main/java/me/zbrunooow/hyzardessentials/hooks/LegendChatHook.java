package me.zbrunooow.hyzardessentials.hooks;

import br.com.devpaulo.legendchat.api.Legendchat;
import br.com.devpaulo.legendchat.channels.ChannelManager;
import me.zbrunooow.hyzardessentials.Core;

public class LegendChatHook {

    public boolean hookLegendChat() {
        try {
            if (Core.getInstance().getServer().getPluginManager().getPlugin("Legendchat") != null) {
                ChannelManager channelManager = Legendchat.getChannelManager();
                return true;
            }
        } catch (NoClassDefFoundError e) {
            return false;
        }
        return false;
    }

}
