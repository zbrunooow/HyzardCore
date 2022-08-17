package me.zbrunooow.hyzardessentials;

import me.zbrunooow.hyzardessentials.objetos.*;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Item;
import me.zbrunooow.hyzardessentials.utils.Save;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class Manager {

    private List<HyzardCommand> commands = new ArrayList<>();

    private List<Warp> warps = new ArrayList<>();

    private List<Kit> kits = new ArrayList<>();

    private int playerstop;

    private Inventory homesinv = Bukkit.createInventory(null, 54, "§6§lSuas homes:");

    public List<HyzardCommand> getCommands() {
        return commands;
    }

    public List<Jogador> jogadores;

    public List<String> topCompleto = new ArrayList<>();

    private String viciado;
    private String viciadoTempo;

    public Manager() {
        jogadores = new ArrayList<>();
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }
    {
        Item vidrovermelho = new Item(Material.STAINED_GLASS_PANE, (byte) 14);
        vidrovermelho.setDisplayName("§cSair");
        Item vidropreto = new Item(Material.STAINED_GLASS_PANE, (byte) 15);
        vidropreto.setDisplayName("§0§m-<>-");
        this.homesinv.setItem(45, vidrovermelho.build());
        int i = 46;
        while(i <= 53) {
            this.homesinv.setItem(i, vidropreto.build());
            i++;
        }
    }
    public Inventory getHomesinv() {
        return this.homesinv;
    }

    public Jogador getJogador(Player player) {
        for(Jogador j : jogadores) {
            if(j.getNome().equals(player.getName())) {
                return j;
            }
        }
        return null;
    }

    public void addHome(Player player, String nome) {
        Jogador j = getJogador(player);

        j.getHomes().add(new Home(nome, player.getLocation()));
        j.save();
    }

    public void removeHome(Player player, Home home) {
        Jogador j = getJogador(player);

        j.getHomes().remove(home);
        j.save();
    }

    public List<Home> getHomes(Player player) {
        return getJogador(player).getHomes();
    }

    public MsgCommand getMensagens(String comando) {
        for(HyzardCommand cmd : getCommands()) {
            if(cmd.getName().contains(comando)) {
                return cmd.getMensagens();
            }
        }
        return null;
    }

    public HyzardCommand getCommand(String comando) {
        for(HyzardCommand cmd : getCommands()) {
            if(cmd.getName().contains(comando)) {
                return cmd;
            }
        }
        return null;
    }

    public void loadAllJogadores() {
        getJogadores().clear();
        for(Player p : Bukkit.getOnlinePlayers()) {
            new Jogador(p);
        }
    }

    public void removeJogador(Jogador j) {
        jogadores.remove(j);
    }

    public List<String> getTopOffline() {
        this.playerstop = Integer.parseInt(getCommand("playtime").getFromConfig("Players_Top"));
        topCompleto.clear();
        List<Integer> top = new ArrayList<>();
        File folder = new File(Core.getInstance().getDataFolder(), "/jogadores/");
        for (File file : folder.listFiles()) {
            List<Object> lista = Save.load(file);
            if (lista == null) return null;
            if (lista.isEmpty()) return null;
            int tempoTotal = Integer.valueOf((String) lista.get(0));

            top.add(tempoTotal);
        }

        Collections.sort(top);
        Collections.reverse(top);

        if(top.size() < playerstop) {
            this.playerstop = top.size();
        }

        int carregando = 0;
        while(carregando < this.playerstop) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                List<Object> lista = Save.load(file);
                if (lista == null) return null;
                if (lista.isEmpty()) return null;

                Integer valorplayer = Integer.valueOf(String.valueOf(lista.get(0)));
                Integer valorlista = top.get(carregando);

                if(valorlista.equals(valorplayer)) {
                    if(carregando == 0) {
                        setViciado(file.getName().replace(".json", ""));
                        setViciadoTempo(API.get().formatTimeSecond(valorlista));
                    }

                    topCompleto.add(getMensagens("playtime").getMsg("Formatacao_Top").replace("{pos}", String.valueOf(carregando+1)).replace("{player}", file.getName().replace(".json", "")).replace("{tempo}", API.get().formatTimeSecond(valorlista)));
                    carregando++;
                    if(carregando == playerstop) break;
                }
            }
        }

        return this.topCompleto;
    }

    public void updateTop() {
        new BukkitRunnable() {
            public void run() {
                for(Jogador j : Manager.get().getJogadores()) {
                    saveTempoTotal(j);
                    getTopOffline();
                }
            }
        }.runTaskTimerAsynchronously(Core.getInstance(), 0, 18000);
    }

    public void setCommands(List<HyzardCommand> commands) {
        this.commands = commands;
    }

    public static Manager get(){
        return Core.getInstance().getManager();
    }

    public Warp getWarp(String nome) {
        Iterator<Warp> it = warps.iterator();
        while(it.hasNext()) {
            Warp warp = it.next();
            if (warp.getNome().equalsIgnoreCase(nome)) return warp;
        }
        return null;
    }

    public Kit getKit(String nome) {
        Iterator<Kit> it = kits.iterator();
        while(it.hasNext()) {
            Kit kit = it.next();
            if (kit.getNome().equalsIgnoreCase(nome)) return kit;
        }
        return null;
    }

    public void saveTempoTotal(Jogador j) {
        j.save(j.getTempoOnline());
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public List<Kit> getKits() {
        return kits;
    }

    public int getPlayerstop() {
        return playerstop;
    }

    public void openHomesMenu(Player player) {
        Jogador j = getJogador(player);
        Item item = new Item(Material.SIGN);
        int clear = 0;
        while(clear <= j.getHomes().size()) {
            getHomesinv().setItem(clear, null);
            clear++;
        }
        int slot = 0;
        for(Home home : j.getHomes()) {
            item.setDisplayName(ChatColor.YELLOW + home.getNome());
            item.setLore("", " §7- Localização: ", "  §eX: §7" + API.get().formatValue(home.getLoc().getX()), "  §eY: §7" + API.get().formatValue(home.getLoc().getY()), "  §eZ: §7" + API.get().formatValue(home.getLoc().getZ()), "");
            getHomesinv().setItem(slot, item.build().clone());
            slot++;
        }
        player.openInventory(getHomesinv());
    }

    public String getViciado() {
        return viciado;
    }

    public void setViciado(String viciado) {
        this.viciado = viciado;
    }

    public String getViciadoTempo() {
        return viciadoTempo;
    }

    public void setViciadoTempo(String viciadoTempo) {
        this.viciadoTempo = viciadoTempo;
    }
}
