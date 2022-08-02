package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
import me.zbrunooow.HyzardCore.Utils.API;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Divulgar implements CommandExecutor {

    public Divulgar(Core core) {
        core.getCommand("divulgar").setExecutor(this);
    }

    public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
        if (!(s instanceof Player)) return true;
        Player p = (Player) s;

        if(cmd.getName().equalsIgnoreCase("divulgar")) {
            if (p.hasPermission("hyzardcore.divulgar") || p.hasPermission("hyzardcore.*")) {
                if(args.length == 2) {
                    if(args[0].equalsIgnoreCase("video")) {
                        if(args[1].contains("youtube.com/")) {
                            if(!API.get().cooldownPlayer("divulgar", 30, p)) {
                                return false;
                            }
                            API.get().sendMessage("§4" + p.getName() + " §cestá divulgando um vídeo!");
                            if(args[1].contains("https://")) {
                                BaseComponent[] bc = new ComponentBuilder("§4Clique para assistir!")
                                        .event(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, args[1]))
                                        .event(new net.md_5.bungee.api.chat.HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                        .create();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.spigot().sendMessage(bc);
                                }
                            } else {
                                BaseComponent[] bc = new ComponentBuilder("§4Clique para assistir!")
                                        .event(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://" + args[1]))
                                        .event(new net.md_5.bungee.api.chat.HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                        .create();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.spigot().sendMessage(bc);
                                }
                            }
                        } else {
                            p.sendMessage("§cUse (/divulgar video [link do video no youtube])");
                        }
                    } else if (args[0].equalsIgnoreCase("live")) {
                        if(args[1].contains("twitch.tv/") || (args[1].contains("youtube.com/"))) {
                            API.get().sendMessage("§5" + p.getName() + " §destá divulgando uma live!");
                            if(args[1].contains("https://")) {
                                BaseComponent[] bc = new ComponentBuilder("§5Clique para assistir!")
                                        .event(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, args[1]))
                                        .event(new net.md_5.bungee.api.chat.HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                        .create();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.spigot().sendMessage(bc);
                                }
                            } else {
                                BaseComponent[] bc = new ComponentBuilder("§5Clique para assistir!")
                                        .event(new net.md_5.bungee.api.chat.ClickEvent(ClickEvent.Action.OPEN_URL, "https://" + args[1]))
                                        .event(new net.md_5.bungee.api.chat.HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Clique para assistir!").create()))
                                        .create();
                                for (Player player : Bukkit.getOnlinePlayers()) {
                                    player.spigot().sendMessage(bc);
                                }
                            }
                        } else {
                            p.sendMessage("§cUse (/divulgar live [link da live na twitch ou no youtube])");
                        }
                    } else {
                        p.sendMessage("§cUse (/divulgar (video/live) [link twitch/link youtube]");
                    }
                } else {
                    p.sendMessage("§cUse (/divulgar (video/live) [link twitch/link youtube]");
                }
            } else {
                p.sendMessage(Mensagens.get().semPerm);
            }
        }

        return false;
    }
}
