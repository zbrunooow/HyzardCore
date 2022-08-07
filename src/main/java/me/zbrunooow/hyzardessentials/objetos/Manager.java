package me.zbrunooow.hyzardessentials.objetos;

import me.zbrunooow.hyzardessentials.Core;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Manager {

    private List<HyzardCommand> commands = new ArrayList<>();

    private List<Warp> warps = new ArrayList<>();

    public List<HyzardCommand> getCommands() {
        return commands;
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

    public List<Warp> getWarps() {
        return warps;
    }

}
