package me.zbrunooow.hyzardessentials.objetos;

import com.avaje.ebean.validation.NotNull;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.MetadataValue;

import java.io.File;

public class Kit {

    private String nome;
    private String perm;
    private Integer cooldown;
    private Integer size;
    private File file;
    private FileConfiguration fileConfiguration;

    public Kit(String nome, Inventory inv) {
        int size = 0;
        this.nome = nome;
        this.perm = "";
        this.cooldown = 0;
        if(inv != null) {
            for(ItemStack i : inv.getContents()) {
                if(i != null) {
                    size++;
                }
            }
        }
        this.size = size;
        file = new File(Core.getInstance().getDataFolder() + "/kits/" + this.nome + ".yml");
        if (!file.exists()){
            try {
                file.createNewFile();
                ItemStack[] invcontent = inv.getContents();
                API.get().serializeItems(invcontent);
                getFile().set("Nome", this.nome);
                getFile().set("Permissao", this.perm);
                getFile().set("Cooldown", this.cooldown);
                getFile().set("Size", this.size);
                getFile().set("Inventario", API.get().serializeItems(invcontent));
                save();
                loadFile();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        loadKit();
        Manager.get().getKits().add(this);
    }

    public boolean getContents(Player player) {
        if(API.get().getFreeSlots(player) >= this.size) {
            for(ItemStack item : API.get().unserializeItems(getFile().getString("Inventario"))) {
                if (item != null) {
                    player.getInventory().addItem(item);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public void loadKit(){
        this.size = getFile().getInt("Size");
        this.perm = getFile().getString("Permissao");
        this.cooldown = getFile().getInt("Cooldown");
    }

    public void save(){
        try {
            fileConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFile() {
        if (!this.file.exists()) {
            Core.getInstance().saveResource(this.nome + ".yml", false);
        }
        this.fileConfiguration = YamlConfiguration.loadConfiguration(this.file);
    }

    public FileConfiguration getFile() {
        if (this.fileConfiguration == null) {
            loadFile();
        }
        return this.fileConfiguration;
    }

    public void deleteFile() {
        this.file.delete();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getPerm() {
        return perm;
    }

    public void setPerm(String perm) {
        this.perm = perm;
    }

    public Integer getCooldown() {
        return cooldown;
    }

    public void setCooldown(Integer cooldown) {
        this.cooldown = cooldown;
    }
}
