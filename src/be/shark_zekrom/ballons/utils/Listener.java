package be.shark_zekrom.ballons.utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;

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
            Parrot parrot = SummonBallons.balloons.get(player);
            parrot.teleport(player);
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

}
