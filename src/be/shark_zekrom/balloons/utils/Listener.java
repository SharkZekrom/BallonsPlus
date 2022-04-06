package be.shark_zekrom.balloons.utils;

import be.shark_zekrom.balloons.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.PlayerLeashEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.event.player.PlayerUnleashEntityEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.spigotmc.event.entity.EntityDismountEvent;
import org.spigotmc.event.entity.EntityMountEvent;

public class Listener implements org.bukkit.event.Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        Entity entity = event.getEntity();
        if (SummonBalloons.balloons.containsValue(entity)) {
            event.setCancelled(true);
        }
    }


    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        Player player = event.getPlayer();

        if (SummonBalloons.balloons.containsKey(player)) {
            ArmorStand as = SummonBalloons.as.get(player);

            ItemStack item = as.getEquipment().getHelmet();

            SummonBalloons.as.remove(player);
            as.remove();

            Parrot parrot = SummonBalloons.balloons.get(player);
            SummonBalloons.balloons.remove(player);
            parrot.remove();

            new BukkitRunnable() {
                @Override
                public void run() {
                    SummonBalloons.summonBalloon(player, item);

                }
            }.runTaskLater(Main.getInstance(), 10L);

        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if (SummonBalloons.balloons.containsKey(player)) {
            SummonBalloons.removeBalloon(player);
        }
    }

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (SummonBalloons.balloons.containsKey(player)) {
            SummonBalloons.removeBalloon(player);
        }
    }


    @EventHandler
    public void onLeash(PlayerLeashEntityEvent event) {
        Player player = event.getPlayer();
        if (SummonBalloons.balloons.containsKey(player)) {
            event.setCancelled(true);
            for (Entity entity : player.getWorld().getNearbyEntities(player.getTargetBlock(null, 50).getLocation(), 0.5, 0.5, 0.5)) {
                Bukkit.broadcastMessage(entity.getType().toString());
                if (entity instanceof LeashHitch) {
                    entity.remove();

                }
            }
        }
    }

    @EventHandler
    public void onUnLeash(PlayerUnleashEntityEvent event) {
        Player player = event.getPlayer();
        if (event.getEntity() instanceof Parrot) {
            Parrot parrot = (Parrot) event.getEntity();
            if (SummonBalloons.balloons.containsValue(parrot)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) entity;
            if (SummonBalloons.as.containsValue(as)) {
                event.setCancelled(true);
            }
        }
    }


    @EventHandler
    public void onMount(EntityMountEvent event) {

        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (SummonBalloons.balloons.containsKey(player)) {
                SummonBalloons.removeBalloon(player);

            }
        }
    }

    @EventHandler
    public void onDismount(EntityDismountEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (SummonBalloons.playerBalloons.containsKey(player)) {
                SummonBalloons.summonBalloon(player, GetSkull.createSkull(Main.getInstance().getConfig().getString("Balloons." + SummonBalloons.playerBalloons.get(player) + ".head")));
            }
        }
    }



}

