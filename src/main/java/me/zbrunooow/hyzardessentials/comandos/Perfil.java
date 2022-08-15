package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.hooks.VaultHook;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public class Perfil {

    Item teia = new Item(Material.WEB);
    Item xp = new Item(Material.EXP_BOTTLE);
    Item money = new Item(Material.EMERALD);
    Item cash = new Item(Material.YELLOW_FLOWER);
    Item clan = new Item(Material.PAPER);
    Item cargo = new Item(Material.NAME_TAG);
    Item cabeca = new Item(Material.SKULL_ITEM);
    Inventory perfil;
    Player p2;

    public Perfil(Core core) {
        HyzardCommand command = new HyzardCommand(core, "perfil", "Abra o perfil dos jogadores", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if(args.length == 1) {
                    p2 = Bukkit.getPlayerExact(args[0]);
                    if(p2 == null) {
                        p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        return false;
                    }
                } else if (args.length == 0){
                    p2 = p.getPlayer();
                } else {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(p2 == p) {
                    perfil = Bukkit.createInventory(null, 54, "§0Seu perfil:");
                } else {
                    perfil = Bukkit.createInventory(null, 54, "§0Perfil - " + p2.getName() + "§0:");
                }

                cabeca.setOwner(p2.getName());
                cabeca.setDisplayName("§a" + p2.getName());
                cabeca.setLore("", "§7 - Informações:", "");

                xp.setDisplayName("§eExperiência");
                xp.setLore("", "§6" + (p2==p ? "Você" : p2.getName()) + " §etem", "§6" + p2.getLevel() + "§e níveis de XP.", "");

                money.setDisplayName("§2Saldo:");
                money.setLore("", "§aSaldo no banco: §2" + VaultHook.eco.getBalance(p2), "");

                cash.setDisplayName("§eCash:");
                cash.setLore("", "§eSeu cash: §6" + PlaceholderAPI.setPlaceholders(p2, "%nextcash_amount%"), "");

                clan.setDisplayName("§bClan:");
                clan.setLore("", "§3Seu clan: §7" + PlaceholderAPI.setPlaceholders(p2, "%clan_tag%") + " - " + PlaceholderAPI.setPlaceholders(p2, "%clan_nome%"), "§3Membros Online: §7" + PlaceholderAPI.setPlaceholders(p2, "%clan_online%") + "");

                cargo.setDisplayName("§8Rank:");
                cargo.setLore("", "§8Seu rank: " + "§7[prefix]");

                teia.setDisplayName("§7Nada aqui...");

                perfil.setItem(4, cabeca.getItem());

                if (p2.getInventory().getHelmet() != null) {
                    perfil.setItem(16, p2.getInventory().getHelmet());
                } else {
                    perfil.setItem(16, teia.getItem());
                }
                if (p2.getInventory().getChestplate() != null) {
                    perfil.setItem(25, p2.getInventory().getChestplate());
                } else {
                    perfil.setItem(25, teia.getItem());
                }
                if (p2.getInventory().getLeggings() != null) {
                    perfil.setItem(34, p2.getInventory().getLeggings());
                } else {
                    perfil.setItem(34, teia.getItem());
                }
                if (p2.getInventory().getBoots() != null) {
                    perfil.setItem(43, p2.getInventory().getBoots());
                } else {
                    perfil.setItem(43, teia.getItem());
                }
                perfil.setItem(19, money.getItem());
                perfil.setItem(21, cash.getItem());
                perfil.setItem(23, xp.getItem());
                perfil.setItem(38, clan.getItem());
                perfil.setItem(40, cargo.getItem());

                p.openInventory(perfil);
                return false;

            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/perfil [player])");
            config.set("Voce_Mesmo", "&cPara abrir seu perfil, basta utilizar /perfil.");
            config.set("Jogador_Offline", "&cJogador offline!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
