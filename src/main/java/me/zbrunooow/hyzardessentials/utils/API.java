package me.zbrunooow.hyzardessentials.utils;

import me.zbrunooow.hyzardessentials.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

public class API {



    public void broadcastMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    public boolean isInt(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isMaterial(String string) {
        try {
            Material.valueOf(string.toUpperCase());
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public String serialize(Location loc) {
        return loc.getWorld().getName() + "<>" + loc.getX() + "<>" + loc.getY() + "<>" + loc.getZ() + "<>" + loc.getYaw() + "<>" + loc.getPitch();
    }

    public Location unserialize(String loc) {
        String[] args = loc.split("<>");
        return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }

    public static Inventory criarInventario(InventoryHolder owner, InventoryType type) {
        return Bukkit.getServer().createInventory(owner, type);
    }

    public static API get(){
        return Core.getInstance().getApi();
    }

    public String serializeItems(ItemStack[] items) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(items.length);

            // Save every element in the list
            for (int i = 0; i < items.length; i++) {
                dataOutput.writeObject(items[i]);
            }

            // Serialize that array
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            return null;
        }
    }

    public ItemStack[] unserializeItems(String data) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = null;
            try {
                dataInput = new BukkitObjectInputStream(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            ItemStack[] items = null;
            try {
                items = new ItemStack[dataInput.readInt()];
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < items.length; i++) {
                try {
                    items[i] = (ItemStack) dataInput.readObject();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try {
                dataInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return items;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;

        }



    }

    public String descriptografar(String linha) {
        return new String(Base64.getDecoder().decode(linha));
    }

    public String criptografar(String linha) {
        return Base64.getEncoder().encodeToString(linha.getBytes());
    }

}
