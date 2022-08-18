package me.zbrunooow.hyzardessentials;

import me.zbrunooow.hyzardessentials.comandos.*;
import me.zbrunooow.hyzardessentials.hooks.LegendChatHook;
import me.zbrunooow.hyzardessentials.hooks.VaultHook;
import me.zbrunooow.hyzardessentials.listeners.*;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import me.zbrunooow.hyzardessentials.objetos.Kit;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.objetos.LocsFile;
import me.zbrunooow.hyzardessentials.utils.SempreDiaNoite;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public final class Core extends JavaPlugin {

    public static Core instance;
    private Mensagens msgs;
    private Config config;
    private API api;
    private Locations locations;
    private Manager manager;
    private VaultHook vaultHook;
    private LegendChatHook lcHook;

    private LocsFile locs;

    public String prefix = "§6[HyzardEssentials §ev" + getDescription().getVersion() + "§6] ";

    @Override
    public void onEnable() {
        instance = this;
        locations = new Locations();
        manager = new Manager();

        locs = new LocsFile(this, "locs.yml");
        if (getServer().getPluginManager().getPlugin("Vault") != null) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aVault encontrado! (Hooked).");
            startEconomy();
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cVault não encontrado, desabilitando plugin.");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aPlaceholderAPI encontrado! Carregando placeholders.");
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cPlaceholderAPI não encontrado, desabilitando plugin.");
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            return;
        }
        if(getServer().getPluginManager().getPlugin("nChat") != null || getServer().getPluginManager().getPlugin("LegendChat") != null) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aChatAPI carregada (As tags funcionarão caso ativadas na 'config.yml').");
            loadLegendchat();
        } else {
            Bukkit.getConsoleSender().sendMessage(prefix + "§cChatAPI carregada (TAGS NÃO FUNCIONARÃO).");
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
        new DelWarp(this);
        new Derreter(this);
        new Desencantar(this);
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
        new me.zbrunooow.hyzardessentials.comandos.Home(this);
        new HomeDeletar(this);
        new HomeSet(this);
        new HyzardEssentials(this);
        new Info(this);
        new Invsee(this);
        new Kill(this);
        new Lixo(this);
        new me.zbrunooow.hyzardessentials.comandos.Kit(this);
        new KitCriar(this);
        new KitDeletar(this);
        new Lore(this);
        new Luz(this);
        new Online(this);
        new Perfil(this);
        new Ping(this);
        new Playtime(this);
        new Potion(this);
        new Renomear(this);
        new SetSpawn(this);
        new SetWarp(this);
        new Spawn(this);
        new Speed(this);
        new SpeedFly(this);
        new Sudo(this);
        new Tpa(this);
        new TpAccept(this);
        new TpDeny(this);
        new TpCancel(this);
        new Teleport(this);
        new TpAll(this);
        new TpHere(this);
        new Vanish(this);
        new Viciado(this);
        new Warp(this);

        List<Listener> eventos = new ArrayList<>();
        eventos.add(new BackListener());
        eventos.add(new DesativarChuva());
        eventos.add(new EchestListener());
        eventos.add(new FlyListener());
        eventos.add(new GodListener());
        eventos.add(new HomesListener());
        eventos.add(new InvseeListener());
        eventos.add(new PlaytimeListener());
        eventos.add(new PerfilListener());
        eventos.add(new RemoveMetaDatas());
        eventos.forEach(evento -> Bukkit.getPluginManager().registerEvents(evento, this));

        reloadPlugin();
        getManager().updateTop();
        getManager().getTopOffline();

        getManager().loadAllJogadores();

        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(prefix + "§fPlugin §ahabilitado §fcom sucesso.");
        Bukkit.getConsoleSender().sendMessage(" ");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage(" ");
        Bukkit.getConsoleSender().sendMessage(prefix + "§fPlugin §cdesabilitado§f.");
        Bukkit.getConsoleSender().sendMessage(" ");

        for(Jogador j : Manager.get().getJogadores()) {
            j.setTempoTotal((int) (TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-j.getTime()) + j.getTempoTotal()));
            j.save();
        }

    }

    public void reloadPlugin() {
        reloadConfig();

        msgs = new Mensagens();
        config = new Config();
        api = new API();
        
        new SempreDiaNoite();

        manager.getCommands().forEach(cmd -> {
            cmd.reloadConfig();
            cmd.getMensagens().loadMensagens();
            cmd.loadConfig();
        });

        manager.getWarps().clear();
        File pastawarps = new File(getDataFolder() + "/warps");
        if (!pastawarps.exists()) pastawarps.mkdir();
        for(File file : pastawarps.listFiles()) {
            if (!file.getName().endsWith(".json")) continue;
            String nome = file.getName().replace(".json", new String());
            new me.zbrunooow.hyzardessentials.objetos.Warp(nome, null);
        }

        manager.getKits().clear();
        File pastakits = new File(getDataFolder() + "/kits");
        if (!pastakits.exists()) pastakits.mkdir();
        for(File file : pastakits.listFiles()) {
            if (!file.getName().endsWith(".yml")) continue;
            String nome = file.getName().replace(".yml", new String());
            new Kit(nome, null);
        }

        if(Config.get().getAtivartagviciado()) {
            Bukkit.getConsoleSender().sendMessage(prefix + "§aTag §2'[viciado]' §afoi ativada!");
            Bukkit.getPluginManager().registerEvents(new LegendChatTagEvent(), this);
        }

    }

    public static Core getInstance() {
        return instance;
    }

    public Locations getLocations() {return locations;}
    public Mensagens getMsgs() {
        return msgs;
    }
    public Config getConfiguration() {
        return config;
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

    private void startEconomy() {
        this.vaultHook = new VaultHook();
        this.vaultHook.setupEconomy();
    }

    private void loadLegendchat() {
        this.lcHook = new LegendChatHook();
        this.lcHook.hookLegendChat();
    }

}
