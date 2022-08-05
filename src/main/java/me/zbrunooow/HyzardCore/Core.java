package me.zbrunooow.HyzardCore;

import me.zbrunooow.HyzardCore.Comandos.*;
import me.zbrunooow.HyzardCore.Listeners.BackListener;
import me.zbrunooow.HyzardCore.Listeners.EchestListener;
import me.zbrunooow.HyzardCore.Listeners.InvseeListener;
import me.zbrunooow.HyzardCore.Objetos.Manager;
import me.zbrunooow.HyzardCore.Utils.API;
import me.zbrunooow.HyzardCore.Utils.LocsFile;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Core extends JavaPlugin {

    public static Core instance;
    private Mensagens msgs;
    private API api;
    private LocsFile locsfile;
    private Locations locations;
    private Manager manager;

    public String prefix = "§6[HyzardCore §ev" + getDescription().getVersion() + "§6] ";

    public void onEnable() {
        instance = this;
        locsfile = new LocsFile();
        locations = new Locations();
        manager = new Manager();

        saveDefaultConfig();
        LocsFile.get().saveDefaultLocs();

        new Alerta(this);
        new Aviso(this);
        new Back(this);
        new Basicos(this);
        new Clear(this);
        new ClearChat(this);
        new Divulgar(this);
        new Echest(this);
        new Feed(this);
        new Gamemode(this);
        new Hat(this);
        new Head(this);
        new Heal(this);
        new HyzardCore(this);
        new Info(this);
        new Invsee(this);
        new Online(this);
        new Perfil(this);
        new Ping(this);
        new Renomear(this);
        new SetLocs(this);
        new Spawn(this);
        new Vanish(this);
        new Warp(this);

        List<Listener> eventos = new ArrayList<>();
        eventos.add(new InvseeListener());
        eventos.add(new EchestListener());
        eventos.add(new BackListener());
        eventos.forEach(evento -> Bukkit.getPluginManager().registerEvents(evento, this));

        reloadPlugin();

        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(prefix + "§fPlugin §ahabilitado §fcom sucesso.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(prefix + "§fPlugin §cdesabilitado§f.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    public void reloadPlugin() {
        reloadConfig();
        msgs = new Mensagens();
        api = new API();

        manager.getCommands().forEach(cmd -> {
            cmd.reloadConfig();
            cmd.getMensagens().loadMensagens();
            cmd.loadConfig();
        });

    }

    public static Core getInstance() {
        return instance;
    }

    public LocsFile getLocsfile() {return locsfile;}
    public Locations getLocations() {return locations;}
    public Mensagens getMsgs() {
        return msgs;
    }
    public API getApi() {
        return api;
    }
    public Manager getManager() {
        return manager;
    }

}
