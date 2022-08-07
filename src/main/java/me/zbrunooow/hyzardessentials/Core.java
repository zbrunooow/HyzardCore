package me.zbrunooow.hyzardessentials;

import me.zbrunooow.hyzardessentials.Comandos.*;
import me.zbrunooow.hyzardessentials.listeners.*;
import me.zbrunooow.hyzardessentials.objetos.Manager;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.objetos.LocsFile;
import me.zbrunooow.hyzardessentials.utils.Warps;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class Core extends JavaPlugin {

    public static Core instance;
    private Mensagens msgs;
    private API api;
    private Locations locations;
    private Manager manager;

    private Warps warpapi;

    private LocsFile warps;
    private LocsFile locs;

    public String prefix = "§6[HyzardEssentials §ev" + getDescription().getVersion() + "§6] ";

    public void onEnable() {
        instance = this;
        locations = new Locations();
        manager = new Manager();

        locs = new LocsFile(this, "locs.yml");

        warps = new LocsFile(this, "warps.yml");

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aPlaceholderAPI encontrado! Carregando placeholders.");
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cPlaceholderAPI não encontrado, os placeholders não funcionarão.");
        }

        saveDefaultConfig();
        locs.saveDefaultConfig();
        warps.saveDefaultConfig();

        new Alerta(this);
        new Aviso(this);
        new Back(this);
        new Basicos(this);
        new Clear(this);
        new ClearChat(this);
        new Divulgar(this);
        new Echest(this);
        new Feed(this);
        new Fly(this);
        new Gamemode(this);
        new Give(this);
        new God(this);
        new Hat(this);
        new Head(this);
        new Heal(this);
        new HyzardEssentials(this);
        new Info(this);
        new Invsee(this);
        new Luz(this);
        new Online(this);
        new Perfil(this);
        new Ping(this);
        new Renomear(this);
        new SetSpawn(this);
        new SetWarp(this);
        new Spawn(this);
        new Vanish(this);
        new Warp(this);

        List<Listener> eventos = new ArrayList<>();
        eventos.add(new BackListener());
        eventos.add(new EchestListener());
        eventos.add(new FlyListener());
        eventos.add(new GodListener());
        eventos.add(new InvseeListener());
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

        warpapi = new Warps();
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

    public LocsFile getLocs() {
        return locs;
    }

    public Warps getWarpAPI() {
        return warpapi;
    }

    public Warps reloadWarpAPI() {
        return warpapi = new Warps();
    }

    public LocsFile getWarps() {
        return warps;
    }

}
