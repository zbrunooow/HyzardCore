package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class TpAccept {

    public TpAccept(Core core) {
        HyzardCommand command = new HyzardCommand(core, "tpaccept", "aceite o teleport do amiguinho", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.tpaccept") && !p.hasPermission("hyzardcore.*")){
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length != 1) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    return false;
                }

                Player p2 = Bukkit.getPlayerExact(args[0]);
                if(p2 == null) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                    return false;
                }

                if(p2 == p) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                    return false;
                }

                if(!p2.hasMetadata("tpa") || p2.getMetadata("tpa").get(0).value() != p.getName()) {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Nao_Recebeu_Tpa").replace("{player}", p2.getName())));
                    return false;
                }

                tpAceito(command, core, p2);

                return true;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/tpaccept [player])");
            config.set("Nao_Recebeu_Tpa", "&cVocê não recebeu um pedido de teleporte de &4{player}&c!");
            config.set("Jogador_Offline", "&cJogador offline.");

            config.set("Teleportado", "&aVocê foi teleportado para &2{player}&a!");
            config.set("Teleportando", "&aTeleporte aceito, você será teleportado em &2{segundos} &asegundos!");
            config.set("Teleportando_ActionBar", "&aVocê será teleportado em &2{segundos}&a!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

    private void tpAceito(HyzardCommand cmd, Core core, Player p) {
        if (p.hasMetadata("tpa")) {
            long aceitou = System.currentTimeMillis();
            Player p2 = Bukkit.getPlayerExact(String.valueOf(p.getMetadata("tpa").get(0).value()));
            Location loc = p2.getLocation();
            if(!p.hasPermission("hyzardcore.nodelay") || !p.hasPermission("hyzardcore.*")) {
                p.sendMessage(cmd.getMensagens().getMsg("Teleportando").replace("{segundos}", String.valueOf(3)));
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    long atual = System.currentTimeMillis() - aceitou;
                    int seconds = p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? 0 : 3 - (int) TimeUnit.MILLISECONDS.toSeconds(atual);
                    int delay = p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? 0 : 3;
                    if (TimeUnit.MILLISECONDS.toSeconds(atual) >= delay) {
                        this.cancel();
                        p.removeMetadata("tpa", core);
                        Bukkit.getScheduler().runTask(core, () -> {
                            p.teleport(loc);
                            API.get().sendActionBarMessage(p, cmd.getMensagens().getMsg("Teleportado").replace("{player}", p2.getName()));
                            p.playSound(loc, Sound.ENDERMAN_TELEPORT, 1, 4);
                        });
                    } else {
                        API.get().sendActionBarMessage(p, cmd.getMensagens().getMsg("Teleportando_ActionBar").replace("{segundos}", API.get().formatTime(seconds)));
                    }
                }
            }.runTaskTimerAsynchronously(core, 0, 20);
        }
    }

}
