package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;

public class LocsFile {

    private String fileName;
    private Core plugin;
    private File configFile;
    private FileConfiguration fileConfiguration;

    @SuppressWarnings("deprecation")
    public LocsFile(Core plugin, String fileName) {
        if (plugin == null) {
            throw new IllegalArgumentException("plugin cannot be null");
        }
        if (!plugin.isEnabled()) {
            throw new IllegalArgumentException("plugin must be enabled");
        }
        this.plugin = plugin;
        this.fileName = fileName;
        File dataFolder = plugin.getDataFolder();
        if (dataFolder == null) {
            throw new IllegalStateException();
        }
        this.configFile = new File(plugin.getDataFolder(), fileName);
    }

    public void saveDefaultConfig() {
        if (!this.configFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
    }

    @SuppressWarnings("deprecation")
    public void reloadConfig() {
        if (!this.configFile.exists()) {
            this.plugin.saveResource(this.fileName, false);
        }
        this.fileConfiguration = (FileConfiguration) YamlConfiguration.loadConfiguration(this.configFile);

        InputStream defConfigStream = this.plugin.getResource(this.fileName);
        if (defConfigStream != null) {
        }
    }

    public FileConfiguration getConfig() {
        if (this.fileConfiguration == null) {
            reloadConfig();
        }
        return this.fileConfiguration;
    }

    public void saveConfig() {
        try {
            getConfig().save(this.configFile);
        } catch (Exception exception) {}
    }

    public void createWarpSection() {
        if(!fileConfiguration.contains("Warps")) {
            fileConfiguration.createSection("Warps");
        }
    }

}
