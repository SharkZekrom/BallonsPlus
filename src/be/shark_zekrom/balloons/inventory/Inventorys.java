package be.shark_zekrom.balloons.inventory;

import be.shark_zekrom.balloons.Main;
import be.shark_zekrom.balloons.utils.GetSkull;
import be.shark_zekrom.balloons.utils.SlotMenu;
import be.shark_zekrom.balloons.utils.SummonBallons;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
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
import java.util.HashMap;

public class Inventorys implements Listener {

    public static HashMap<Player, Integer> pages = new HashMap<>();

    public static void inventory(Player player, int ballons, int loop, ArrayList<String> list) {
        pages.put(player, loop);
        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Inventory inventory = Bukkit.createInventory(null, 54, "Ballons");
        player.openInventory(inventory);

        ItemStack remove = new ItemStack(Material.BARRIER);
        ItemMeta removemeta = remove.getItemMeta();
        removemeta.setDisplayName(ChatColor.RED + "Remove");
        remove.setItemMeta(removemeta);
        inventory.setItem(49, remove);

        if (pages.get(player) > 0) {
            ItemStack previous = new ItemStack(Material.ARROW);
            ItemMeta previousmeta = previous.getItemMeta();
            previousmeta.setDisplayName(ChatColor.DARK_GRAY + "« " + ChatColor.YELLOW + "Previous");
            previous.setItemMeta(previousmeta);
            inventory.setItem(48, previous);
        }


        int slot = 0;
        for (int i = 0; i < 45; i++) {
            if (list.size() > i + loop) {

                ItemStack item = new ItemStack(GetSkull.createSkull(config.getString("Balloons." + list.get(i + loop) + ".head")));
                ItemMeta itemmeta = item.getItemMeta();
                itemmeta.setDisplayName(i + loop + 1 + " : " + list.get(i + loop));
                item.setItemMeta(itemmeta);
                inventory.setItem(slot, item);
                slot++;

                if (slot == 45) {
                    if (slot != ballons) {
                        ItemStack next = new ItemStack(Material.ARROW);
                        ItemMeta nextmeta = next.getItemMeta();
                        nextmeta.setDisplayName(ChatColor.DARK_GRAY + "» " + ChatColor.YELLOW + "Next");
                        next.setItemMeta(nextmeta);
                        inventory.setItem(50, next);
                    }
                    return;
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


            if (event.getCurrentItem() != null) {


                if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String[] number = inv.getItem(slot).getItemMeta().getDisplayName().split(" : ");
                    if (SummonBallons.balloons.containsKey(player)) {
                        SummonBallons.removeBalloon(player);
                    }
                    SummonBallons.summonBalloon(player, GetSkull.createSkull(config.getString("Balloons." + number[1] + ".head")));
                    player.closeInventory();
                }


                if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                    player.closeInventory();
                    SummonBallons.removeBalloon(player);
                }

                if (slot == 48) {
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    int ballons = 0;
                    ArrayList<String> list = new ArrayList<>();
                    ConfigurationSection cs = config.getConfigurationSection("Balloons");
                    for (String key : cs.getKeys(false)) {
                        list.add(key);
                        ballons++;
                    }
                    inventory(player, ballons , pages.get(player) - 45, list);
                }
                if (slot == 50) {
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    int ballons = 0;
                    ArrayList<String> list = new ArrayList<>();
                    ConfigurationSection cs = config.getConfigurationSection("Balloons");
                    for (String key : cs.getKeys(false)) {
                        list.add(key);
                        ballons++;
                    }
                    inventory(player, ballons , pages.get(player) + 45, list);
                }
            }
        }
    }
}
