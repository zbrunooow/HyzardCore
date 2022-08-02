package me.zbrunooow.HyzardCore;

import me.zbrunooow.HyzardCore.Comandos.*;
import me.zbrunooow.HyzardCore.Utils.API;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static Core instance;
    private Mensagens msgs;
    private API api;

    public String prefix = "§6[HyzardCore §ev" + getDescription().getVersion() + "§6] ";

    public void onEnable() {
        instance = this;
        msgs = new Mensagens();
        api = new API();

        saveDefaultConfig();

        new Alerta(this);
        new Aviso(this);
        new Basicos(this);
        new Clear(this);
        new Divulgar(this);
        new Echest(this);
        new Gamemode(this);
        new Hat(this);
        new Ping(this);
        new Vanish(this);

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

    public API getApi() {
        return api;
    }

}
