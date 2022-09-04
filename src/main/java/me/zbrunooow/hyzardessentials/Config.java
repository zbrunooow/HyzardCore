package me.zbrunooow.hyzardessentials;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class Config {

    private int tempoSemDanoTeleportar;
    private Boolean chuva;
    private Boolean sempredia;
    private Boolean bloquearfome;
    private Boolean ativartagviciado;
    private Boolean bloquearmsgentrada;
    private Boolean bloquearmsgsaida;
    private Boolean bloquearmonstros;
    private Boolean bloquearestragarplantacao;
    private Boolean bloquearanimais;
    private Boolean bloquearquedafolhas;
    private Boolean bloqueardanocacto;
    private String tagviciado;
    private String blockedcommandmsg;
    private List<String> mundoschuva;
    private List<String> mensagensmorte;
    private List<String> blockedcontainers;
    private List<String> blockedcommands;
    private List<String> mundosdia;

    public Config() {
        tempoSemDanoTeleportar = replaceInt("Tempo-Sem-Dano-Ao-Teleportar");
        mundoschuva = replaceList("Chuva.Mundos");
        mundosdia = replaceList("Dia.Mundos");
        blockedcontainers = replaceList("Blocked-Containers");
        blockedcommands = replaceList("Blocked-Commands");
        mensagensmorte = replaceList("Mensagens-de-Morte");
        bloquearmsgentrada = Boolean.valueOf(replace("Bloquear-Msg-Entrada"));
        bloquearquedafolhas = Boolean.valueOf(replace("Bloquear-Queda-Folhas"));
        bloqueardanocacto = Boolean.valueOf(replace("Bloquear-Dano-Cacto"));
        bloquearestragarplantacao = Boolean.valueOf(replace("Bloquear-Estragar-Plantacao"));
        bloquearmsgsaida = Boolean.valueOf(replace("Bloquear-Msg-Saida"));
        chuva = Boolean.valueOf(replace("Chuva.Desativar"));
        bloquearfome = Boolean.valueOf(replace("Bloquear-Fome"));
        sempredia = Boolean.valueOf(replace("Dia.Sempre-Dia"));
        ativartagviciado = Boolean.valueOf(replace("Dia.Sempre-Dia"));
        bloquearmonstros = Boolean.valueOf(replace("Bloquear-Monstros"));
        bloquearanimais = Boolean.valueOf(replace("Bloquear-Animais"));
        blockedcommandmsg = replace("Blocked-Command-Msg");
        tagviciado = replace("Tags.Top-Online.Tag");
    }

    private int replaceInt(String linha) {
        FileConfiguration config = Core.getInstance().getConfig();
        return config.getInt("Config." + linha);
    }

    private String replace(String linha) {
        FileConfiguration config = Core.getInstance().getConfig();
        return config.getString("Config." + linha).replace('&', '§');
    }

    private List<String> replaceList(String linha) {
        FileConfiguration config = Core.getInstance().getConfig();
        return config.getStringList("Config." + linha);
    }

    public int getTempoSemDanoTeleportar() {return tempoSemDanoTeleportar;}

    public Boolean getDesativarChuva() {
        return chuva;
    }
    public Boolean getSempredia() {
        return sempredia;
    }
    public Boolean getBloquearestragarplantacao() {
        return bloquearestragarplantacao;
    }
    public Boolean getAtivartagviciado() {return ativartagviciado;}
    public Boolean getBloquearquedafolhas() {return bloquearquedafolhas;}
    public Boolean getBloquearfome() { return bloquearfome; }
    public Boolean getBloquearmsgentrada() { return bloquearmsgentrada; }
    public Boolean getBloquearmsgsaida() { return bloquearmsgsaida; }
    public Boolean getBloquearmonstros() { return bloquearmonstros; }
    public Boolean getBloquearanimais() { return bloquearanimais; }
    public Boolean getBloqueardanocacto() { return bloqueardanocacto; }

    public String getTagViciado() {
        return tagviciado;
    }
    public String getBlockedcommandmsg() {
        return blockedcommandmsg;
    }

    public List<String> getBlockedcontainers() { return blockedcontainers; }
    public List<String> getBlockedcommands() { return blockedcommands; }
    public List<String> getMundosChuva() {return mundoschuva;}
    public List<String> getMundosDia() {
        return mundosdia;
    }
    public List<String> getMensagensmorte() {
        return mensagensmorte;
    }

    public static Config get(){
        return Core.getInstance().getConfiguration();
    }

}
