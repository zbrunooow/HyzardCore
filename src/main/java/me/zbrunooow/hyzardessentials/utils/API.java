package me.zbrunooow.hyzardessentials.utils;

import me.zbrunooow.hyzardessentials.Config;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.objetos.Kit;
import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class API {

    private final Pattern pattern = Pattern.compile("[^A-zÀ-ü0-9@$]");

    public void sendActionBarMessage(Player player, String message) {
        PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message.replace("&", "§")), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }

    public void broadcastActionBarMessage(String message) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(message.replace("&", "§")), (byte) 2);
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public int getRandomNumber(int max) {
        int i = new Random().nextInt(max);
        while(i==8) i = new Random().nextInt(max);
        return i;
    }

    public ItemStack aleatorioItem() {
        System.out.print("A");
        List<String> lista = Config.get().getMensagensmorte();
        Random r = new Random();
        int randomitem = r.nextInt(lista.size());
        String randomElement = lista.get(randomitem);

        String[] lore = {"", " §7- " + randomElement.replace("&", "§").replace("{player}", "killer").replace("{morto}", "victim"), ""};

        ItemStack item = new ItemStack(Material.NAME_TAG, 1);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("§cMensagem de morte personalizada:");
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);

        return item.clone();
    }

    public void broadcastMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    public void broadcastMessageDestacada(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("");
            p.sendMessage(msg);
            p.sendMessage("");
        }
    }

    public boolean isEnchant(String nome) {
        if(Enchantment.getByName(nome) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isEnchant(Integer id) {
        if(Enchantment.getById(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPotionType(String nome) {
        if(PotionEffectType.getByName(nome) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isPotionType(Integer id) {
        if(PotionEffectType.getById(id) != null) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isDouble(String string) {
        try {
            Double.parseDouble(string);
            return true;
        } catch (Exception ignored) {
            return false;
        }
    }

    public boolean isInt(String string) {
        try {
            if(Integer.parseInt(string) > 0) {
                return true;
            }
            return false;
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

    public void abrirCaixa(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "Caixinha");
        p.openInventory(inv);

        new BukkitRunnable() {
            int slot = 0;
            int slot2 = 26;
            @Override
            public void run() {
                ItemStack vrido = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) getRandomNumber(15));
                ItemStack vrido2 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) getRandomNumber(15));
                ItemMeta meta = vrido.getItemMeta();
                ItemMeta meta2 = vrido2.getItemMeta();
                meta.setDisplayName("§7§m-<>-");
                meta2.setDisplayName("§7§m-<>-");
                vrido.setItemMeta(meta);
                vrido2.setItemMeta(meta2);
                if(slot <= 12) {
                    p.getOpenInventory().getTopInventory().setItem(slot, vrido);
                    slot++;
                }
                if(slot2 >= 14) {
                    p.getOpenInventory().getTopInventory().setItem(slot2, vrido2);
                    slot2--;
                    p.playSound(p.getLocation(), Sound.ITEM_PICKUP, 1, 5);
                }
                if(slot2 == 13 || slot == 13) {
                    p.getOpenInventory().getTopInventory().setItem(13, API.get().aleatorioItem());
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 5);
                    this.cancel();
                }
            }
        }.runTaskTimerAsynchronously(Core.getInstance(), 0, 5);
    }

    public String serialize(Location loc) {
        return loc.getWorld().getName() + "<>" + loc.getX() + "<>" + loc.getY() + "<>" + loc.getZ() + "<>" + loc.getYaw() + "<>" + loc.getPitch();
    }

    public String serializeKit(Kit kit) {
        return kit.getNome() + ";" + (TimeUnit.MILLISECONDS.toMinutes(System.currentTimeMillis())+kit.getCooldown()) + "<>";
    }

    public String[] unserializeKit(String s) {
        String[] args = s.split("<>");
        return args;
    }

    public String formatTime(int segundos) {
        long HH = segundos / 3600;
        long MM = (segundos % 3600) / 60;
        long SS = segundos % 60;
        String data = " ";
        if (HH > 0) data+=" "+HH+"h";
        if (MM > 0) data+=" "+MM+"m";
        if (SS > 0) data+=" "+SS+"s";
        while(data.startsWith(" ")) {
            data = data.replaceFirst(" ", new String());
        }
        return data.length() > 0 ? data : "0s";
    }

    public String formatTimeKit(int segundos) {
        long DD = segundos / 86400;
        long HH = (segundos % 86400) / 3600;
        long MM = (segundos % 3600) / 60;
        long SS = segundos % 60;
        String data = " ";
        if (DD > 0) data+=" "+DD+" dias,";
        if (HH > 0) data+=" "+HH+" horas,";
        if (MM > 0) data+=" "+MM+" minutos,";
        if (SS > 0) data+=" "+SS+" segundos,";
        if (data.endsWith(",")) {
            data = data.substring(0, data.length() - 1);
        }
        while(data.startsWith(" ")) {
            data = data.replaceFirst(" ", new String());
        }
        return data.length() > 0 ? data : "0s";
    }

    public String formatTimeSecond(int segundos) {
        long DD = segundos / 86400;
        long HH = (segundos % 86400) / 3600;
        long MM = (segundos % 3600) / 60;
        long SS = segundos % 60;
        String data = " ";
        if (DD > 0) data+=" "+DD+"d,";
        if (HH > 0) data+=" "+HH+"h,";
        if (MM > 0) data+=" "+MM+"m,";
        if (SS > 0) data+=" "+SS+"s,";
        if (data.endsWith(",")) {
            data = data.substring(0, data.length() - 1);
        }
        while(data.startsWith(" ")) {
            data = data.replaceFirst(" ", new String());
        }
        return data.length() > 0 ? data : "0s";
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
            dataOutput.writeInt(items.clone().length);

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
            return items.clone();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getFreeSlots(Player player) {
        int freeslots = 0;
        for (ItemStack it : player.getInventory().getContents()) {
            if (it == null || it.getType() == Material.AIR) {
                freeslots++;
            }
        }

        return freeslots;
    }

    public double formatValue(double valor) {
        NumberFormat formatter = new DecimalFormat("0.00");
        return Double.valueOf(formatter.format(valor).replace(",", "."));
    }

    public Integer getHomeLimit(Player p) {
        if(p.hasPermission("hyzardcore.*") || p.hasPermission("*")) return 45;
        for(PermissionAttachmentInfo perm : p.getEffectivePermissions()) {
            if(perm.getPermission().startsWith("hyzardcore.homes.")) {
                return Integer.parseInt(perm.getPermission().replace("hyzardcore.homes.", ""));
            }
        }
        return 2;
    }

    public Boolean stringContainsSpecialCharacters(String input) {
        return pattern.matcher(input).find();
    }

    public String descriptografar(String linha) {
        return new String(Base64.getDecoder().decode(linha));
    }

    public String criptografar(String linha) {
        return Base64.getEncoder().encodeToString(linha.getBytes());
    }

}
