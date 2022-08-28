package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Item;
import me.zbrunooow.hyzardessentials.utils.SkullCreator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GiveCaixaKill {

    public GiveCaixaKill(Core core) {
        HyzardCommand command = new HyzardCommand(core, "givecaixakill", "givar uma caixa de kill", "", new ArrayList<>());

        Item caixa = new Item(SkullCreator.itemFromUrl("http://textures.minecraft.net/texture/d8c1e1c62dc695eb90fa192da6aca49ab4f9dffb6adb5d2629ebfc9b2788fa2").clone());
        caixa.setDisplayName("§cCaixa 'mensagens de morte'!");
        List<String> lista = new ArrayList<>();
        lista.add("");
        lista.add("§7Possíveis mensagens:");
        List<String> l = command.getConfig().getStringList("Config.Mensagens_Morte");
        for(String str : l) {
            lista.add(" §7- " + str.replace("&", "§").replace("{player}", "killer").replace("{morreu}", "victim"));
        }
        lista.add("");
        caixa.setLore(lista);

        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!s.hasPermission("hyzardcore.givecaixakill") && !s.hasPermission("hyzardcore.*")) {
                    s.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 2) {
                    s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(!API.get().isInt(args[0])) {
                    s.sendMessage(command.getMensagens().getMsg("Quantidade_Invalida"));
                    return false;
                }

                int i = Integer.parseInt(args[0]);
                if(i > 64) {
                    s.sendMessage(command.getMensagens().getMsg("Quantidade_Invalida"));
                    return false;
                }
                caixa.setAmount(i);

                if(args[0].equals("*") || args[1].equals("@a")) {
                    for(Player p : Bukkit.getOnlinePlayers()) {
                        p.getInventory().addItem(caixa.build().clone());
                        p.sendMessage(command.getMensagens().getMsg("Givou_Outro").replace("{quantidade}", String.valueOf(i)));
                    }
                    s.sendMessage(command.getMensagens().getMsg("Givou_All").replace("{quantidade}", String.valueOf(i)));
                    return true;
                }

                Player p2 = Bukkit.getPlayerExact(args[1]);
                if(p2 == null) {
                    s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                    return false;
                }

                s.sendMessage(command.getMensagens().getMsg("Givou").replace("{player}", p2.getName()).replace("{quantidade}", String.valueOf(i)));
                p2.sendMessage(command.getMensagens().getMsg("Givou_Outro").replace("{quantidade}", String.valueOf(i)));
                p2.getInventory().addItem(caixa.build().clone());

                return false;
            }
        });

        command.createConfig(() -> {
            ConfigurationSection config = command.getConfigurationSection();
            List<String> list = new ArrayList<>();
            list.add("&4{player} &cmatou &4{morreu}&c!");
            list.add("&4{morreu} &cmorreu para &4{player}&c!");

            config.set("Mensagens_Morte", list);

            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/givecaixakill [quantia] [player])");
            config.set("Quantidade_Invalida", "&cInsira uma quantidade de 1 a 64!");
            config.set("Jogador_Offline", "&cJogador offline.");

            config.set("Givou", "&aVocê givou &2{quantidade} &acaixas para &2{player}&a.");
            config.set("Givou_All", "&aVocê givou &2{quantidade} &acaixas para &2todos&a.");
            config.set("Givou_Outro", "&aVocê recebeu &2{quantidade} &acaixas tipo mensagens de morte.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
