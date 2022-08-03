package me.zbrunooow.HyzardCore.Utils;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class LocsFile {

    private File filelocs = null;
    private FileConfiguration fileConfigurationlocs = null;
    File verificarLocs = new File(Core.getInstance().getDataFolder(), "locs.yml");

    public void saveDefaultLocs(){
        if(!verificarLocs.exists()) {
            Core.getInstance().saveResource("locs.yml", false);
        }
    }

    public FileConfiguration getLocs() {
        if (fileConfigurationlocs == null) {
            reloadLocs();
        }
        return fileConfigurationlocs;
    }

    public void saveLocs() {
        try {
            getLocs().save(this.filelocs);
        } catch (Exception ignored) { }
    }

    public void reloadLocs() {
        if (this.filelocs == null) {
            this.filelocs = new File(Core.getInstance().getDataFolder(), "locs.yml");
        }
        fileConfigurationlocs = (FileConfiguration) YamlConfiguration.loadConfiguration(this.filelocs);
        if (fileConfigurationlocs != null) {
            YamlConfiguration locs = YamlConfiguration.loadConfiguration(this.filelocs);
            fileConfigurationlocs.setDefaults((Configuration) locs);
        }
    }



    public static LocsFile get(){
        return Core.getInstance().getLocsfile();
    }

}
