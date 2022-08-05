package me.zbrunooow.HyzardCore.Utils;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.concurrent.TimeUnit;

public class API {

    public void broadcastMessage(String msg){
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(msg);
        }
    }

    public boolean cooldownPlayer(String nomecooldown, int tempominutos, Player player) {
        if (!player.hasMetadata("cooldown" + nomecooldown)) {
            player.setMetadata("cooldown" + nomecooldown, new FixedMetadataValue(Core.getInstance(), System.currentTimeMillis()));
            return true;
        }
        long time = System.currentTimeMillis() - (long) player.getMetadata("cooldown" + nomecooldown).get(0).value();
        if (time >= 60000* (long) tempominutos) player.removeMetadata("cooldown" + nomecooldown, Core.getInstance());
        if (player.hasMetadata("cooldown" + nomecooldown)) {
            player.sendMessage("§cVocê precisa aguardar §4" + (30 - TimeUnit.MILLISECONDS.toMinutes(time)) + " §cminutos para " + nomecooldown + " §cnovamente.");
        }
        return false;
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
        return loc.getWorld().getName() + " : " + loc.getX() + " : " + loc.getY() + " : " + loc.getZ() + " : " + loc.getYaw() + " : " + loc.getPitch();
    }

    public Location unserialize(String loc) {
        String[] args = loc.split(" : ");
        return new Location(Bukkit.getWorld(args[0]), Double.parseDouble(args[1]), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Float.parseFloat(args[4]), Float.parseFloat(args[5]));
    }

    public static Inventory criarInventario(InventoryHolder owner, InventoryType type) {
        return Bukkit.getServer().createInventory(owner, type);
    }

    public static API get(){
        return Core.getInstance().getApi();
    }

}
