package be.shark_zekrom.balloons.inventory;

import be.shark_zekrom.balloons.Main;
import be.shark_zekrom.balloons.utils.GetSkull;
import be.shark_zekrom.balloons.utils.SlotMenu;
import be.shark_zekrom.balloons.utils.SummonBallons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;

public class Inventorys implements Listener {


    public static void inventory(Player player, int ballons, int loop, ArrayList<String> list) {
        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        Inventory inv = null;
        if (ballons <= 7) {

            inv = Bukkit.createInventory(null, 27, "Ballons");
            player.openInventory(inv);
        }

        else if (ballons <= 14) {

            inv = Bukkit.createInventory(null, 36, "Ballons");
            player.openInventory(inv);
        }
        else if (ballons <= 21) {

            inv = Bukkit.createInventory(null, 45, "Ballons");
            player.openInventory(inv);
        }
        else {

            inv = Bukkit.createInventory(null, 54, "Ballons");
            player.openInventory(inv);
        }


        int slot = 10;
        for (int i = 0; i < ballons; i++) {

            ItemStack item = new ItemStack(GetSkull.createSkull(config.getString(list.get(i + loop) + ".head")));
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(i + loop + 1 + " : " + list.get(i + loop));
            item.setItemMeta(itemmeta);
            inv.setItem(slot, item);
            slot++;
            if (slot == 17) {
                slot = 19;
            }
            if (slot == 26) {
                slot = 28;
            }
            if (slot == 35) {
                slot = 37;
            }

            if (slot == 44) {
                String[] number = inv.getItem(10).getItemMeta().getDisplayName().split(" : ");
                if (Integer.parseInt(number[0]) == 1) {
                    ItemStack previous = new ItemStack(Material.ORANGE_STAINED_GLASS_PANE);
                    inv.setItem(45, previous);
                }
                else {
                    if (player.getOpenInventory().getTopInventory().getSize() == 27) {
                        SlotMenu.slot26(inv);

                        ItemStack previous = new ItemStack(Material.ARROW);
                        ItemMeta previousmeta = previous.getItemMeta();
                        previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");
                        previous.setItemMeta(previousmeta);
                        inv.setItem(18, previous);

                        ItemStack remove = new ItemStack(Material.BARRIER);
                        ItemMeta removemeta = remove.getItemMeta();
                        removemeta.setDisplayName(ChatColor.RED + "Remove");
                        remove.setItemMeta(removemeta);
                        inv.setItem(22, remove);

                    }
                    if (player.getOpenInventory().getTopInventory().getSize() == 36) {
                        SlotMenu.slot35(inv);

                        ItemStack previous = new ItemStack(Material.ARROW);
                        ItemMeta previousmeta = previous.getItemMeta();
                        previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");
                        previous.setItemMeta(previousmeta);
                        inv.setItem(27, previous);

                        ItemStack remove = new ItemStack(Material.BARRIER);
                        ItemMeta removemeta = remove.getItemMeta();
                        removemeta.setDisplayName(ChatColor.RED + "Remove");
                        remove.setItemMeta(removemeta);
                        inv.setItem(31, remove);
                    }
                    if (player.getOpenInventory().getTopInventory().getSize() == 45) {
                        SlotMenu.slot44(inv);

                        ItemStack previous = new ItemStack(Material.ARROW);
                        ItemMeta previousmeta = previous.getItemMeta();
                        previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");
                        previous.setItemMeta(previousmeta);
                        inv.setItem(36, previous);

                        ItemStack remove = new ItemStack(Material.BARRIER);
                        ItemMeta removemeta = remove.getItemMeta();
                        removemeta.setDisplayName(ChatColor.RED + "Remove");
                        remove.setItemMeta(removemeta);
                        inv.setItem(40, remove);

                    }
                    if (player.getOpenInventory().getTopInventory().getSize() == 54) {
                        SlotMenu.slot53(inv);

                        ItemStack previous = new ItemStack(Material.ARROW);
                        ItemMeta previousmeta = previous.getItemMeta();
                        previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");
                        previous.setItemMeta(previousmeta);
                        inv.setItem(45, previous);

                        ItemStack remove = new ItemStack(Material.BARRIER);
                        ItemMeta removemeta = remove.getItemMeta();
                        removemeta.setDisplayName(ChatColor.RED + "Remove");
                        remove.setItemMeta(removemeta);
                        inv.setItem(49, remove);

                    }
                }
                ItemStack next = new ItemStack(Material.ARROW);
                ItemMeta nextmeta = next.getItemMeta();
                nextmeta.setDisplayName(ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Next");
                next.setItemMeta(nextmeta);
                inv.setItem(53, next);
                return;
            }
            else {
                if (player.getOpenInventory().getTopInventory().getSize() == 27) {
                    SlotMenu.slot26(inv);
                    ItemStack previous = new ItemStack(Material.ARROW);
                    ItemMeta previousmeta = previous.getItemMeta();
                    previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");                    previous.setItemMeta(previousmeta);
                    inv.setItem(18, previous);


                    ItemStack remove = new ItemStack(Material.BARRIER);
                    ItemMeta removemeta = remove.getItemMeta();
                    removemeta.setDisplayName(ChatColor.RED + "Remove");
                    remove.setItemMeta(removemeta);
                    inv.setItem(22, remove);
                }
                if (player.getOpenInventory().getTopInventory().getSize() == 36) {
                    SlotMenu.slot35(inv);

                    ItemStack previous = new ItemStack(Material.ARROW);
                    ItemMeta previousmeta = previous.getItemMeta();
                    previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");                    previous.setItemMeta(previousmeta);
                    inv.setItem(27, previous);

                    ItemStack remove = new ItemStack(Material.BARRIER);
                    ItemMeta removemeta = remove.getItemMeta();
                    removemeta.setDisplayName(ChatColor.RED + "Remove");
                    remove.setItemMeta(removemeta);
                    inv.setItem(31, remove); }
                if (player.getOpenInventory().getTopInventory().getSize() == 45) {
                    SlotMenu.slot44(inv);

                    ItemStack previous = new ItemStack(Material.ARROW);
                    ItemMeta previousmeta = previous.getItemMeta();
                    previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");                    previous.setItemMeta(previousmeta);
                    inv.setItem(36, previous);

                    ItemStack remove = new ItemStack(Material.BARRIER);
                    ItemMeta removemeta = remove.getItemMeta();
                    removemeta.setDisplayName(ChatColor.RED + "Remove");
                    remove.setItemMeta(removemeta);
                    inv.setItem(40, remove);
                }
                if (player.getOpenInventory().getTopInventory().getSize() == 54) {
                    SlotMenu.slot53(inv);

                    ItemStack previous = new ItemStack(Material.ARROW);
                    ItemMeta previousmeta = previous.getItemMeta();
                    previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");                    previous.setItemMeta(previousmeta);
                    inv.setItem(45, previous);

                    ItemStack remove = new ItemStack(Material.BARRIER);
                    ItemMeta removemeta = remove.getItemMeta();
                    removemeta.setDisplayName(ChatColor.RED + "Remove");
                    remove.setItemMeta(removemeta);
                    inv.setItem(49, remove);
                }
            }
        }
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Inventory inv = event.getInventory();
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();
        if (event.getView().getTitle().equalsIgnoreCase("Ballons")) {
            event.setCancelled(true);



            if (event.getClickedInventory().getItem(slot).getType().equals(Material.PLAYER_HEAD)) {
                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(slot).getItemMeta().getDisplayName().split(" : ");
                SummonBallons.removeBalloon(player);
                SummonBallons.summonBalloon(player,GetSkull.createSkull(config.getString(number[1] + ".head")));
                player.closeInventory();
            }



            if (slot == 18 && event.getClickedInventory().getItem(18).getType().equals(Material.ARROW)) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(10).getItemMeta().getDisplayName().split(" : ");
                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, Integer.parseInt(number[0]), Integer.parseInt(number[0]) -29, list);
            }
            if (slot == 27 && event.getClickedInventory().getItem(27).getType().equals(Material.ARROW)) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(10).getItemMeta().getDisplayName().split(" : ");
                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, Integer.parseInt(number[0]), Integer.parseInt(number[0]) -29, list);
            }
            if (slot == 36 && event.getClickedInventory().getItem(36).getType().equals(Material.ARROW)) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(10).getItemMeta().getDisplayName().split(" : ");
                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, Integer.parseInt(number[0]), Integer.parseInt(number[0]) -29, list);
            }
            if (slot == 45 && event.getClickedInventory().getItem(45).getType().equals(Material.ARROW)) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(10).getItemMeta().getDisplayName().split(" : ");
                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, Integer.parseInt(number[0]), Integer.parseInt(number[0]) -29, list);
            }



            if (slot == 22 && event.getClickedInventory().getItem(22).getType().equals(Material.BARRIER)) {
                player.closeInventory();

                SummonBallons.removeBalloon(player);
            }
            if (slot == 31 && event.getClickedInventory().getItem(31).getType().equals(Material.BARRIER)) {
                player.closeInventory();

                SummonBallons.removeBalloon(player);

            }
            if (slot == 40 && event.getClickedInventory().getItem(40).getType().equals(Material.BARRIER)) {
                player.closeInventory();
                SummonBallons.removeBalloon(player);

            }
            if (slot == 49 && event.getClickedInventory().getItem(49).getType().equals(Material.BARRIER)) {
                player.closeInventory();
                SummonBallons.removeBalloon(player);

            }










            if (slot == 53) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] numbers = inv.getItem(43).getItemMeta().getDisplayName().split(" : ");
                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, list.size() - Integer.parseInt(numbers[0]), Integer.parseInt(numbers[0]), list);
            }
        }
    }
}
