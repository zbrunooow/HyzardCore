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

        new Basicos(this);
        new Echest(this);
        new Clear(this);
        new Hat(this);
        new Vanish(this);
        new Alerta(this);
        new Gamemode(this);

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
