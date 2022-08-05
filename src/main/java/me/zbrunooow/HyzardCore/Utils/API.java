package me.zbrunooow.HyzardCore.Utils;

import me.zbrunooow.HyzardCore.Core;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
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
