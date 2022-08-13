package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
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
import java.util.List;

public class Compactar {

    public Compactar(Core core) {
        HyzardCommand command = new HyzardCommand(core, "compactar", "compacte seus minérios em blocos", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;

                if(!s.hasPermission("hyzardcore.compactar") && !s.hasPermission("hyzardcore.*")) {
                    s.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 0) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                Player p = (Player) s;
                PlayerInventory inv = p.getInventory();

                if(!temItensCompactar(inv)) {
                    p.sendMessage(command.getMensagens().getMsg("Sem_Itens"));
                    return false;
                }

                if(compactarItens(inv, p) == 0) {
                    p.sendMessage(command.getMensagens().getMsg("Sem_Itens"));
                    return false;
                }

                p.sendMessage(command.getMensagens().getMsg("Compactou"));
                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Compactou_ActionBar")));
                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 8);

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/compactar)!");
            config.set("Sem_Itens", "&cVocê não tem itens para compactar!");
            config.set("Compactou", "&aVocê compactou seus itens com sucesso!");
            config.set("Compactou_ActionBar", "&aVocê compactou seus itens com sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

    private Boolean daParaCompactar(Material item) {
        if (item == Material.IRON_INGOT ||
                item == Material.GOLD_INGOT ||
                item == Material.DIAMOND ||
                item == Material.INK_SACK ||
                item == Material.EMERALD ||
                item == Material.REDSTONE ||
                item == Material.COAL) {
            return true;
        }
        return false;
    }

    private Boolean temItensCompactar(PlayerInventory inv) {
        if (inv.contains(Material.IRON_INGOT) ||
                inv.contains(Material.GOLD_INGOT) ||
                inv.contains(Material.DIAMOND) ||
                inv.contains(Material.INK_SACK) ||
                inv.contains(Material.EMERALD) ||
                inv.contains(Material.REDSTONE) ||
                inv.contains(Material.COAL)) {
            return true;
        }
        return false;
    }

    private ItemStack getCompactado(Material material, int quant) {
        if(material == Material.IRON_INGOT) {
            return new ItemStack(Material.IRON_BLOCK, quant);
        }
        if(material == Material.GOLD_INGOT) {
            return new ItemStack(Material.GOLD_BLOCK, quant);
        }
        if(material == Material.DIAMOND) {
            return new ItemStack(Material.DIAMOND_BLOCK, quant);
        }
        if(material == Material.INK_SACK) {
            return new ItemStack(Material.LAPIS_BLOCK, quant);
        }
        if(material == Material.EMERALD) {
            return new ItemStack(Material.EMERALD_BLOCK, quant);
        }
        if(material == Material.REDSTONE) {
            return new ItemStack(Material.REDSTONE_BLOCK, quant);
        }
        if(material == Material.COAL) {
            return new ItemStack(Material.COAL_BLOCK, quant);
        }
        return null;
    }

    private int compactarItens(PlayerInventory inv, Player p) {
        int i = 0;
        List<ItemStack> restoDevolver = new ArrayList<>();
        for (ItemStack item : inv.getContents()) {
            if (item == null || item.getType().equals(Material.AIR)) continue;
            int quantidade = item.getAmount();
            if (quantidade < 9) continue;
            if (!daParaCompactar(item.getType())) continue;

            int resto = quantidade % 9;
            int trueAmount = quantidade-resto;

            try {
                item.setAmount(trueAmount);

                ItemStack compactado = getCompactado(item.getType(), trueAmount/9);

                inv.removeItem(item);
                inv.addItem(compactado);

                if (resto > 0) {
                    restoDevolver.add(new ItemStack(item.getType(), resto, item.getDurability()));
                }

                i++;
            } catch (Exception ignored) {

            }
        }

        for(ItemStack restoitem : restoDevolver) {
            for (ItemStack resto : inv.addItem(restoitem).values()) {
                p.getWorld().dropItem(p.getLocation(), resto);
            }
        }

        return i;
    }

}
