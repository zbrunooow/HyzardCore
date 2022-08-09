package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
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

                if(args.length != 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(p.hasMetadata("tpa")) {
                    p.sendMessage(command.getMensagens().getMsg("Enviado_Aguarde"));
                    return false;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    p.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                    return false;
                }

                p.sendMessage(command.getMensagens().getMsg("Enviado").replace("{player}", p2.getName()));
                p2.sendMessage(command.getMensagens().getMsg("Outro_Recebeu").replace("{player}", p.getName()));
                p.setMetadata("tpa", new FixedMetadataValue(core, p2.getName()));

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if(p.hasMetadata("tpa")) {
                            p.removeMetadata("tpa", core);
                            p.sendMessage(command.getMensagens().getMsg("Expirou").replace("{player}", p2.getName()));
                        }
                        this.cancel();
                    }
                }.runTaskTimerAsynchronously(core, 600, 0);

                return true;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tpa [player])");

            config.set("Jogador_Offline", "&cJogador offline.");
            config.set("Enviado_Aguarde", "&cVocê já enviou um pedido de teleporte, aguarde!");

            config.set("Expirou", "&cSeu pedido de teleporte para &4{player} &cexpirou!");

            List<String> enviado = new ArrayList<>();
            enviado.add("");
            enviado.add("&aVocê enviou um pedido de teleporte para &2{player}&a!");
            enviado.add("&aPara cancelar, basta utilizar &2/tpcancel&a!");
            enviado.add("");
            config.set("Enviado", enviado);

            List<String> recebido = new ArrayList<>();
            recebido.add("");
            recebido.add("&aVocê recebeu um pedido de teleporte de &2{player}&a!");
            recebido.add("&aPara aceitar, basta utilizar &2/tpaccept {player}&a!");
            recebido.add("&aPara negar, basta utilizar &2/tpdeny {player}&a!");
            recebido.add("");
            config.set("Outro_Recebeu", recebido);

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
