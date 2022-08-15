package me.zbrunooow.hyzardessentials.objetos;

import org.bukkit.Location;

public class Home {

    private String nome;
    private Location loc;

    public Home(String nome, Location loc) {
        this.nome = nome;
        this.loc = loc;
        System.out.print("TO SALVANDO UMA NOVA ROME EM");
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
}
