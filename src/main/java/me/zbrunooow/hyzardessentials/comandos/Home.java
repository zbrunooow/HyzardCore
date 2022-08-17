package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class Home {

    public Home(Core core) {
        HyzardCommand command = new HyzardCommand(core, "home", "va ate suas homes", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;

                if(!p.hasPermission("hyzardcore.home") && !p.hasPermission("hyzardcore.*")) {
                    p.sendMessage(Mensagens.get().getSemPerm());
                    return false;
                }

                if(args.length > 1) {
                    p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    return false;
                }

                if(args.length == 0) {
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 10);
                    Manager.get().openHomesMenu(p);
                    return false;
                }

                if(p.hasMetadata("teleportandohome")) {
                    p.sendMessage(command.getMensagens().getMsg("Ja_Teleportando").replace("{home}", String.valueOf(p.getMetadata("teleportandohome").get(0).value())));
                    return false;
                }

                Jogador j = Manager.get().getJogador(p);
                me.zbrunooow.hyzardessentials.objetos.Home home = j.getHome(args[0]);
                if(home != null) {
                    long teleportando = System.currentTimeMillis();
                    p.sendMessage(command.getMensagens().getMsg("Teleportando").replace("{home}", home.getNome()).replace("{segundos}", p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? "0" : "3"));
                    p.setMetadata("teleportandohome", new FixedMetadataValue(core, home.getNome()));
                    Location teleportandoloc = p.getLocation();
                    Location loc = home.getLoc();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            long atual = System.currentTimeMillis() - teleportando;
                            int seconds = p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? 0 : 3 - (int) TimeUnit.MILLISECONDS.toSeconds(atual);
                            int delay = p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? 0 : 3;
                            if(!p.isOnline()) this.cancel();
                            if (TimeUnit.MILLISECONDS.toSeconds(atual) >= delay) {
                                this.cancel();
                                Bukkit.getScheduler().runTask(core, () -> {
                                    p.teleport(loc);
                                    API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar").replace("{home}", home.getNome()).replace("{home}", home.getNome())));
                                    p.playSound(loc, Sound.ENDERMAN_TELEPORT, 1, 4);
                                    p.removeMetadata("teleportandohome", core);
                                });
                            } else {
                                if(!p.getLocation().equals(teleportandoloc)) {
                                    this.cancel();
                                    p.sendMessage(command.getMensagens().getMsg("Se_Mexeu"));
                                    API.get().sendActionBarMessage(p, command.getMensagens().getMsg("Se_Mexeu"));
                                    p.removeMetadata("teleportandohome", core);
                                    return;
                                }
                                API.get().sendActionBarMessage(p, command.getMensagens().getMsg("Teleportando_ActionBar").replace("{home}", home.getNome()).replace("{segundos}", API.get().formatTime(seconds)));
                            }
                        }
                    }.runTaskTimerAsynchronously(core, 0, 5);
                    return true;
                }


                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Home_Inexistente").replace("{home}", args[0])));
                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/home [home])!");
            config.set("Home_Inexistente", "&cA home &4{home} &cnão existe!");
            config.set("Ja_Teleportando", "&cVocê já está se teleportando para uma home. (&4{home}&c)");
            config.set("Se_Mexeu", "&cVocê se mexeu, cancelando teleporte!");

            config.set("Teleportando", "&aVocê será teleportado para a home &2{home} &aem &2{segundos} &asegundos!");
            config.set("Teleportando_ActionBar", "&aVocê será teleportado para a home &2{home} &aem &2{segundos} &asegundos!");

            config.set("Teleportado", "&aVocê se teleportou para a home &2{home} &acom sucesso!");
            config.set("Teleportado_ActionBar", "&aVocê se teleportou para a home &2{home} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
