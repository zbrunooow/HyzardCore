package me.zbrunooow.hyzardessentials;

import org.bukkit.configuration.file.FileConfiguration;

public class Mensagens {

    private String semPerm;

    public Mensagens() {
        semPerm = replace("Sem-Permissao");
    }

    private String replace(String linha) {
        FileConfiguration config = Core.getInstance().getConfig();
        return config.getString("Mensagens." + linha).replace('&', '§');
    }

    public String getSemPerm() {
        return semPerm;
    }

    public static Mensagens get(){
        return Core.getInstance().getMsgs();
    }

}
