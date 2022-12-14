package me.zbrunooow.hyzardessentials.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.zbrunooow.hyzardessentials.Core;
import me.zbrunooow.hyzardessentials.Manager;
import me.zbrunooow.hyzardessentials.objetos.Home;
import me.zbrunooow.hyzardessentials.objetos.HyzardCommand;
import me.zbrunooow.hyzardessentials.objetos.Jogador;
import me.zbrunooow.hyzardessentials.utils.API;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.TimeUnit;

public class HomesListener implements Listener {

    @EventHandler
    public void aoClicarHome(InventoryClickEvent e) {
        if (e.getInventory().getName().contains("homes")) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (e.getSlot() < 45) {
                if(e.getCurrentItem().getType() == Material.SIGN) {
                    HyzardCommand command = Manager.get().getCommand("home");
                    Player p = (Player) e.getWhoClicked();
                    Jogador j = Manager.get().getJogador(p);
                    Home home = j.getHome(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
                    p.closeInventory();

                    long teleportando = System.currentTimeMillis();
                    p.sendMessage(command.getMensagens().getMsg("Teleportando").replace("{home}", home.getNome()).replace("{segundos}", p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? "0" : "3"));
                    p.setMetadata("teleportandohome", new FixedMetadataValue(Core.getInstance(), home.getNome()));
                    Location teleportandoloc = p.getLocation();
                    Location loc = home.getLoc();
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            long atual = System.currentTimeMillis() - teleportando;
                            int seconds = p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? 0 : 3 - (int) TimeUnit.MILLISECONDS.toSeconds(atual);
                            int delay = p.hasPermission("hyzardcore.nodelay") || p.hasPermission("hyzardcore.*") ? 0 : 3;
                            if (!p.isOnline()) this.cancel();
                            if (TimeUnit.MILLISECONDS.toSeconds(atual) >= delay) {
                                this.cancel();
                                Bukkit.getScheduler().runTask(Core.getInstance(), () -> {
                                    p.teleport(loc);
                                    API.get().sendActionBarMessage(p, PlaceholderAPI.setPlaceholders(p, command.getMensagens().getMsg("Teleportado_ActionBar").replace("{home}", home.getNome()).replace("{home}", home.getNome())));
                                    p.playSound(loc, Sound.ENDERMAN_TELEPORT, 1, 4);
                                    p.removeMetadata("teleportandohome", Core.getInstance());
                                });
                            } else {
                                if (!p.getLocation().equals(teleportandoloc)) {
                                    this.cancel();
                                    p.sendMessage(command.getMensagens().getMsg("Se_Mexeu"));
                                    API.get().sendActionBarMessage(p, command.getMensagens().getMsg("Se_Mexeu"));
                                    p.removeMetadata("teleportandohome", Core.getInstance());
                                    return;
                                }
                                API.get().sendActionBarMessage(p, command.getMensagens().getMsg("Teleportando_ActionBar").replace("{home}", home.getNome()).replace("{segundos}", API.get().formatTime(seconds)));
                            }
                        }
                    }.runTaskTimerAsynchronously(Core.getInstance(), 0, 5);
                }
            } else {
                if (e.getSlot() == 45) {
                    e.getWhoClicked().closeInventory();
                }
            }
        }
    }

    @EventHandler
    public void aoTeleportarHomeAberto(PlayerTeleportEvent e) {
        if(e.getPlayer().getOpenInventory().getTopInventory().getName().contains("homes")) {
            e.getPlayer().closeInventory();
        }
    }

}
