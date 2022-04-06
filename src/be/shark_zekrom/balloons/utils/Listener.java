package be.shark_zekrom.balloons.utils;

import be.shark_zekrom.balloons.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;

public class Listener implements org.bukkit.event.Listener {

    public void onDamage(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity entity = event.getEntity();
        if (SummonBallons.balloons.containsValue(entity)) {
            Bukkit.broadcastMessage("TEST");
            entity.setVelocity(damager.getLocation().getDirection().multiply(2.5D));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (SummonBallons.balloons.containsValue(entity)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onUnleash(PlayerUnleashEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getEntity();
        if (SummonBallons.balloons.containsValue(entity)) {
            SummonBallons.removeBalloon(player);
        }
    }

    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (SummonBallons.balloons.containsKey(player)) {
            ArmorStand as = SummonBallons.as.get(player);

            ItemStack item = as.getEquipment().getHelmet();

            SummonBallons.as.remove(player);
            as.remove();

            Parrot parrot = SummonBallons.balloons.get(player);
            SummonBallons.balloons.remove(player);
            parrot.remove();

            new BukkitRunnable() {
                @Override
                public void run() {
                    SummonBallons.summonBalloon(player,item);

                }
            }.runTaskLater(Main.getInstance(),10L);

        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (SummonBallons.balloons.containsKey(player)) {
            SummonBallons.removeBalloon(player);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (SummonBallons.balloons.containsKey(player)) {
            SummonBallons.removeBalloon(player);
        }
    }


    @EventHandler
    public void onLeash(PlayerLeashEntityEvent event) {
        Player player = event.getPlayer();
        if (SummonBallons.balloons.containsKey(player)) {
            event.setCancelled(true);



            for (Entity entity : player.getWorld().getNearbyEntities(player.getTargetBlock(null, 50).getLocation(),0.5,0.5,0.5)) {
                Bukkit.broadcastMessage(entity.getType().toString());
                if (entity instanceof LeashHitch) {
                    entity.remove();

                }
            }


        }
    }


}
