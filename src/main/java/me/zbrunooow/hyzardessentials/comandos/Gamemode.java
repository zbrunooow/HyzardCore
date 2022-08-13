package me.zbrunooow.hyzardessentials.comandos;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Gamemode {

    public Gamemode(Core core) {
        HyzardCommand command = new HyzardCommand(core, "gamemode", "troque seu gamemode", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {

            @Override
            public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
                if (s instanceof Player) {
                    Player p = (Player) s;
                    if (p.hasPermission("hyzardcore.gamemode") || p.hasPermission("hyzardcore.*")) {
                        if (args.length == 1) {
                            switch (args[0].toLowerCase()) {
                                case "0":
                                    if (p.getGameMode() == GameMode.SURVIVAL) {
                                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual")));
                                        return false;
                                    }
                                    p.setGameMode(GameMode.SURVIVAL);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "SOBREVIVÊNCIA")));
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "SOBREVIVÊNCIA")));
                                    break;
                                case "1":
                                    if (p.getGameMode() == GameMode.CREATIVE) {
                                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual")));
                                        return false;
                                    }
                                    p.setGameMode(GameMode.CREATIVE);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "CRIATIVO")));
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "CRIATIVO")));
                                    break;
                                case "2":
                                    if (p.getGameMode() == GameMode.ADVENTURE) {
                                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual")));
                                        return false;
                                    }
                                    p.setGameMode(GameMode.ADVENTURE);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "AVENTURA")));
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "AVENTURA")));
                                    break;
                                case "3":
                                    if (p.getGameMode() == GameMode.SPECTATOR) {
                                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual")));
                                        return false;
                                    }
                                    p.setGameMode(GameMode.SPECTATOR);
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "ESPECTADOR")));
                                    p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                    API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "ESPECTADOR")));
                                    break;
                                default:
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                                    break;
                            }
                        } else if (args.length == 2) {
                            Player p2 = Bukkit.getPlayerExact(args[1]);
                            if(p2 != null) {
                                if (p2 != p) {
                                    switch (args[0].toLowerCase()) {
                                        case "0":
                                            if (p2.getGameMode() == GameMode.SURVIVAL) {
                                                p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "SOBREVIVÊNCIA").replace("{player}", p2.getName())));
                                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual_Outro_ActionBar").replace("{gamemode}", "SOBREVIVÊNCIA").replace("{player}", p2.getName())));
                                                return false;
                                            }
                                            p2.setGameMode(GameMode.SURVIVAL);
                                            p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "SOBREVIVÊNCIA")));
                                            p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "SOBREVIVÊNCIA")));
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            p2.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_Outro_ActionBar").replace("{gamemode}", "SOBREVIVÊNCIA").replace("{player}", p2.getName())));
                                            API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "SOBREVIVÊNCIA")));
                                            break;
                                        case "1":
                                            if (p2.getGameMode() == GameMode.CREATIVE) {
                                                p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "CRIATIVO").replace("{player}", p2.getName())));
                                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual_Outro_ActionBar").replace("{gamemode}", "CRIATIVO").replace("{player}", p2.getName())));
                                                return false;
                                            }
                                            p2.setGameMode(GameMode.CREATIVE);
                                            p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "CRIATIVO")));
                                            p2.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "CRIATIVO")));
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_Outro_ActionBar").replace("{gamemode}", "CRIATIVO").replace("{player}", p2.getName())));
                                            API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "CRIATIVO")));
                                            break;
                                        case "2":
                                            if (p2.getGameMode() == GameMode.ADVENTURE) {
                                                p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "AVENTURA").replace("{player}", p2.getName())));
                                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual_Outro_ActionBar").replace("{gamemode}", "AVENTURA").replace("{player}", p2.getName())));
                                                return false;
                                            }
                                            p2.setGameMode(GameMode.ADVENTURE);
                                            p2.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "AVENTURA")));
                                            p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "AVENTURA")));
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_Outro_ActionBar").replace("{gamemode}", "AVENTURA").replace("{player}", p2.getName())));
                                            API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "AVENTURA")));
                                            break;
                                        case "3":
                                            if (p2.getGameMode() == GameMode.SPECTATOR) {
                                                p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "ESPECTADOR").replace("{player}", p2.getName())));
                                                p.playSound(p.getLocation(), Sound.VILLAGER_NO, 1, 2);
                                                API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Atual_Outro_ActionBar").replace("{gamemode}", "ESPECTADOR").replace("{player}", p2.getName())));
                                                return false;
                                            }
                                            p2.setGameMode(GameMode.SPECTATOR);
                                            p2.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            p.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "ESPECTADOR")));
                                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                            p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "ESPECTADOR")));
                                            API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Gamemode_Alterado_Outro_ActionBar").replace("{gamemode}", "ESPECTADOR").replace("{player}", p2.getName())));
                                            API.get().sendActionBarMessage(p2, PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_ActionBar").replace("{gamemode}", "ESPECTADOR")));
                                            break;
                                        default:
                                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                                            break;
                                    }
                                } else {
                                    p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Voce_Mesmo")));
                                }
                            } else {
                                p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Jogador_Offline")));
                            }
                        } else {
                            p.sendMessage(PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Como_Usar")));
                        }
                    } else {
                        p.sendMessage(PlaceholderAPI.setPlaceholders(p, Mensagens.get().getSemPerm()));
                    }
                } else {
                    if (args.length == 2) {
                        Player p2 = Bukkit.getPlayerExact(args[1]);
                        if (p2 != null) {
                            switch (args[0].toLowerCase()) {
                                case "0":
                                    if (p2.getGameMode() == GameMode.SURVIVAL) {
                                        s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "SOBREVIVÊNCIA").replace("{player}", p2.getName())));
                                        return false;
                                    }
                                    p2.setGameMode(GameMode.SURVIVAL);
                                    s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "SOBREVIVÊNCIA")));
                                    p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "SOBREVIVÊNCIA")));
                                    break;
                                case "1":
                                    if (p2.getGameMode() == GameMode.CREATIVE) {
                                        s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "CRIATIVO").replace("{player}", p2.getName())));
                                        return false;
                                    }
                                    p2.setGameMode(GameMode.CREATIVE);
                                    s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "CRIATIVO")));
                                    p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "CRIATIVO")));
                                    break;
                                case "2":
                                    if (p2.getGameMode() == GameMode.ADVENTURE) {
                                        s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "AVENTURA").replace("{player}", p2.getName())));
                                        return false;
                                    }
                                    p2.setGameMode(GameMode.ADVENTURE);
                                    s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "AVENTURA")));
                                    p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "AVENTURA")));
                                    break;
                                case "3":
                                    if (p2.getGameMode() == GameMode.SPECTATOR) {
                                        s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Atual_Outro").replace("{gamemode}", "ESPECTADOR").replace("{player}", p2.getName())));
                                        return false;
                                    }
                                    p2.setGameMode(GameMode.SPECTATOR);
                                    s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado_Outro").replace("{player}", p2.getName()).replace("{gamemode}", "ESPECTADOR")));
                                    p2.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "ESPECTADOR")));
                                    break;
                                default:
                                    s.sendMessage(PlaceholderAPI.setPlaceholders(p2, command.getMensagens().getMsg("Como_Usar")));
                                    break;
                            }
                        } else {
                            s.sendMessage(command.getMensagens().getMsg("Jogador_Offline"));
                        }
                    } else {
                        s.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }
                }
                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/gm [0/1/2/3] [player]");
            config.set("Voce_Mesmo", "&cPara alterar seu gamemode, basta utilizar (/gm [0/1/2/3])");
            config.set("Jogador_Offline", "&cJogador Offline.");

            config.set("Gamemode_Alterado", "&aVocê foi para o gamemode {gamemode}");
            config.set("Gamemode_Alterado_Outro", "&aVocê alterou o gamemode de &2{player} &apara {gamemode}");
            config.set("Gamemode_Atual", "&cVocê já está nesse gamemode.");
            config.set("Gamemode_Atual_Outro", "&cO player &4{player} já está no gamemode {gamemode}.");

            config.set("Gamemode_Alterado_ActionBar", "&aVocê foi para o gamemode {gamemode}");
            config.set("Gamemode_Alterado_Outro_ActionBar", "&aVocê alterou o gamemode de &2{player} &apara {gamemode}");
            config.set("Gamemode_Atual_ActionBar", "&cVocê já está nesse gamemode.");
            config.set("Gamemode_Atual_Outro_ActionBar", "&cO player &4{player} já está no gamemode {gamemode}.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
