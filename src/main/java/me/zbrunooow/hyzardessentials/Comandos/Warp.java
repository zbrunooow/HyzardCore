package me.zbrunooow.hyzardessentials.Comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Warps;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class Warp {

    public Warp(Core core) {
        HyzardCommand command = new HyzardCommand(core, "warp", "va para as warps", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                Location warp;

                if(args.length == 1) {
                    if(core.getWarps().getConfig().getString("Warps." + args[0].toLowerCase() + ".Loc") != null) {
                        warp = API.get().unserialize(core.getWarps().getConfig().getString("Warps." + args[0].toLowerCase() + ".Loc"));
                        if (p.hasPermission("hyzardcore.warp." + args[0].toLowerCase()) || p.hasPermission("hyzardcore.warp.*") || p.hasPermission("hyzardcore.*")) {
                            if (core.getWarpAPI().getDelay(args[0]) > 0) {
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportando").replace("{tempo}", String.valueOf(core.getWarpAPI().getDelay(args[0]))).replace("{warp}", args[0])));
                            }
                            new BukkitRunnable() {
                                @Override
                                public void run() {
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado").replace("{warp}", args[0])));
                                    p.teleport(warp);
                                }
                            }.runTaskLater(core, 20L * core.getWarpAPI().getDelay(args[0]));

                            return true;
                        } else {
                            p.sendMessage(Mensagens.get().getSemPerm());
                        }
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Nao_Existe").replace("{warp}", args[0])));
                    }
                } else {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Warps").replace("{warps}", Warps.get().getWarps())));
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/warp [nome])");
            config.set("Nao_Existe", "&cA warp &4{warp} &cnão existe.");

            config.set("Teleportando", "&aVocê será teleportado para a warp &2{warp} &aem &2{tempo} &asegundos.");
            config.set("Teleportado", "&aVocê foi teleportado para a warp &2{warp}&a!");

            config.set("Warps", "&aWarps: &7{warps}&a.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }


}
