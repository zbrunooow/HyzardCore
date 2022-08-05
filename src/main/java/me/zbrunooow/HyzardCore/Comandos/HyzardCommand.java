package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Objetos.Manager;
import me.zbrunooow.HyzardCore.Objetos.MsgCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HyzardCommand extends Command {

    private File file;
    private FileConfiguration config;
    private MsgCommand mensagens;
    private Core plugin;
    private List<String> objects = new ArrayList<>();

    public HyzardCommand(Core plugin, String name, String description, String usageMessage, List<String> aliases) {
        super(name, description, usageMessage, aliases);
        this.plugin = plugin;
        if (!new File(plugin.getDataFolder()+"/comandos").exists()) new File(plugin.getDataFolder()+"/comandos").mkdir();
        this.file = new File(plugin.getDataFolder() + "/comandos/" + name + ".yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
                config = YamlConfiguration.loadConfiguration(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        mensagens = new MsgCommand(this);
        Manager.get().getCommands().add(this);
    }

    public void setExecutor(CommandExecutor executor){
        plugin.getCommand(getName()).setExecutor(executor);
    }

    public void reloadConfig(){
        try {
            config = YamlConfiguration.loadConfiguration(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isConfigEmpty() {
        return file.length() == 0;
    }

    @Override
    public boolean execute(CommandSender s, String lb, String[] args) {
        return false;
    }

    @Override
    public List<String> getAliases() {
        return super.getAliases();
    }

    @Override
    public String getDescription() {
        return super.getDescription();
    }

    @Override
    public String getLabel() {
        return super.getLabel();
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public String getPermission() {
        return super.getPermission();
    }

    @Override
    public String getPermissionMessage() {
        return super.getPermissionMessage();
    }

    @Override
    public String getUsage() {
        return super.getUsage();
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public void createConfig(Runnable run) {
        if(!config.contains("Config")) {
            config.createSection("Config");
            run.run();
        }
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public void setConfig(FileConfiguration config) {
        this.config = config;
    }

    public MsgCommand getMensagens() {
        return mensagens;
    }

    public Core getPlugin() {
        return plugin;
    }

    public void setPlugin(Core plugin) {
        this.plugin = plugin;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }

    public String getFromConfig(String key) {
        Iterator<String> it = objects.iterator();
        while(it.hasNext()) {
            String o = it.next();

            String[] args = o.split("<>");
            if(args[0].equals(key)) {
                return args[1];
            }
        }
        return null;
    }

    public void loadConfig() {
        if(!config.contains("Config")) return;

        objects.clear();
        for(String key : config.getConfigurationSection("Config").getKeys(false)) {
            objects.add(key + "<>" + config.getString("Config." + key));
        }
    }

    public ConfigurationSection getConfigurationSection(){
        return config.getConfigurationSection("Config");
    }

    public void setMensagens(MsgCommand mensagens) {
        this.mensagens = mensagens;
    }

}
