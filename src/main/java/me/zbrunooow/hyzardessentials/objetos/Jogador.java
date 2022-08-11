package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Save;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Jogador {

    private String nome;
    private Long time = System.currentTimeMillis();
    private int tempoTotal;
    private File file;

    public Jogador(Player player) {
        nome = player.getName();
        File pasta = new File(Core.getInstance().getDataFolder() + "/jogadores");
        if (!pasta.exists()) pasta.mkdir();
        file = new File(Core.getInstance().getDataFolder() + "/jogadores/"+player.getName() + ".json");

        if (!file.exists()){
            try {
                file.createNewFile();
                save();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        load();
        Manager.get().getJogadores().add(this);
    }

    public void save(Integer i) {
        List<Object> listaq = new ArrayList<>();
        listaq.add(String.valueOf(i));
        new Save(file,listaq);
    }

    public void save() {
        List<Object> lista = new ArrayList<>();
        lista.add(String.valueOf(tempoTotal));
        new Save(file,lista);
    }

    public void load(){
        List<Object> lista = Save.load(file);
        if (lista == null) return;
        if (lista.isEmpty()) return;
        int tempoTotal = Integer.valueOf((String) lista.get(0));
        this.tempoTotal = tempoTotal;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTempoOnline() {
        return (int) (tempoTotal + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-time));
    }

    public String getTempoOnlineFormatado() {
        return API.get().formatTime((int) (tempoTotal + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()-time)));
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getTempoTotal() {
        return tempoTotal;
    }

    public void setTempoTotal(int tempoTotal) {
        this.tempoTotal = tempoTotal;
    }

    public File getFile() {
        return file;
    }
}
