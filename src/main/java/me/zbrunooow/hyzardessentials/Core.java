package me.zbrunooow.hyzardessentials;

import me.zbrunooow.hyzardessentials.comandos.*;
import me.zbrunooow.hyzardessentials.listeners.*;
import me.zbrunooow.hyzardessentials.objetos.Manager;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.objetos.LocsFile;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Core extends JavaPlugin {

    public static Core instance;
    private Mensagens msgs;
    private API api;
    private Locations locations;
    private Manager manager;

    private LocsFile locs;

    public String prefix = "§6[HyzardEssentials §ev" + getDescription().getVersion() + "§6] ";

    public void onEnable() {
        instance = this;
        locations = new Locations();
        manager = new Manager();

        locs = new LocsFile(this, "locs.yml");

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aPlaceholderAPI encontrado! Carregando placeholders.");
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cPlaceholderAPI não encontrado, os placeholders não funcionarão.");
        }

        saveDefaultConfig();
        locs.saveDefaultConfig();

        new Alerta(this);
        new Aviso(this);
        new Back(this);
        new Basicos(this);
        new Clear(this);
        new ClearChat(this);
        new Compactar(this);
        new Cores(this);
        new Derreter(this);
        new Divulgar(this);
        new Echest(this);
        new Enchant(this);
        new Feed(this);
        new Fly(this);
        new Gamemode(this);
        new Give(this);
        new GiveOLD(this);
        new God(this);
        new Hat(this);
        new Head(this);
        new Heal(this);
        new HyzardEssentials(this);
        new Info(this);
        new Invsee(this);
        new Kill(this);
        new Lixo(this);
        new Lore(this);
        new Luz(this);
        new Online(this);
        new Perfil(this);
        new Ping(this);
        new Potion(this);
        new Renomear(this);
        new SetSpawn(this);
        new SetWarp(this);
        new Spawn(this);
        new Sudo(this);
        new Teleport(this);
        new Tpa(this);
        new TpAccept(this);
        new TpAll(this);
        new TpHere(this);
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

        msgs = new Mensagens();
        api = new API();

        manager.getCommands().forEach(cmd -> {
            cmd.reloadConfig();
            cmd.getMensagens().loadMensagens();
            cmd.loadConfig();
        });

        manager.getWarps().clear();
        File pasta = new File(getDataFolder() + "/warps");
        if (!pasta.exists()) pasta.mkdir();
        for(File file : pasta.listFiles()) {
            if (!file.getName().endsWith(".json")) continue;
            String nome = file.getName().replace(".json", new String());
            new me.zbrunooow.hyzardessentials.objetos.Warp(nome, null);
        }

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

}
