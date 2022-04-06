package be.shark_zekrom.balloons.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class SummonBalloons {

    public static HashMap<Player, String> playerBalloons = new HashMap<>();
    public static HashMap<Player, Parrot> balloons = new HashMap<>();
    public static HashMap<Player, ArmorStand> as = new HashMap<>();

    public static void summonBalloon(Player player, ItemStack item) {
        Parrot parrot  = (Parrot) player.getWorld().spawnEntity(player.getLocation().add(0,2,0), EntityType.PARROT);
        parrot.setInvisible(true);
        parrot.setSilent(true);

        balloons.put(player, parrot);
        parrot.setLeashHolder(player);

        Location loc = player.getLocation().add(0,2,0);
        ArmorStand as1 = (ArmorStand) loc.getWorld().spawnEntity(loc, EntityType.ARMOR_STAND);
        as1.setVisible(false);
        as1.setCustomNameVisible(false);
        as1.setGravity(false);
        as1.setCanPickupItems(false);
        as1.setArms(true);
        as1.setBasePlate(false);
        as1.getEquipment().setHelmet(item);
        as.put(player, as1);

    }

    public static void removeBalloon(Player player) {
        ArmorStand as = SummonBalloons.as.get(player);
        SummonBalloons.as.remove(player);
        as.remove();

        Parrot parrot = SummonBalloons.balloons.get(player);
        SummonBalloons.balloons.remove(player);
        parrot.remove();

    }

    public static void removeAllBalloon() {
        for (Parrot parrot : SummonBalloons.balloons.values()) {
            parrot.remove();
        }
        for (ArmorStand as : SummonBalloons.as.values()) {
            as.remove();
        }

    }
}
