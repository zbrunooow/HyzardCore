package me.zbrunooow.hyzardessentials.hooks;

import me.zbrunooow.hyzardessentials.Core;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultHook {

    public static Economy eco = null;
    public boolean setupEconomy() {
        try {
            if (Core.getInstance().getServer().getPluginManager().getPlugin("Vault") != null) {
                RegisteredServiceProvider<Economy> economyProvider = Core.getInstance().getServer().getServicesManager().getRegistration(Economy.class);
                if (economyProvider != null)
                    eco = (Economy) economyProvider.getProvider();
                return (eco != null);
            }
        } catch (Exception ex) {
            return false;
        }
        return false;
    }

}
