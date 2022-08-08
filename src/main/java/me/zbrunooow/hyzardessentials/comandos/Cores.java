package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Cores {

    public Cores(Core core) {
        HyzardCommand command = new HyzardCommand(core, "cores", "veja as cores!", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;

                Player p = (Player) s;

                p.sendMessage("");
                p.sendMessage("§fFormatação de cores");
                p.sendMessage("");
                p.sendMessage("§0&0 §f/ §1&1 §f\\ §2&2");
                p.sendMessage("§3&3 §f/ §4&4 §f\\ §5&5");
                p.sendMessage("§6&6 §f/ §7&7 §f\\ §8&8");
                p.sendMessage("§9&9 §f/ §a&a §f\\ §b&b");
                p.sendMessage("§c&c §f/ §d&d §f\\ §e&e");
                p.sendMessage("");
                p.sendMessage("§7&l§lHyzard");
                p.sendMessage("§7&m§mHyzard");
                p.sendMessage("§7&n§nHyzard");
                p.sendMessage("§7&o§oHyzard");
                p.sendMessage("&r§rHyzard");
                p.sendMessage("§7&k§kHyzard");
                p.sendMessage("");

                return false;
            }
        });


    }

}
