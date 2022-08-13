package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.Cooldown;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Divulgar {

    public Divulgar(Core core) {
        HyzardCommand command = new HyzardCommand(core, "divulgar", "use para divulgar videos e lives (twitch e yt)", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {

            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.divulgar") && p.hasPermission("hyzardcore.*")) {
                    Cooldown cooldown = new Cooldown("Divulgar", 30, p);
                    if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("video")) {
                            if(cooldown.hasCooldown()) {
                                cooldown.getCooldown();
                                return false;
                            }

                            if (args[1].contains("youtube.com/")) {
                                API.get().broadcastMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Divulgando_Video").replace("{player}", p.getName())));
                                API.get().broadcastActionBarMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Divulgando_ActionBar").replace("{player}", p.getName())));
                                if (args[1].contains("https://")) {
                                    BaseComponent[] bc = new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Clique_Video")))
                                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, args[1]))
                                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                            .create();
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.spigot().sendMessage(bc);
                                    }
                                } else {
                                    BaseComponent[] bc = new ComponentBuilder(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Clique_Video")))
                                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://" + args[1]))
                                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                            .create();
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.spigot().sendMessage(bc);
                                    }
                                }
                                cooldown.createCooldown();
                            } else {
                                p.sendMessage(command.getMensagens().getMsg(PlaceholderAPI.setPlaceholders(p, "Como_Usar")));
                            }
                        } else if (args[0].equalsIgnoreCase("live")) {
                            if(cooldown.hasCooldown()) {
                                cooldown.getCooldown();
                                return false;
                            }
                            if (args[1].contains("twitch.tv/") || (args[1].contains("youtube.com/"))) {
                                API.get().broadcastMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Divulgando_Live").replace("{player}", p.getName())));
                                API.get().broadcastActionBarMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Divulgando_ActionBar").replace("{player}", p.getName())));
                                if (args[1].contains("https://")) {
                                    BaseComponent[] bc = new ComponentBuilder(command.getMensagens().getMsg("Clique_Live"))
                                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, args[1]))
                                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                            .create();
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.spigot().sendMessage(bc);
                                    }
                                } else {
                                    BaseComponent[] bc = new ComponentBuilder(command.getMensagens().getMsg("Clique_Live"))
                                            .event(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://" + args[1]))
                                            .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                            .create();
                                    for (Player player : Bukkit.getOnlinePlayers()) {
                                        player.spigot().sendMessage(bc);
                                    }
                                }
                                cooldown.createCooldown();
                            } else {
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                            }
                        } else {
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                        }
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                    }
                } else {
                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, Mensagens.get().getSemPerm()));
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/divulgar (video/live) [link twitch/link youtube]");
            config.set("Divulgando_Live", "&5{player} &destá divulgando uma live!");
            config.set("Clique_Live", "&5Clique para assistir!");
            config.set("Divulgando_Video", "&4{player} &cestá divulgando um vídeo!");
            config.set("Clique_Video", "&4Clique para assistir!");
            config.set("Divulgando_ActionBar", "&6{player} &eestá fazendo uma divulgação!");
            config.set("Jogador_Offline", "&cJogador offline!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
