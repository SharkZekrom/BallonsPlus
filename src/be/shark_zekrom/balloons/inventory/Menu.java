package be.shark_zekrom.balloons.inventory;

import be.shark_zekrom.balloons.Main;
import be.shark_zekrom.balloons.utils.GetSkull;
import be.shark_zekrom.balloons.utils.InventoryItems;
import be.shark_zekrom.balloons.utils.SummonBalloons;
import org.bukkit.Bukkit;
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

public class Menu implements Listener {

    public static HashMap<Player, Integer> pages = new HashMap<>();
    public static ArrayList<String> list = new ArrayList<>();
    public static HashMap<Player,ArrayList<String>> playerlist = new HashMap<>();



    public static void inventory(Player player, int loop) {
        pages.put(player, loop);

        if (Main.showOnlyBallonsWithPermission) {
            File file = new File(Main.getInstance().getDataFolder(), "config.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            ArrayList<String> list = new ArrayList<>();
            ConfigurationSection cs = config.getConfigurationSection("Balloons");
            for (String key : cs.getKeys(false)) {
                if (player.hasPermission("Balloons." + key + ".permission")) {
                    list.add(key);
                }
            }
            playerlist.put(player, list);

            Inventory inventory = Bukkit.createInventory(null, 54, Main.getInstance().getConfig().getString("BalloonsMenuName") + " (" + ((loop / 45) + 1) + "/" + ((list.size() / 45) + 1) + ")");
            player.openInventory(inventory);

            InventoryItems.border(inventory);
            InventoryItems.remove(inventory);

            if (pages.get(player) > 0) {
                InventoryItems.previous(inventory);
            }



            int slot = 0;
            for (int i = 0; i < 45; i++) {
                if (list.size() > i + loop) {
                    if (player.hasPermission(config.getString("Balloons." + list.get(i + loop) + ".permission"))) {

                        ItemStack item = new ItemStack(GetSkull.createSkull(config.getString("Balloons." + list.get(i + loop) + ".head")));
                        ItemMeta itemmeta = item.getItemMeta();

                        if (config.getString("Balloons." + list.get(i + loop) + ".displayname") != null) {
                            itemmeta.setDisplayName(config.getString("Balloons." + list.get(i + loop) + ".displayname"));
                        } else {
                            itemmeta.setDisplayName("§e" + list.get(i + loop));
                        }
                        ArrayList<String> lore = new ArrayList<>();
                        lore.add("");
                        lore.add(Main.getInstance().getConfig().getString("BalloonsMenuClickToSummon"));
                        itemmeta.setLore(lore);
                        item.setItemMeta(itemmeta);
                        inventory.setItem(slot, item);
                        slot++;

                        if (slot == 45) {
                            if (slot != list.size()) {
                                InventoryItems.next(inventory);
                            }
                            return;
                        }


                    }

                }

            }




        } else {
            File file = new File(Main.getInstance().getDataFolder(), "config.yml");
            YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

            Inventory inventory = Bukkit.createInventory(null, 54, Main.getInstance().getConfig().getString("BalloonsMenuName") + " (" + ((loop / 45) + 1) + "/" + ((list.size() / 45) + 1) + ")");
            player.openInventory(inventory);

            InventoryItems.border(inventory);
            InventoryItems.remove(inventory);


            if (pages.get(player) > 0) {
                InventoryItems.previous(inventory);
            }


            int slot = 0;
            for (int i = 0; i < 45; i++) {
                if (list.size() > i + loop) {

                    ItemStack item = new ItemStack(GetSkull.createSkull(config.getString("Balloons." + list.get(i + loop) + ".head")));
                    ItemMeta itemmeta = item.getItemMeta();
                    if (config.getString("Balloons." + list.get(i + loop) + ".displayname") != null) {
                        itemmeta.setDisplayName(config.getString("Balloons." + list.get(i + loop) + ".displayname"));
                    } else {
                        itemmeta.setDisplayName("§e" + list.get(i + loop));
                    }
                    ArrayList<String> lore = new ArrayList<>();
                    lore.add("");
                    if (player.hasPermission(config.getString("Balloons." + list.get(i + loop) + ".permission"))) {
                        lore.add(Main.getInstance().getConfig().getString("BalloonsMenuClickToSummon"));
                    } else {
                        lore.add(Main.getInstance().getConfig().getString("BalloonsMenuNoPermissionToSummon"));
                    }
                    itemmeta.setLore(lore);
                    item.setItemMeta(itemmeta);
                    inventory.setItem(slot, item);
                    slot++;

                    if (slot == 45) {
                        if (slot != list.size()) {
                            InventoryItems.next(inventory);
                        }
                        return;
                    }
                }

            }

        }
    }
    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();
        if (event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfig().getString("BalloonsMenuName") + " (" + ((pages.get(player) / 45) + 1) + "/" + ((list.size() / 45) + 1)+")")) {
            event.setCancelled(true);

            if (Main.showOnlyBallonsWithPermission) {
                if (event.getCurrentItem() != null) {


                    if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                        if (SummonBalloons.balloons.containsKey(player)) {
                            SummonBalloons.removeBalloon(player);
                        }
                        SummonBalloons.playerBalloons.put(player, (playerlist.get(player).get(slot + pages.get(player))));

                        SummonBalloons.summonBalloon(player, GetSkull.createSkull(config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".head")));
                        player.closeInventory();
                    }


                    if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                        player.closeInventory();
                        SummonBalloons.removeBalloon(player);
                    }

                    if (slot == 48) {

                        inventory(player, pages.get(player) - 45);
                    }
                    if (slot == 50) {

                        inventory(player, pages.get(player) + 45);
                    }
                }


            } else {
                if (event.getCurrentItem() != null) {


                    if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                        int ballons = 0;
                        ConfigurationSection cs = config.getConfigurationSection("Balloons");
                        for (String key : cs.getKeys(false)) {
                            if (ballons == (slot + pages.get(player))) {

                                String permission = config.getString("Balloons." + key + ".permission");
                                if (permission != null) {
                                    if (player.hasPermission(permission)) {
                                        if (SummonBalloons.balloons.containsKey(player)) {
                                            SummonBalloons.removeBalloon(player);
                                        }
                                        SummonBalloons.playerBalloons.put(player, key);
                                        SummonBalloons.summonBalloon(player, GetSkull.createSkull(config.getString("Balloons." + key + ".head")));
                                        player.closeInventory();
                                    }
                                }

                                return;
                            }
                            ballons++;
                        }


                    }

                    if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                        player.closeInventory();
                        SummonBalloons.removeBalloon(player);
                    }

                    if (slot == 48) {

                        inventory(player, pages.get(player) - 45);
                    }
                    if (slot == 50) {

                        inventory(player, pages.get(player) + 45);
                    }
                }
            }
        }
    }
}
