package me.zbrunooow.HyzardCore.Objetos;

import me.zbrunooow.HyzardCore.Comandos.HyzardCommand;
import me.zbrunooow.HyzardCore.Core;

import java.util.ArrayList;
import java.util.List;

public class Manager {

    private List<HyzardCommand> commands = new ArrayList<>();

    public List<HyzardCommand> getCommands() {
        return commands;
    }

    public void setCommands(List<HyzardCommand> commands) {
        this.commands = commands;
    }

    public static Manager get(){
        return Core.getInstance().getManager();
    }

}
