package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class Renomear {

    public Renomear(Core core) {
        HyzardCommand command = new HyzardCommand(core, "renomear", "renomeie seus itens", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.renomear") || p.hasPermission("hyzardcore.*")) {
                    if (args.length >= 1) {
                        if(p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                            StringBuilder renomeando = new StringBuilder();
                            for (int i = 0; i < args.length; i++) {
                                renomeando.append(args[i]).append(" ");
                            }
                            String argumentos = renomeando.toString().trim();
                            ItemStack item = p.getItemInHand();
                            ItemMeta meta = item.getItemMeta();
                            meta.setDisplayName(argumentos.replace('&', '§'));
                            item.setItemMeta(meta);
                            p.sendMessage(command.getMensagens().getMsg("Renomeou").replace("{nome-do-item}", argumentos.replace('&', '§') + "§a!"));
                            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                            p.updateInventory();
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Sem_Item"));
                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }
                return false;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            config.set("Gastar_Money", false);
            config.set("Money", 10000);
            config.set("Gastar_XP", false);
            config.set("XP", 5);

            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/renomear [nome])");
            config.set("Sem_Item", "&cVocê não tem nenhum item em sua mão");

            config.set("Renomeou", "&aVocê renomeou seu item para &f{nome-do-item}");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
