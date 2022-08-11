package me.zbrunooow.hyzardessentials;

import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import me.zbrunooow.hyzardessentials.objetos.MsgCommand;
import me.zbrunooow.hyzardessentials.objetos.Warp;
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

    private int playerstop = 5;

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

    public void removeJogador(Jogador j) {
        jogadores.remove(j);
    }

    public List<String> getTopOffline() {
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

        int carregando = 0;
        System.out.print(carregando);
        while(carregando < playerstop) {
            for (File file : Objects.requireNonNull(folder.listFiles())) {
                List<Object> lista = Save.load(file);
                if (lista == null) return null;
                if (lista.isEmpty()) return null;

                Integer valorplayer = Integer.valueOf(String.valueOf(lista.get(0)));
                Integer valorlista = top.get(carregando);

                if(valorlista.equals(valorplayer)) {
                    topCompleto.add(getMensagens("playtime").getMsg("Formatacao_Top").replace("{pos}", String.valueOf(carregando+1)).replace("{player}", file.getName().replace(".json", "")).replace("{tempo}", API.get().formatTime(valorlista)));
                    carregando++;
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

    public void saveTempoTotal(Jogador j) {
        j.save(j.getTempoOnline());
    }

    public List<Warp> getWarps() {
        return warps;
    }

}
