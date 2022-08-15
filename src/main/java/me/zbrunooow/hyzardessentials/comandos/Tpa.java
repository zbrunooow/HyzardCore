package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class Tpa {

    public Tpa(Core core) {
        HyzardCommand command = new HyzardCommand(core, "tpa", "solicite um teleport para um player", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tpa") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    return false;
                }

                if(p.hasMetadata("tpa")) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Enviado_Aguarde")));
                    return false;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                    return false;
                }

                if(p2 == p) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Voce_Mesmo")));
                    return false;
                }

                for(String str : command.getMensagens().getLista("Enviado")) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p2, str.replace("{player}", p2.getName())));
                }
                for(String str : command.getMensagens().getLista("Outro_Recebeu")) {
                    p2.sendMessage(PlaceholderAPI.setPlaceholders(p, str.replace("{player}", p.getName())));
                }

                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Enviou_ActionBar")));
                API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Outro_Recebeu_ActionBar")));

                p.setMetadata("tpa", new FixedMetadataValue(core, p2.getName()));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(p.hasMetadata("tpa")) {
                            p.removeMetadata("tpa", core);
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Expirou").replace("{player}", p2.getName())));
                        }
                    }
                }.runTaskLaterAsynchronously(core, 600);

                return true;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tpa [player])");
            config.set("Voce_Mesmo", "&cVocê não pode enviar um teleporte para sí mesmo");

            config.set("Jogador_Offline", "&cJogador offline.");
            config.set("Enviado_Aguarde", "&cVocê já enviou um pedido de teleporte, aguarde!");

            config.set("Expirou", "&cSeu pedido de teleporte para &4{player} &cexpirou!");

            List<String> enviado = new ArrayList<>();
            enviado.add("&aVocê enviou um pedido de teleporte para &2{player}&a!");
            enviado.add("&aPara cancelar, basta utilizar &2/tpcancel&a!");
            config.set("Enviado", enviado);

            List<String> recebido = new ArrayList<>();
            recebido.add("&aVocê recebeu um pedido de teleporte de &2{player}&a!");
            recebido.add("&aPara aceitar, basta utilizar &2/tpaccept {player}&a!");
            recebido.add("&aPara negar, basta utilizar &2/tpdeny {player}&a!");
            config.set("Outro_Recebeu", recebido);

            config.set("Outro_Recebeu_ActionBar", "&aVocê recebeu uma solicitação de teleporte!");
            config.set("Enviou_ActionBar", "&aVocê enviou uma solicitação de teleporte!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
