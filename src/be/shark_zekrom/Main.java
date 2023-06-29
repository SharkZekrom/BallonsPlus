package be.shark_zekrom;

import be.shark_zekrom.utils.Distance;
import be.shark_zekrom.utils.Skulls;
import be.shark_zekrom.utils.SummonBalloons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public class Main extends JavaPlugin {


    private static Main instance;

    public static Main getInstance() {
        return instance;
    }

    public static boolean showOnlyBallonsWithPermission = false;
    public static boolean BalloonDoesNotDeflate = true;
    public static boolean BalloonWithItemInInventory = false;
    public static double NumberOfPercentageLostByHour = 0;
    public static double NumberOfPourcentageInflateByHour = 0;
    public static String prefix;


    @Override
    public void onEnable() {
        instance = this;
        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new Listener(), this);
        pm.registerEvents(new Menu(), this);
        pm.registerEvents(new RecipesGui(), this);

        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Parrot) {
                    Parrot parrot = (Parrot) entity;
                    if (parrot.getScoreboardTags().contains("Balloons+")) {
                        parrot.remove();
                    }
                }
                if (entity instanceof ArmorStand) {
                    ArmorStand armorStand = (ArmorStand) entity;
                    if (armorStand.getScoreboardTags().contains("Balloons+")) {
                        armorStand.remove();
                    }
                }

            }
        }
        this.getCommand("balloons+").setExecutor(new Commands());
        this.getCommand("balloons+").setTabCompleter(new Commands());

        new BukkitRunnable() {
            public void run() {
                for (Player player : SummonBalloons.balloons.keySet()) {
                    Parrot parrot = SummonBalloons.balloons.get(player);

                    if (parrot.getLocation().distance(player.getLocation()) < 6D) {
                        if ((parrot).isLeashed()) {
                            Distance.line(parrot, (parrot).getLeashHolder());
                        }

                    } else {
                        SummonBalloons.removeBalloon(player);

                        if (Main.getInstance().getConfig().getString("Balloons." + SummonBalloons.playerBalloons.get(player) + ".item") != null) {
                            ItemStack itemStack = new ItemStack(Material.valueOf(Main.getInstance().getConfig().getString("Balloons." + SummonBalloons.playerBalloons.get(player) + ".item")));
                            ItemMeta itemMeta = itemStack.getItemMeta();
                            itemMeta.setCustomModelData(Main.getInstance().getConfig().getInt("Balloons." + SummonBalloons.playerBalloons.get(player) + ".custommodeldata"));
                            itemStack.setItemMeta(itemMeta);
                            SummonBalloons.summonBalloon(player, itemStack, SummonBalloons.percentage.get(player));

                        } else {
                            SummonBalloons.summonBalloon(player, Skulls.createSkull(Main.getInstance().getConfig().getString("Balloons." + SummonBalloons.playerBalloons.get(player) + ".head")), SummonBalloons.percentage.get(player));

                        }
                    }
                }
            }
        }.runTaskTimer(Main.getInstance(), 0L, 2L);


        FileConfiguration config = getConfig();

        config.addDefault("ShowOnlyBalloonsWithPermission", false);
        config.addDefault("ShowParticlesBalloonsOnRemove", true);
        config.addDefault("BalloonDoesNotDeflate", true);
        config.addDefault("BalloonWithItemInInventory", false);
        config.addDefault("NumberOfPourcentageLostByHour", 1.0);
        config.addDefault("NumberOfPourcentageInflateByHour", 1.0);
        config.addDefault("BalloonPrefix", "§b[Balloons+] ");
        config.addDefault("BalloonReload", "§bSuccessfully reloaded!");
        config.addDefault("NoBalloonsFound", "§bNo balloons found with this name.");
        config.addDefault("NoBalloonsPermission", "§bYou do not have permission to use this balloons.");
        config.addDefault("BalloonsRemoved", "§bBalloons removed.");
        config.addDefault("BalloonsSummoned", "§bBalloons summoned.");
        config.addDefault("BalloonsMenuName", "Balloons");
        config.addDefault("BalloonsMenuPrevious", "§7« §ePrevious");
        config.addDefault("BalloonsMenuNext", "§7« §eNext");
        config.addDefault("BalloonsMenuRemove", "§cRemove");
        config.addDefault("BalloonsMenuClickToSummon", "§6» §eClick to summon");
        config.addDefault("BalloonsMenuNoPermissionToSummon", "§cNo permission to summon");
        config.addDefault("CantOpenInventory", "§bYou can't open the inventory inside a vehicle.");
        config.addDefault("BalloonsMenuEditing", "Balloon editing");
        config.addDefault("BalloonsMenuCreate", "Balloon creating");
        config.addDefault("BalloonsMenuPermission", "§ePermission");
        config.addDefault("BalloonsEnterPermission", "§bEnter permission.");
        config.addDefault("BalloonsPermissionSet", "§bPermission set.");
        config.addDefault("BalloonsEnterPermissionTimeOut", "§cTime out.");
        config.addDefault("BalloonsMenuEditPermission", "§7Click to edit the permission of this balloon");
        config.addDefault("BalloonsMenuCurrentPermission", "§7Current: ");
        config.addDefault("BalloonsMenuSave", "§aSave");
        config.addDefault("BalloonRemovedForPlayer", "§cThe balloon has been removed.");
        config.addDefault("BalloonSetForPlayer", "§eThe balloon has been set.");
        if (config.get("Balloons") == null) {
            config.set("Balloons.head.permission", "Ballons+.shark_zekrom");
            config.set("Balloons.head.displayname", "§eshark_zekrom");
            config.set("Balloons.head.head", "ewogICJ0aW1lc3RhbXAiIDogMTYyNzA1NDA1Mjg5MCwKICAicHJvZmlsZUlkIiA6ICIzMzNhMjQ3ODk3MTE0MDA2YTE3ZDFmOTU4ZjhkMDZmMSIsCiAgInByb2ZpbGVOYW1lIiA6ICJzaGFya196ZWtyb20iLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDBjNzAyODQyZTc0MDM4ODA0YzYzNDUwZTU4YzI4ZTgwOGJjNmFiY2I1M2EwZjI0NTRjN2FkMmRkMDUwNmFhMyIKICAgIH0KICB9Cn0=");

            config.set("Balloons.item.permission", "Ballons+.item");
            config.set("Balloons.item.item", "DIAMOND_HOE");
            config.set("Balloons.item.custommodeldata", 1);
            config.set("Balloons.item.displayname", "§eitem");

        }
        config.addDefault("Balloon.Recipes", null);


        config.options().copyDefaults(true);
        saveConfig();


        ConfigurationSection cs = config.getConfigurationSection("Balloons");
        Menu.list.addAll(cs.getKeys(false));

        BalloonDoesNotDeflate = config.getBoolean("BalloonDoesNotDeflate");
        BalloonWithItemInInventory = config.getBoolean("BalloonWithItemInInventory");
        showOnlyBallonsWithPermission = config.getBoolean("ShowOnlyBalloonsWithPermission");
        NumberOfPercentageLostByHour = config.getInt("NumberOfPourcentageLostByHour");
        NumberOfPourcentageInflateByHour = config.getInt("NumberOfPourcentageInflateByHour");
        prefix = config.getString("BalloonPrefix");

        Bukkit.getLogger().info("Balloons+ enabled !");
        Recipes.loadRecipes();

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    for (int i = 0; i < 36; i++) {
                        ItemStack itemStack = player.getInventory().getItem(i);
                        if (itemStack != null) {
                            if (itemStack.hasItemMeta()) {
                                if (itemStack.getItemMeta().hasDisplayName()) {
                                    if (itemStack.getItemMeta().getDisplayName().contains("§eBalloons+ : ")) {
                                        String percentage = itemStack.getItemMeta().getLore().get(0).split(" : ")[1].replace("%", "");
                                        double newPercentage = Double.parseDouble(percentage) + (NumberOfPourcentageInflateByHour / 60 / 60);
                                        if (newPercentage > 100) {
                                            newPercentage = 100;
                                        }

                                        ItemStack clone = itemStack.clone();
                                        ItemMeta cloneMeta = clone.getItemMeta();
                                        cloneMeta.setLore(Arrays.asList("§6Percentage : " + newPercentage + "%"));
                                        clone.setItemMeta(cloneMeta);
                                        player.getInventory().setItem(i, clone);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }.runTaskTimer(getInstance(), 0, 20);
    }

    @Override
    public void onDisable() {
        SummonBalloons.removeAllBalloon();
        Bukkit.getLogger().info("Balloons+ disabled !");

    }
}