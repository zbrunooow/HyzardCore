package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Kit;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class KitCriar {

    public KitCriar(Core core) {
        HyzardCommand command = new HyzardCommand(core, "criarkit", "crie um kit", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.criarkit") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length > 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length == 0) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(API.get().getFreeSlots(p) == 36) {
                    p.sendMessage(command.getMensagens().getMsg("Sem_Itens"));
                    return false;
                }

                if(Manager.get().getKit(args[0]) != null) {
                    p.sendMessage(command.getMensagens().getMsg("Kit_Existente").replace("{kit}", args[0]));
                    p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 1);
                    return false;
                }

                new Kit(args[0], p.getInventory());
                p.getInventory().setContents(new ItemStack[36]);
                p.sendMessage(command.getMensagens().getMsg("Kit_Criado").replace("{kit}", args[0]));
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);

                return true;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse /criarkit {kit}");
            config.set("Sem_Itens", "&cVocê não tem nenhum item em seu inventário para criar o kit.");
            config.set("Kit_Existente", "&cO kit &4{kit} &cjá existe!");
            config.set("Kit_Criado", "&aVocê criou o kit &2{kit}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
