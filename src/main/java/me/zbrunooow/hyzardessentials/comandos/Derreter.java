package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;

public class Derreter {

    public Derreter(Core core) {
        HyzardCommand command = new HyzardCommand(core, "derreter", "derreta os itens que estão no seu inv", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.derreter") && !s.hasPermission("hyzardcore.*")) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, Mensagens.get().getSemPerm()));
                    return false;
                }

                if(args.length != 0) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    return false;
                }

                PlayerInventory inv = p.getInventory();

                if(!temItensDerreter(inv)) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Sem_Itens")));
                    return false;
                }

                int derretidos = derreterItens(inv);
                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Derreteu").replace("{derreteu}", String.valueOf(derretidos))));
                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Derreteu_ActionBar").replace("{derreteu}", String.valueOf(derretidos))));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 8);

                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/derreter)!");
            config.set("Sem_Itens", "&cVocê não tem itens para derreter!");
            config.set("Derreteu", "&aVocê derreteu &2{derreteu} &aitens com sucesso!");
            config.set("Derreteu_ActionBar", "&aVocê derreteu &2{derreteu} &aitens com sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

    private Boolean daParaDerreter(Material item) {
        if (item == Material.IRON_ORE ||
                item == Material.GOLD_ORE ||
                item == Material.DIAMOND_ORE ||
                item == Material.LAPIS_ORE ||
                item == Material.EMERALD_ORE ||
                item == Material.REDSTONE_ORE ||
                item == Material.COAL_ORE) {
            return true;
        }
        return false;
    }

    private Boolean temItensDerreter(PlayerInventory inv) {
        if (inv.contains(Material.IRON_ORE) ||
                inv.contains(Material.GOLD_ORE) ||
                inv.contains(Material.DIAMOND_ORE) ||
                inv.contains(Material.LAPIS_ORE) ||
                inv.contains(Material.EMERALD_ORE) ||
                inv.contains(Material.REDSTONE_ORE) ||
                inv.contains(Material.COAL_ORE)) {
            return true;
        }
        return false;
    }

    private ItemStack getDerretido(Material material, int quant) {
        if(material == Material.IRON_ORE) {
            return new ItemStack(Material.IRON_INGOT, quant);
        }
        if(material == Material.GOLD_ORE) {
            return new ItemStack(Material.GOLD_INGOT, quant);
        }
        if(material == Material.DIAMOND_ORE) {
            return new ItemStack(Material.DIAMOND, quant);
        }
        if(material == Material.LAPIS_ORE) {
            return new ItemStack(Material.INK_SACK, quant, (short) 4);
        }
        if(material == Material.EMERALD_ORE) {
            return new ItemStack(Material.EMERALD, quant);
        }
        if(material == Material.REDSTONE_ORE) {
            return new ItemStack(Material.REDSTONE, quant);
        }
        if(material == Material.COAL_ORE) {
            return new ItemStack(Material.COAL, quant);
        }
        return null;
    }

    private int derreterItens(PlayerInventory inv) {
        int derretidos = 0;

        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType().equals(Material.AIR)) continue;
            if(!daParaDerreter(item.getType())) continue;
            try {
                int quantidade = item.getAmount();

                ItemStack derretido = getDerretido(item.getType(), quantidade);

                inv.removeItem(item);
                inv.addItem(derretido);
                derretidos += quantidade;

            } catch (Exception ignored) { }

        }
        return derretidos;
    }

}
