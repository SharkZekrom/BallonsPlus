package be.shark_zekrom.balloons;

import be.shark_zekrom.balloons.commands.Balloons;
import be.shark_zekrom.balloons.inventory.Inventorys;
import be.shark_zekrom.balloons.utils.Distance;
import be.shark_zekrom.balloons.utils.Listener;
import be.shark_zekrom.balloons.utils.SummonBallons;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Listener(), this);
        pm.registerEvents(new Inventorys(), this);

        this.getCommand("balloons+").setExecutor(new Balloons());

        new BukkitRunnable() {
            public void run() {
                for (Parrot parrot : SummonBallons.balloons.values()) {
                   Distance.line(parrot, (parrot).getLeashHolder());
               //   parrot.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 0,false,false));

                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);


        FileConfiguration config = getConfig();

        config.addDefault("NoBalloonsFound", "§bNo balloons found with this name.");
        config.addDefault("NoBalloonsPermission", "§bYou do not have permission to use this balloons.");
        config.addDefault("BalloonsRemoved", "§bBalloons removed.");
        config.addDefault("BalloonsSummoned", "§bBalloons summoned.");

        for (int i = 0; i < 100; i++) {

            config.addDefault("Balloons.shark_" + i +" .permission", "Ballons+.shark_" + i);
            config.addDefault("Balloons.shark_" + i +" .head", "ewogICJ0aW1lc3RhbXAiIDogMTYyNzA1NDA1Mjg5MCwKICAicHJvZmlsZUlkIiA6ICIzMzNhMjQ3ODk3MTE0MDA2YTE3ZDFmOTU4ZjhkMDZmMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGFya196ZWtyb20iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBjNzAyODQyZTc0MDM4ODA0YzYzNDUwZTU4YzI4ZTgwOGJjNmFiY2I1M2EwZjI0NTRjN2FkMmRkMDUwNmFhMyIKICAgIH0KICB9Cn0=");

        }
       // config.addDefault("Balloons.shark_zekrom.permission", "Ballons+.shark_zekrom");
        //config.addDefault("Balloons.shark_zekrom.head", "ewogICJ0aW1lc3RhbXAiIDogMTYyNzA1NDA1Mjg5MCwKICAicHJvZmlsZUlkIiA6ICIzMzNhMjQ3ODk3MTE0MDA2YTE3ZDFmOTU4ZjhkMDZmMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGFya196ZWtyb20iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBjNzAyODQyZTc0MDM4ODA0YzYzNDUwZTU4YzI4ZTgwOGJjNmFiY2I1M2EwZjI0NTRjN2FkMmRkMDUwNmFhMyIKICAgIH0KICB9Cn0=");

     config.options().copyDefaults(true);
        saveConfig();

        Bukkit.getLogger().info("Balloons+ enabled !");

    }

    @Override
    public void onDisable() {
        SummonBallons.removeAllBalloon();
        Bukkit.getLogger().info("Balloons+ disabled !");

    }
}
