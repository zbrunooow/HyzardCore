package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Manager;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Warp {

    public Warp(Core core) {
        HyzardCommand command = new HyzardCommand(core, "warp", "va para as warps", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if(args.length == 1) {
                    if (p.hasMetadata("warp_use")){
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Em_Teleporte")));
                        return false;
                    }

                    me.zbrunooow.hyzardessentials.objetos.Warp warp = Manager.get().getWarp(args[0]);
                    if (warp != null) {
                        p.setMetadata("warp_use", new FixedMetadataValue(core, p.hasPermission("*") ? System.currentTimeMillis() : 0));
                        new BukkitRunnable() {
                            int lastSecond = 1;
                            @Override
                            public void run() {
                                if (!p.isOnline()) p.removeMetadata("warp_use", core);
                                if (!p.hasMetadata("warp_use")) {
                                    this.cancel();
                                    return;
                                }
                                long time = System.currentTimeMillis() - (long) p.getMetadata("warp_use").get(0).value();
                                int seconds = warp.getTempoTeleporte() - (int) TimeUnit.MILLISECONDS.toSeconds(time);
                                if (lastSecond != seconds){
                                    lastSecond = seconds;
                                    p.playSound(p.getLocation(), Sound.NOTE_STICKS, 0.5f, 5);
                                }
                                if (TimeUnit.MILLISECONDS.toSeconds(time) >= warp.getTempoTeleporte()) {
                                    this.cancel();
                                    p.removeMetadata("warp_use", core);
                                    Bukkit.getScheduler().runTask(core, ()-> {
                                       warp.teleport(p);
                                       API.get().sendActionBarMessage(p, "");
                                       p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 1, 1);
                                    });
                                } else {
                                    API.get().sendActionBarMessage(p, "&aVoce vai ser teleportado para a warp &f" + warp.getNome() + " &aem &e" + API.get().formatTime(seconds) + "&a!");
                                }
                            }
                        }.runTaskTimerAsynchronously(core, 0,1);
                        return false;
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Nao_Existe").replace("{warp}", args[0])));
                    }
                } else {
                    String warps = new String();
                    for(me.zbrunooow.hyzardessentials.objetos.Warp warp : Manager.get().getWarps()) {
                        if(warps.length() == 0) {
                            warps = warp.getNome();
                        } else {
                            warps = warps + ", " + warp.getNome();
                        }
                    }
                    if(warps.length() == 0) {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Warps").replace("{warps}", "&7Nenhuma warp setada")));
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Warps").replace("{warps}", warps)));
                    }
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/warp [nome])");
            config.set("Em_Teleporte", "&cVocê já está em um teleporte, aguarde...");
            config.set("Nao_Existe", "&cA warp &4{warp} &cnão existe.");

            config.set("Teleportando", "&aVocê será teleportado para a warp &2{warp} &aem &2{tempo} &asegundos.");
            config.set("Teleportado", "&aVocê foi teleportado para a warp &2{warp}&a!");

            config.set("Warps", "&aWarps: &7{warps}&a.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
