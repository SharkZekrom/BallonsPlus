package be.shark_zekrom.utils;

import be.shark_zekrom.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SummonBalloons {

    public static HashMap<Player, String> playerBalloons = new HashMap<>();
    public static HashMap<Player, Parrot> balloons = new HashMap<>();
    public static HashMap<Player, ArmorStand> as = new HashMap<>();
    public static HashMap<Player, Double> percentage = new HashMap<>();
    public static HashMap<Player, BukkitRunnable> deflateTask = new HashMap<>();


    public static void summonBalloon(Player player, ItemStack item, Double percentageBalloon) {
        Parrot parrot  = (Parrot) player.getWorld().spawnEntity(player.getLocation().add(0,2,0), EntityType.PARROT);
        parrot.setInvisible(true);
        parrot.setSilent(true);
        parrot.addScoreboardTag("Balloons+");
        parrot.setInvulnerable(true);


        balloons.put(player, parrot);
        parrot.setLeashHolder(player);

        Location loc = player.getLocation().add(0,2,0);
        ArmorStand as1 = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as1.addScoreboardTag("Balloons+");
        as1.setVisible(false);
        as1.setGravity(false);
        as1.setCanPickupItems(false);
        as1.setArms(true);
        as1.setBasePlate(false);
        as1.setInvulnerable(true);
        as1.getEquipment().setHelmet(item);
        as1.addEquipmentLock(EquipmentSlot.HEAD, ArmorStand.LockType.ADDING_OR_CHANGING);
        as1.addEquipmentLock(EquipmentSlot.CHEST, ArmorStand.LockType.ADDING_OR_CHANGING);
        as1.addEquipmentLock(EquipmentSlot.LEGS, ArmorStand.LockType.ADDING_OR_CHANGING);
        as1.addEquipmentLock(EquipmentSlot.FEET, ArmorStand.LockType.ADDING_OR_CHANGING);
        as1.addEquipmentLock(EquipmentSlot.HAND, ArmorStand.LockType.ADDING_OR_CHANGING);
        as1.addEquipmentLock(EquipmentSlot.OFF_HAND, ArmorStand.LockType.ADDING_OR_CHANGING);

        as.put(player, as1);

        if (!Main.BalloonDoesNotDeflate) {
            as1.setCustomNameVisible(true);
            percentage.put(player, percentageBalloon);
            BukkitRunnable task = new BukkitRunnable() {
                @Override
                public void run() {
                    if (percentage.get(player) > 0.0) {
                        double remainingPourcentage = percentage.get(player) - (Main.NumberOfPercentageLostByHour / 60 / 60);
                        as1.setCustomName("§c" + Math.round(remainingPourcentage) + "%");
                        percentage.put(player,remainingPourcentage);
                    } else {
                        percentage.put(player,0.0);

                        removeBalloonWithGiveItem(player);
                        this.cancel();
                    }
                }
            };
            task.runTaskTimer(Main.getInstance(), 0, 20);
            deflateTask.put(player, task);
        }

    }
    public static void removeBalloonWithGiveItem(Player player) {
        ArmorStand as = SummonBalloons.as.get(player);

        if (Main.BalloonWithItemInInventory) {
            percentage.putIfAbsent(player, 100.0);

            ItemStack item = as.getEquipment().getHelmet();
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName("§eBalloons+ : " + playerBalloons.get(player));
            meta.setLore(new ArrayList<>(){{
                add("§6Percentage : " + percentage.get(player) + "%");
            }});
            item.setItemMeta(meta);
            player.getInventory().addItem(item);

        }

        removeBalloon(player);
    }

    public static void removeBalloon(Player player) {

        ArmorStand as = SummonBalloons.as.get(player);


        if (Main.getInstance().getConfig().getBoolean("ShowParticlesBalloonsOnRemove")) {
            as.getWorld().spawnParticle(Particle.CLOUD, as.getLocation().add(0, 2, 0), 5, 0.1, 0.1, 0.1, 0.1);
        }
        SummonBalloons.as.remove(player);
        as.remove();

        Parrot parrot = SummonBalloons.balloons.get(player);
        SummonBalloons.balloons.remove(player);
        parrot.remove();

        if (!Main.BalloonDoesNotDeflate) {
            deflateTask.get(player).cancel();
            deflateTask.remove(player);
        }
    }

    public static void removeAllBalloon() {
        for (Player player : SummonBalloons.balloons.keySet()) {
            removeBalloonWithGiveItem(player);
        }

        for (Parrot parrot : SummonBalloons.balloons.values()) {
            parrot.remove();
        }
        for (ArmorStand as : SummonBalloons.as.values()) {
            if (Main.getInstance().getConfig().getBoolean("ShowParticlesBalloonsOnRemove")) {
                as.getWorld().spawnParticle(Particle.CLOUD, as.getLocation().add(0, 2, 0), 5, 0.1, 0.1, 0.1, 0.1);
            }
            as.remove();
        }

    }
}
