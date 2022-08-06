package me.zbrunooow.HyzardCore.Objetos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;

public class EnderChestAPI {

    private File file;
    private FileConfiguration echest;
    private Player p;
    private Core plugin;

    public EnderChestAPI(Core plugin, Player p) {
        this.p = p;
        this.plugin = plugin;
        if (!new File(plugin.getDataFolder()+"/EnderChests").exists()) new File(plugin.getDataFolder()+"/EnderChests").mkdir();
        this.file = new File(plugin.getDataFolder() + "/EnderChests/" + p.getName() + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                echest = YamlConfiguration.loadConfiguration(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        echest = YamlConfiguration.loadConfiguration(file);
    }

    public ConfigurationSection getEnderChest() {
        return echest.getConfigurationSection(p.getName() + ".enderchest");
    }

    public void saveEnderChest() {
        try {
            echest.save(file);
        } catch (Exception var2) {
            Core.getInstance().getLogger().log(Level.SEVERE, "Algo de errado não está certo", var2);
        }
    }

    public void saveDefaultEchest() {
        try {
            echest.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reloadEnderChest() {
        if (!this.file.exists()) {
            this.plugin.saveResource(this.file.getName(), false);
        }
        this.echest = (FileConfiguration) YamlConfiguration.loadConfiguration(this.file);

        InputStream defConfigStream = this.plugin.getResource(this.file.getName());
        if (defConfigStream != null) {
        }
    }

    public void setupInventory() {
        int rows;
        rows = 4;
        p.sendMessage("debug1");

        if (!echest.contains(p.getName())) {
            echest.createSection(p.getName());
            ConfigurationSection section = echest.getConfigurationSection(p.getName());
            section.set("enderchest", API.get().serializeItems(new ItemStack[9*rows]));
        } else {
            ConfigurationSection section = echest.getConfigurationSection(p.getName());
            section.set("enderchest", API.get().serializeItems(((Inventory)p.getMetadata("enderchest").get(0).value()).getContents().clone()));
        }
        saveDefaultEchest();
        reloadEnderChest();

        if (p.hasMetadata("enderchest") && p.getMetadata("enderchest").isEmpty()) p.removeMetadata("enderchest", plugin);
        Inventory inv = p.hasMetadata("enderchest") ? (Inventory) p.getMetadata("enderchest").get(0).value() : Bukkit.createInventory(null, 9*rows, "Baú do Fim");
        if (!p.hasMetadata("enderchest")) {
            inv.setContents(API.get().unserializeItems(echest.getString(p.getName() + ".enderchest")));
        }
        p.setMetadata("enderchest", new FixedMetadataValue(plugin, inv));
    }

    public ItemStack loadItem(ConfigurationSection config) {
        ItemStack item = config.getItemStack(".item");
        if (item == null)
            return new ItemStack(Material.AIR);
        return item;
    }

    public void saveItem(ConfigurationSection config, ItemStack item) {
        config.set("item", item);
    }


}
