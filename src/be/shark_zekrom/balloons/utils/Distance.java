package be.shark_zekrom.balloons.utils;

import org.bukkit.entity.*;
import org.bukkit.util.Vector;

public class Distance {


    public static void line(Entity entity, Entity player) {
        if (entity.getWorld().equals(player.getWorld())) {

            if (entity.getLocation().distance(player.getLocation()) > 3.8D) {
                Vector direction = player.getLocation().toVector().subtract(entity.getLocation().toVector())
                        .normalize();
                entity.setVelocity(entity.getVelocity().add(direction.multiply(0.4D)));
            }
            if (entity.getLocation().distance(player.getLocation()) < 3.0D) {
                entity.setVelocity(entity.getVelocity().add(new Vector(0.0D, 0.3D, 0.0D)));

            }
            //   if (entity.getLocation().getY() > player.getLocation().getY() + 4.0D) {
            //        entity.setVelocity(entity.getVelocity().add(new Vector(0.0D, -0.2D, 0.0D)));

            //    }
            entity.getLocation().setDirection(player.getLocation().getDirection());

            ArmorStand as = SummonBallons.as.get(player);
            as.teleport(entity.getLocation().add(0, -1.3, 0));
            as.getLocation().setDirection(player.getLocation().getDirection());
        } else {
            entity.teleport(player);
        }
        if (entity.getLocation().distance(player.getLocation()) > 10.0D) {
            entity.teleport(player);
        }

    }

}
