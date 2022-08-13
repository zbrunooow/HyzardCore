package me.zbrunooow.hyzardessentials.comandos;

import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Lixeiro {

    public Lixeiro(Core core) {
        HyzardCommand command = new HyzardCommand(core, "lixeiro", "veja quanto tempo falta para limpar o chão", "", new ArrayList<>());

        command.setExecutor(new CommandExecutor() {
            @Override
            public boolean onCommand(CommandSender s, Command cmd, String lb, String[] args) {
                if(!(s instanceof Player)) return false;
                Player p = (Player) s;



                return false;
            }
        });

        command.createConfig(() -> {
            command.saveConfig();
            command.loadConfig();
        });

        command.getMensagens().createMensagens(() -> {
            ConfigurationSection config = command.getMensagens().getConfigurationSection();
            config.set("Como_Usar", "&cUse (/clear [player])");
            config.set("Avisar_Outro", "&aSeu inventário foi limpo por &2{playerlimpou}&a!");
            config.set("Avisar_Outro_ActionBar", "&aSeu inventário foi limpo por &2{playerlimpou}&a!");
            config.set("Inventario_Limpo", "&aVocê limpou seu inventário com sucesso!");
            config.set("Inventario_Outro", "&aVocê limpou o inventário de &2{player} &acom sucesso.");
            config.set("Inventario_All", "&aVocê limpou o inventário de todos os jogadores.");
            config.set("Jogador_Offline", "&cJogador offline!");
            config.set("Voce_Mesmo", "&cPara limpar seu inventário, basta utilizar /clear!");

            command.saveConfig();
            command.getMensagens().loadMensagens();
        });

    }

}
