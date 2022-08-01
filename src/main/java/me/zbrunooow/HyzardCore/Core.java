package me.zbrunooow.HyzardCore;

import me.zbrunooow.HyzardCore.Comandos.Basicos;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    public static Core instance;

    public String prefix = "§6[HyzardCore §ev" + getDescription().getVersion() + "§6] ";

    public void onEnable() {
        instance = this;
        saveDefaultConfig();

        getCommand("craft").setExecutor(new Basicos());
        getCommand("bigorna").setExecutor(new Basicos());
        getCommand("echest").setExecutor(new Basicos());
        getCommand("clear").setExecutor(new Basicos());
        getCommand("hat").setExecutor(new Basicos());

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

}
