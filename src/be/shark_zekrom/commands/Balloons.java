package be.shark_zekrom.commands;

import be.shark_zekrom.Main;
import be.shark_zekrom.inventory.Menu;
import be.shark_zekrom.utils.Skulls;
import be.shark_zekrom.utils.SummonBalloons;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Balloons implements CommandExecutor, TabExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String string, String[] args) {

        Player player = (Player) commandSender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                player.sendMessage("§b==========[Balloons+]==========");
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.AQUA + "/balloons+ help");
                player.sendMessage(ChatColor.AQUA + "/balloons+ reload");
                player.sendMessage(ChatColor.AQUA + "/balloons+ inventory");
                player.sendMessage(ChatColor.AQUA + "/balloons+ spawn <name>");
                player.sendMessage(ChatColor.AQUA + "/balloons+ remove");
            }
            if (args[0].equalsIgnoreCase("remove")) {
                if (SummonBalloons.balloons.containsKey(player)) {
                    SummonBalloons.removeBalloon(player);
                    SummonBalloons.playerBalloons.remove(player);

                    player.sendMessage(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("BalloonsRemoved"));

                }
            }
            if (args[0].equalsIgnoreCase("inventory")) {
                if (player.isInsideVehicle()) {
                    player.sendMessage(Main.getInstance().getConfig().getString("Prefix") + Main.getInstance().getConfig().getString("CantOpenInventory"));

                } else {
                    Menu.inventory(player, 0);
                }
            }
            if (args[0].equalsIgnoreCase("reload")) {
                if (player.hasPermission("balloons+.reload")) {
                    reload();
                    player.sendMessage(Main.prefix + Main.getInstance().getConfig().getString("BalloonReload"));
                } else {
                    player.sendMessage(Main.prefix + Main.getInstance().getConfig().getString("NoPermission"));
                }


            }
        } else if (args.length > 1) {

            if (args[0].equalsIgnoreCase("spawn")) {
                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                if (config.getString("Balloons." + args[1]) != null) {

                    String permission = config.getString("Balloons." + args[1] + ".permission");
                    if (commandSender.hasPermission(permission)) {
                        if (SummonBalloons.balloons.containsKey(player)) {
                            if (config.getString("Balloons." + args[1] + ".item") != null) {
                                ItemStack itemStack = new ItemStack(Material.valueOf(config.getString("Balloons." + args[1] + ".item")));
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.setCustomModelData(config.getInt("Balloons." + args[1] + ".custommodeldata"));
                                itemStack.setItemMeta(itemMeta);
                                SummonBalloons.as.get(player).getEquipment().setHelmet(itemStack);
                            } else {
                                SummonBalloons.as.get(player).getEquipment().setHelmet(Skulls.createSkull(config.getString("Balloons." + args[1] + ".head")));

                            }
                        } else {
                            if (config.getString("Balloons." + args[1] + ".item") != null) {
                                ItemStack itemStack = new ItemStack(Material.valueOf(config.getString("Balloons." + args[1] + ".item")));
                                ItemMeta itemMeta = itemStack.getItemMeta();
                                itemMeta.setCustomModelData(config.getInt("Balloons." + args[1] + ".custommodeldata"));
                                itemStack.setItemMeta(itemMeta);
                                SummonBalloons.summonBalloon(player, itemStack);

                            } else {
                                SummonBalloons.summonBalloon(player, Skulls.createSkull(config.getString("Balloons." + args[1] + ".head")));

                            }

                        }
                        SummonBalloons.playerBalloons.put(player, args[1]);
                        player.sendMessage("§b[Balloons+] " + Main.getInstance().getConfig().getString("BalloonsSummoned"));

                    } else {
                        player.sendMessage("§b[Balloons+] " + Main.getInstance().getConfig().getString("NoBalloonsPermission"));
                    }
                } else {
                    player.sendMessage("§b[Balloons+] " + Main.getInstance().getConfig().getString("NoBalloonsFound"));
                }
            }
        } else {
            player.sendMessage("§b==========[Balloons+]==========");
            player.sendMessage(ChatColor.AQUA + "");
            player.sendMessage(ChatColor.AQUA + "/balloons+ help");
            player.sendMessage(ChatColor.AQUA + "/balloons+ inventory");
            player.sendMessage(ChatColor.AQUA + "/balloons+ reload");
            player.sendMessage(ChatColor.AQUA + "/balloons+ spawn <name>");
            player.sendMessage(ChatColor.AQUA + "/balloons+ remove");
        }
        return false;
    }



    public static void reload() {
        try {
            Main.getInstance().getConfig().load(new File(Main.getInstance().getDataFolder(), "config.yml"));

            Menu.list.clear();
            ConfigurationSection cs = Main.getInstance().getConfig().getConfigurationSection("Balloons");
            Menu.list.addAll(cs.getKeys(false));

            Main.showOnlyBallonsWithPermission = Main.getInstance().getConfig().getBoolean("ShowOnlyBalloonsWithPermission");
            Main.prefix = Main.getInstance().getConfig().getString("BalloonPrefix");


        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }

    }
    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        List<String> arguments = new ArrayList<>();
        if (args.length == 1) {
            arguments.add("help");
            arguments.add("inventory");
            arguments.add("spawn");
            arguments.add("remove");
            if (sender.hasPermission("balloon+.*")){
                arguments.add("reload");
            }
        }
        return arguments;
    }

}