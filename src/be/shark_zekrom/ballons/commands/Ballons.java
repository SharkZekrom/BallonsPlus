package be.shark_zekrom.ballons.commands;

import be.shark_zekrom.ballons.Main;
import be.shark_zekrom.ballons.utils.GetSkull;
import be.shark_zekrom.ballons.utils.SummonBallons;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class Ballons implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command cmd, String string, String[] args) {

        Player player = (Player) commandSender;
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("off")) {
                if (SummonBallons.balloons.containsKey(player)) {
                    SummonBallons.removeBalloon(player);
                }else  {
                    player.sendMessage("You don't have a balloon enabled ");
                }
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
                            player.sendMessage("No permission");
                        }
                    } else {
                        SummonBallons.removeBalloon(player);
                        SummonBallons.summonBalloon(player, GetSkull.createSkull(config.getString(args[1] + ".head")));
                    }
                } else {
                    player.sendMessage("Balloon doesn't exist");
                }
            } else {
                player.sendMessage("§b==========[Ballons+]==========");
                player.sendMessage(ChatColor.AQUA + "");
                player.sendMessage(ChatColor.AQUA + "/ballons+ spawn <name>");
                player.sendMessage(ChatColor.AQUA + "/ballons+ off");


            }
        }
        return true;
    }


}
