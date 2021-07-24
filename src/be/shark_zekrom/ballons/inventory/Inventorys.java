package be.shark_zekrom.ballons.inventory;

import be.shark_zekrom.ballons.Main;
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
        player.sendMessage("loop" + loop);
        player.sendMessage("ballons" + ballons);
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

            ItemStack item = new ItemStack(Material.ARROW);
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
                player.sendMessage(String.valueOf(inv.getItem(43)));
                if (inv.getItem(43) != null) {
                    String[] number = inv.getItem(43).getItemMeta().getDisplayName().split(" :");
                    if (Integer.parseInt(number[0]) >= 29) {
                        ItemStack previous = new ItemStack(Material.ARROW);
                        ItemMeta previousmeta = previous.getItemMeta();
                        previousmeta.setDisplayName("test");
                        previous.setItemMeta(previousmeta);
                        inv.setItem(45, previous);
                    }
                }
                ItemStack next = new ItemStack(Material.ARROW);
                ItemMeta nextmeta = next.getItemMeta();
                nextmeta.setDisplayName("test");
                next.setItemMeta(nextmeta);
                inv.setItem(53, next);
                return;
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
            if (slot == 45) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(10).getItemMeta().getDisplayName().split(" :");
                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, Integer.parseInt(number[0]), Integer.parseInt(number[0]) -29, list);
            }
            if (slot == 53) {
                player.closeInventory();

                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                String[] number = inv.getItem(43).getItemMeta().getDisplayName().split(" :");

                ArrayList<String> list = new ArrayList<>();
                for (String key : config.getKeys(false)) { // For each company key in the set
                    list.add(key);
                }
                inventory(player, Integer.parseInt(number[0]) , Integer.parseInt(number[0]), list);
            }
        }
    }
}
