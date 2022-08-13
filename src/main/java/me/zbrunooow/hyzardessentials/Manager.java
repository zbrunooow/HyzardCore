package me.zbrunooow.hyzardessentials;

import me.zbrunooow.hyzardessentials.objetos.*;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Save;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class Manager {

    private List<HyzardCommand> commands = new ArrayList<>();

    private List<Warp> warps = new ArrayList<>();

    private List<Kit> kits= new ArrayList<>();

    private int playerstop;

    public List<HyzardCommand> getCommands() {
        return commands;
    }

    public List<Jogador> jogadores;

    public List<String> topCompleto = new ArrayList<>();

    public Manager() {
        jogadores = new ArrayList<>();
    }

    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public Jogador getJogador(Player player) {
        for(Jogador j : jogadores) {
            if(j.getNome().equals(player.getName())) {
                return j;
            }
        }
        return null;
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

                System.out.print(carregando);
                if(valorlista.equals(valorplayer)) {
                    topCompleto.add(getMensagens("playtime").getMsg("Formatacao_Top").replace("{pos}", String.valueOf(carregando+1)).replace("{player}", file.getName().replace(".json", "")).replace("{tempo}", API.get().formatTime(valorlista)));
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

}
