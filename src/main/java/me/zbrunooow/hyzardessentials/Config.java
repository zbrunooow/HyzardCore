package me.zbrunooow.hyzardessentials;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    private Boolean chuva;
    private Boolean sempredia;
    private List<String> mundoschuva;
    private List<String> mundosdia;

    public Config() {
        chuva = Boolean.valueOf(replace("Chuva.Desativar"));
        mundoschuva = replaceList("Chuva.Mundos");
        sempredia = Boolean.valueOf(replace("Dia.Sempre-Dia"));
        mundosdia = replaceList("Dia.Mundos");
    }

    private String replace(String linha) {
        FileConfiguration config = Core.getInstance().getConfig();
        return config.getString("Config." + linha).replace('&', '§');
    }

    private List<String> replaceList(String linha) {
        FileConfiguration config = Core.getInstance().getConfig();
        return config.getStringList("Config." + linha);
    }

    public Boolean getDesativarChuva() {
        return chuva;
    }
    public Boolean getSempredia() {
        return sempredia;
    }

    public List<String> getMundosChuva() {
        return mundoschuva;
    }
    public List<String> getMundosDia() {
        return mundosdia;
    }

    public static Config get(){
        return Core.getInstance().getConfiguration();
    }

}
