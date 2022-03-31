package be.shark_zekrom.balloons.commands;

import be.shark_zekrom.balloons.Main;
import be.shark_zekrom.balloons.inventory.Inventorys;
import be.shark_zekrom.balloons.utils.GetSkull;
import be.shark_zekrom.balloons.utils.SummonBallons;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;

public class Balloons implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String string, String[] args) {

        Player player = (Player) commandSender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("remove")) {
                if (SummonBallons.balloons.containsKey(player)) {
                    SummonBallons.removeBalloon(player);
                }
                if (args[0].equalsIgnoreCase("inventory")) {
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    int ballons = 0;
                    ArrayList<String> list = new ArrayList<>();
                    for (String key : config.getKeys(false)) { // For each company key in the set
                        list.add(key);
                        ballons++;
                    }
                    Inventorys.inventory(player, ballons, 0, list);

                }
            } else if (args.length > 1) {

                if (args[0].equalsIgnoreCase("spawn")) {
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
                    if (config.getString(args[1]) != null) {

                        String permission = config.getString(args[1] + ".permission");
                        if (permission != null) {
                            if (commandSender.hasPermission(permission)) {
                                SummonBallons.removeBalloon(player);
                                SummonBallons.summonBalloon(player, GetSkull.createSkull(config.getString(args[1] + ".head")));
                            } else {
                                player.sendMessage("§b[Balloons+] " + Main.getInstance().getConfig().getString("NoBalloonsPermission"));
                            }
                        } else {
                            SummonBallons.removeBalloon(player);
                            SummonBallons.summonBalloon(player, GetSkull.createSkull(config.getString(args[1] + ".head")));
                            player.sendMessage("§b[Balloons+] " + Main.getInstance().getConfig().getString("BalloonsSummoned"));

                        }
                    } else {
                        player.sendMessage("§b[Balloons+] " + Main.getInstance().getConfig().getString("NoBalloonsFound"));
                    }
                }


            } else {
                player.sendMessage("§b==========[Balloons+]==========");
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.AQUA + "/balloons+ inventory");
                player.sendMessage(ChatColor.AQUA + "/balloons+ spawn <name>");
                player.sendMessage(ChatColor.AQUA + "/balloons+ remove");
            }
        }
        return false;
    }

}
