package me.zbrunooow.HyzardCore.Objetos;

import me.zbrunooow.HyzardCore.Comandos.HyzardCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MsgCommand {

    private HyzardCommand command;

    private List<String> objects = new ArrayList<>();

    private class Objeto {

        private String key;
        private Object value;

        public Objeto(String key, Object value) {
            this.value = value;
            this.key = key;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }

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
