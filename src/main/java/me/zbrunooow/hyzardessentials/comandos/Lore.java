package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.Item;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Lore {

    public Lore(Core core) {
        HyzardCommand command = new HyzardCommand(core, "lore", "coloque um lore em seu item", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.lore") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
                    p.sendMessage(command.getMensagens().getMsg("Mao_Vazia"));
                    return false;
                }

                String[] lore = args[0].replace("&", "§").split(";");
                Item item = new Item(p.getItemInHand());
                item.setLore(lore);
                p.updateInventory();
                p.sendMessage(command.getMensagens().getMsg("Aplicado"));

                return true;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/lore [linha;linha])!");
            config.set("Mao_Vazia", "&cNenhum item na sua mão!");

            config.set("Aplicado", "&aVocê aplicou um novo lore em seu item!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
