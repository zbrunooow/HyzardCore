package me.zbrunooow.hyzardessentials.objetos;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MsgCommand {

    private HyzardCommand command;

    private List<String> objects = new ArrayList<>();

    public ConfigurationSection getConfigurationSection() {
        return command.getConfig().getConfigurationSection("Mensagens");
    }

    public MsgCommand(HyzardCommand command) {
        this.command = command;
        FileConfiguration config = command.getConfig();
        loadMensagens();
    }

    public void createMensagens(Runnable run) {
        if (!command.getConfig().contains("Mensagens")) {
            command.getConfig().createSection("Mensagens");
            run.run();
        }
    }

    public void loadMensagens() {
        if (!command.getConfig().contains("Mensagens")) return;
        objects.clear();
        for(String key : command.getConfig().getConfigurationSection("Mensagens").getKeys(false)) {
            String value = command.getConfig().getString("Mensagens." + key).replace('&', '§');
            objects.add(key + "<>" + value);
        }
    }

    public String getMsg(String key) {
        Iterator<String> it = objects.iterator();
        while(it.hasNext()) {
            String linha = it.next();
            String[] args = linha.split("<>");
            String key1 = args[0];
            if (key1.equals(key)) return args[1];
        }
        return null;
    }

    public List<String> getLista(String key) {
        Iterator<String> it = objects.iterator();
        while(it.hasNext()) {
            String linha = it.next();
            String[] args = linha.split("<>");
            String key1 = args[0];
            List<String> lista = new ArrayList<>();
            if (key1.equals(key)) {
                for(String s : command.getConfig().getStringList("Mensagens." + key)) {
                    lista.add(s.replace('&', '§'));
                }
                return lista;
            }
        }
        return null;
    }


    public HyzardCommand getCommand() {
        return command;
    }

    public void setCommand(HyzardCommand command) {
        this.command = command;
    }

    public List<String> getObjects() {
        return objects;
    }

    public void setObjects(List<String> objects) {
        this.objects = objects;
    }
}
