package be.shark_zekrom.ballons;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

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

      //  pm.registerEvents(new Manager(), this);

        //this.getCommand("familier+").setExecutor(new Commands());
    }

    @Override
    public void onDisable() {

    }
}
