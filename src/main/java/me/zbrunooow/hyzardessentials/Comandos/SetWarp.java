package me.zbrunooow.hyzardessentials.Comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Mensagens;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.LocsFile;
import me.zbrunooow.hyzardessentials.utils.API;
import me.zbrunooow.hyzardessentials.utils.Warps;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class SetWarp {

    public SetWarp(Core core) {
        HyzardCommand command = new HyzardCommand(core, "setwarp", "setar varias warps uhulll", "", new ArrayList<>());
        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if (!(s instanceof Player)) return true;
                Player p = (Player) s;

                if (p.hasPermission("hyzardcore.setwarp") || p.hasPermission("hyzardcore.*")) {
                    if(args.length == 1) {
                        if(core.getWarps().getConfig().getString("Warps." + args[0].toLowerCase() + ".Locs") == null) {
                            core.getWarps().getConfig().set("Warps." + args[0].toLowerCase() + ".Nome", args[0]);
                            core.getWarps().getConfig().set("Warps." + args[0].toLowerCase() + ".Loc", API.get().serialize(p.getLocation()));
                            core.getWarps().saveConfig();
                            core.getWarps().reloadConfig();
                            core.reloadWarpAPI();

                            p.sendMessage(command.getMensagens().getMsg("Setou_Warp").replace("{nome}", args[0]));
                            p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 10);
                        } else {
                            p.sendMessage(command.getMensagens().getMsg("Ja_Existe"));
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
            config.set("Como_Usar", "&cUse (/setwarp [nome])");
            config.set("Ja_Existe", "&cA warp &4{warp} &cjá existe!");
            config.set("Setou_Warp", "&aVocê setou a Warp &2{nome} &acom sucesso!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
