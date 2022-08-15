package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Save;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Jogador {

    private String nome;
    private Long time = System.currentTimeMillis();
    private int tempoTotal;
    private Inventory enderchest;
    public List<Home> homes;
    private File file;

    public Jogador(Player player) {
        this.nome = player.getName();
        this.homes = new ArrayList<>();
        this.enderchest = Bukkit.createInventory(player, 9*4, "Baú do Fim");
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

    public void save(Inventory inv) {
        List<Object> lista = new ArrayList<>();
        lista.add(String.valueOf(tempoTotal));
        List<String> homesformato = new ArrayList<>();
        for(Home h : homes) {
            String home = h.getNome() + "///" + API.get().serialize(h.getLoc());
            homesformato.add(home);
        }
        lista.add(homesformato);
        lista.add(API.get().serializeItems(inv.getContents()));
        new Save(file,lista);
    }

    public void save(Integer i) {
        List<Object> lista = new ArrayList<>();
        lista.add(String.valueOf(i));
        List<String> homesformato = new ArrayList<>();
        for(Home h : homes) {
            String home = h.getNome() + "///" + API.get().serialize(h.getLoc());
            homesformato.add(home);
        }
        lista.add(homesformato);
        new Save(file,lista);
    }

    public void save() {
        List<Object> lista = new ArrayList<>();
        lista.add(String.valueOf(tempoTotal));
        List<String> homesformato = new ArrayList<>();
        for(Home h : homes) {
            String home = h.getNome() + "///" + API.get().serialize(h.getLoc());
            homesformato.add(home);
        }
        lista.add(homesformato);
        lista.add(API.get().serializeItems(enderchest.getContents()));
        new Save(file,lista);
    }

    public void load(){
        List<Object> lista = Save.load(file);
        if (lista == null) return;
        if (lista.isEmpty()) return;
        int tempoTotal = Integer.valueOf((String) lista.get(0));
        List<Object> objects = (List<Object>) lista.get(1);
        for(Object s : objects) {
            String[] linha = ((String) s).split("///");
            String nome = linha[0];
            Location loc = API.get().unserialize(linha[1]);
            getHomes().add(new Home(nome, loc));
        }
        this.tempoTotal = tempoTotal;
        this.enderchest.setContents(API.get().unserializeItems((String) lista.get(2)));
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

    public List<Home> getHomes() {
        return homes;
    }

    public Home getHome(String nome) {
        for(Home home : getHomes()) {
            if(nome.equalsIgnoreCase(home.getNome())) {
                return home;
            }
        }
        return null;
    }

    public Inventory getEnderchest() {
        return enderchest;
    }

    public void setEnderchest(Inventory enderchest) {
        this.enderchest = enderchest;
    }
}
