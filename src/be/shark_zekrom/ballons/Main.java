package be.shark_zekrom.ballons;

import be.shark_zekrom.ballons.commands.Ballons;
import be.shark_zekrom.ballons.inventory.Inventorys;
import be.shark_zekrom.ballons.utils.Distance;
import be.shark_zekrom.ballons.utils.Listener;
import be.shark_zekrom.ballons.utils.SummonBallons;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class Main extends JavaPlugin {

    private static Main instance;
    public static Main getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        Bukkit.getLogger().info("Ballons+ enabled !");
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Listener(), this);
        pm.registerEvents(new Inventorys(), this);

        this.getCommand("ballons+").setExecutor(new Ballons());

        new BukkitRunnable() {
            public void run() {
                for (Parrot parrot : SummonBallons.balloons.values()) {
                   Distance.line(parrot, (parrot).getLeashHolder());
                  parrot.addPotionEffect(new PotionEffect(PotionEffectType.LEVITATION, 10, 0));

                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);


        FileConfiguration config = getConfig();
        config.addDefault("test.permission", "Ballons+.test");
        config.addDefault("test.head", "ewogICJ0aW1lc3RhbXAiIDogMTYyNzA1NDA1Mjg5MCwKICAicHJvZmlsZUlkIiA6ICIzMzNhMjQ3ODk3MTE0MDA2YTE3ZDFmOTU4ZjhkMDZmMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGFya196ZWtyb20iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBjNzAyODQyZTc0MDM4ODA0YzYzNDUwZTU4YzI4ZTgwOGJjNmFiY2I1M2EwZjI0NTRjN2FkMmRkMDUwNmFhMyIKICAgIH0KICB9Cn0=");
        config.options().copyDefaults(true);
        saveConfig();

    }

    @Override
    public void onDisable() {
        SummonBallons.removeAllBalloon();
    }
}
