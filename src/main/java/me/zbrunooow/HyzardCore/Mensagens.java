package me.zbrunooow.HyzardCore;

public class Mensagens {

    static String SemPerm = Core.getInstance().getConfig().getString("Sem-Permissao").replace('&', '§');

    public static String getSemPerm() {
        return SemPerm;
    }
}
