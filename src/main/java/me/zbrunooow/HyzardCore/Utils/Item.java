package me.zbrunooow.HyzardCore.Utils;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {

    private ItemStack item;

    private int slot;

    private Double valor;

    private String permission;

    private Boolean player;

    public Item(ItemStack item) {
        this.item = item;
        this.slot = -1;
        this.player = false;
        this.permission = null;
        this.valor = 0.0;
    }

    @SuppressWarnings("deprecation")
    public Item(int ID, int DATA) {
        try {
            this.item = new ItemStack(Material.getMaterial(ID), 1, (short) DATA);
        } catch(Exception e) {
            this.item = new ItemStack(Material.getMaterial(0), 1, (short) 0);
        }
    }

    public Item(Material material, int DATA) {
        this.item = new ItemStack(material, 1, (short) DATA);
    }

    public void setOwner(String nick) {
        this.item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(nick);

        item.setItemMeta(meta);
    }

    public int getAmount() {
        return item.getAmount();
    }

    public Item(Material material) {
        this.item = new ItemStack(material);
    }

    public List<String> getLore() {
        return item.hasItemMeta() && item.getItemMeta().hasLore() ? item.getItemMeta().getLore() : new ArrayList<>();
    }

    public String getDisplayName() {
        return item.hasItemMeta() && item.getItemMeta().hasDisplayName() ? item.getItemMeta().getDisplayName() : item.getType().toString().replace("_", " ");
    }

    public void setDisplayName(String name) {
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

    public void setLore(List<String> lore) {
        ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    public void setLore(String... linha) {
        ItemMeta meta = item.getItemMeta();
        List<String> linhas = new ArrayList<>();
        for(String l : linha) {
            linhas.add(l);
        }
        meta.setLore(linhas);
        item.setItemMeta(meta);
    }

    public void setGlow() {

        if (item.getEnchantments().size() == 0) {
            item.addUnsafeEnchantment(Enchantment.PROTECTION_PROJECTILE, 1);
        }

        ItemMeta meta = item.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
    }

    public void setAmount(int amount) {
        item.setAmount(amount);
    }

    public void addEnchantment(Enchantment enchant, int level) {
        item.addUnsafeEnchantment(enchant, level);
    }

    public ItemStack build() {
        return item;
    }

    public ItemStack getItem() {
        return item;
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Boolean getPlayer() {
        return player;
    }

    public void setPlayer(Boolean player) {
        this.player = player;
    }
}
