package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.hooks.VaultHook;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Item;
import net.milkbowl.vault.Vault;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Desencantar {

    public Desencantar(Core core) {
        HyzardCommand command = new HyzardCommand(core, "desencantar", "use para desencantar seus itens", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.desencantar") && !p.hasPermission("hyzardcore.*")){
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length > 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                Item item = new Item(p.getItemInHand());

                if(item.getItem() == null || item.getItem().getType() == Material.AIR) {
                    p.sendMessage(command.getMensagens().getMsg("Sem_Item"));
                    return false;
                }

                int ench = p.getItemInHand().getEnchantments().size();
                double valor = Double.parseDouble(command.getFromConfig("Valor_Por_Enchant"))*ench;
                if(args.length == 0) {
                    if(!item.hasEnchants()) {
                        p.sendMessage(command.getMensagens().getMsg("Sem_Encantamentos"));
                        return false;
                    }
                    p.sendMessage(command.getMensagens().getMsg("Confirmar").replace("{valor}", String.valueOf(valor)));
                    return false;
                }

                if(!args[0].equalsIgnoreCase("confirmar")) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(!item.hasEnchants()) {
                    p.sendMessage(command.getMensagens().getMsg("Sem_Encantamentos"));
                    return false;
                }

                if(!VaultHook.eco.has(p, valor)) {
                    p.sendMessage(command.getMensagens().getMsg("Sem_Dinheiro").replace("{valor}", String.valueOf(valor)));
                    return false;
                }

                VaultHook.eco.withdrawPlayer(p, valor);
                item.removeAllEnchants();

                p.sendMessage(command.getMensagens().getMsg("Desencantou").replace("{valor}", String.valueOf(valor)));
                return false;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            config.set("Valor_Por_Enchant", 2000);
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/desencantar)!");
            config.set("Sem_Encantamentos", "&cEsse item não possui encantamentos!");
            config.set("Sem_Item", "&cColoque um item na sua mão!");
            config.set("Sem_Dinheiro", "&cVocê não tem dinheiro suficiente! &c(&4{valor}&c$)");
            config.set("Confirmar", "&eCaso realmente queira desencantar esse item pelo preço &6{valor}$&e, use &7/desencantar confirmar!");

            config.set("Desencantou", "&aVocê desencantou seu item por &2{valor}$");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
