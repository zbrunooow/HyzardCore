package me.zbrunooow.HyzardCore;

import me.zbrunooow.HyzardCore.Comandos.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static Core instance;
    private Mensagens msgs;

    public String prefix = "§6[HyzardCore §ev" + getDescription().getVersion() + "§6] ";

    public void onEnable() {
        instance = this;
        msgs = new Mensagens();

        saveDefaultConfig();

        getCommand("craft").setExecutor(new Basicos());
        getCommand("bigorna").setExecutor(new Basicos());
        getCommand("echest").setExecutor(new Echest());
        getCommand("clear").setExecutor(new Clear());
        getCommand("hat").setExecutor(new Hat());
        getCommand("vanish").setExecutor(new Vanish());
        getCommand("alerta").setExecutor(new Alerta());

        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(prefix + "§fPlugin §ahabilitado §fcom sucesso.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(prefix + "§fPlugin §cdesabilitado§f.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public static Core getInstance() {
        return instance;
    }

    public Mensagens getMsgs() {
        return msgs;
    }

}
