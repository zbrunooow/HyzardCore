package me.zbrunooow.HyzardCore.Comandos;

import me.zbrunooow.HyzardCore.Core;
import me.zbrunooow.HyzardCore.Mensagens;
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
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;
                if (p.hasPermission("hyzardcore.gamemode") || p.hasPermission("hyzardcore.*")) {
                    if (args.length == 1) {
                        switch (args[0].toLowerCase()){
                            case "0":
                                if(p.getGameMode() == GameMode.SURVIVAL) {
                                    p.sendMessage(command.getMensagens().getMsg("Gamemode_Atual"));
                                    return false;
                                }
                                p.setGameMode(GameMode.SURVIVAL);
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                p.sendMessage(command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "SOBREVIVÊNCIA"));
                                break;
                            case "1":
                                if(p.getGameMode() == GameMode.CREATIVE) {
                                    p.sendMessage(command.getMensagens().getMsg("Gamemode_Atual"));
                                    return false;
                                }
                                p.setGameMode(GameMode.CREATIVE);
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                p.sendMessage(command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "CRIATIVO"));
                                break;
                            case "2":
                                if(p.getGameMode() == GameMode.ADVENTURE) {
                                    p.sendMessage(command.getMensagens().getMsg("Gamemode_Atual"));
                                    return false;
                                }
                                p.setGameMode(GameMode.ADVENTURE);
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                p.sendMessage(command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "AVENTURA"));
                                break;
                            case "3":
                                if(p.getGameMode() == GameMode.SPECTATOR) {
                                    p.sendMessage(command.getMensagens().getMsg("Gamemode_Atual"));
                                    return false;
                                }
                                p.setGameMode(GameMode.SPECTATOR);
                                p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                                p.sendMessage(command.getMensagens().getMsg("Gamemode_Alterado").replace("{gamemode}", "ESPECTADOR"));
                                break;
                            default:
                                p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                                break;
                        }
                    } else {
                        p.sendMessage(command.getMensagens().getMsg("Como_Usar"));
                    }
                } else {
                    p.sendMessage(Mensagens.get().getSemPerm());
                }

                return false;
            }
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/gm [0/1/2/3]");

            config.set("Gamemode_Alterado", "&aVocê foi para o gamemode {gamemode}");
            config.set("Gamemode_Atual", "&cVocê já está nesse gamemode.");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }
}
