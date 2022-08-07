package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Save;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Warp {

    private String nome;
    private Location loc;
    private File file;
    private int tempoTeleporte = 3;

    public Warp(String nome, Location loc) {
        this.nome = nome;
        this.loc = loc;
        file = new File(Core.getInstance().getDataFolder() + "/warps/"+nome+".json");
        if (!file.exists()){
            try {
                file.createNewFile();
                save();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        load();
        Manager.get().getWarps().add(this);
    }

    public void save() {
        List<Object> lista = new ArrayList<>();
        lista.add(API.get().serialize(loc));
        lista.add(String.valueOf(tempoTeleporte));
        new Save(file,lista);
    }

    public void load(){
        List<Object> lista = Save.load(file);
        if (lista == null) return;
        if (lista.isEmpty()) return;
        Location loc = API.get().unserialize((String)lista.get(0));
        int time = Integer.valueOf((String)lista.get(1));
        this.loc = loc;
        this.tempoTeleporte = time;

    }

    public void teleport(Player p) {
        if (loc.getChunk().isLoaded()) loc.getChunk().load();
        p.teleport(loc);
    }

    public void delete(){
        Manager.get().getWarps().remove(this);
        file.delete();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }

    public int getTempoTeleporte() {
        return tempoTeleporte;
    }

    public void setTempoTeleporte(int tempoTeleporte) {
        this.tempoTeleporte = tempoTeleporte;
    }
}
