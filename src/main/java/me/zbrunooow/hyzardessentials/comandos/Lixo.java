package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class Lixo {

    public Lixo(Core core) {

        HyzardCommand command = new HyzardCommand(core, "lixo", "abrir uma lixeira", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;

                Player p = (Player) s;
                Inventory lixo = Bukkit.createInventory(p, 36, command.getMensagens().getMsg("Nome_Lixeira"));
                p.openInventory(lixo);
                p.sendMessage(command.getMensagens().getMsg("Abriu_Lixeira"));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);

                return false;
            }

        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Nome_Lixeira", "&4Lixeira");
            config.set("Abriu_Lixeira", "&aVocê abriu a lixeira!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
