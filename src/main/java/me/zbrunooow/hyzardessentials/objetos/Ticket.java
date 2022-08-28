package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Manager;
import org.bukkit.entity.Player;

public class Ticket {

    private Player autor;
    private String duvida;
    private long id;

    public Ticket(Player autor, int id, String duvida) {
        this.autor = autor;
        this.duvida = duvida;
        this.id = id;
    }

    public Player getAutor() {
        return autor;
    }

    public void setAutor(Player autor) {
        this.autor = autor;
    }

    public String getDuvida() {
        return duvida;
    }

    public void setDuvida(String duvida) {
        this.duvida = duvida;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
