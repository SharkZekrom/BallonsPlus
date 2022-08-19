package be.shark_zekrom.inventory;

import be.shark_zekrom.Main;
import be.shark_zekrom.commands.Balloons;
import be.shark_zekrom.utils.Skulls;
import be.shark_zekrom.utils.InventoryItems;
import be.shark_zekrom.utils.SummonBalloons;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Menu implements Listener {

    public static HashMap<Player, Integer> pages = new HashMap<>();
    public static ArrayList<String> list = new ArrayList<>();
    public static HashMap<Player, ArrayList<String>> playerlist = new HashMap<>();

    public static HashMap<Player, String> playerIdEditing = new HashMap<>();


    public static void inventory(Player player, int loop) {
        pages.put(player, loop);

        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if (Main.showOnlyBallonsWithPermission) {

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
                        ItemStack item;
                        ItemMeta itemmeta;
                        if (config.getString("Balloons." + list.get(i + loop) + ".item") != null) {
                            item = new ItemStack(Material.valueOf(config.getString("Balloons." + list.get(i + loop) + ".item")));
                            itemmeta = item.getItemMeta();
                            itemmeta.setCustomModelData(config.getInt("Balloons." + list.get(i + loop) + ".custommodeldata"));
                        } else {
                            item = new ItemStack(Skulls.createSkull(config.getString("Balloons." + list.get(i + loop) + ".head")));
                            itemmeta = item.getItemMeta();

                        }


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
                    ItemStack item;
                    ItemMeta itemmeta;

                    if (config.getString("Balloons." + list.get(i + loop) + ".item") != null) {
                        item = new ItemStack(Material.valueOf(config.getString("Balloons." + list.get(i + loop) + ".item")));
                        itemmeta = item.getItemMeta();
                        itemmeta.setCustomModelData(config.getInt("Balloons." + list.get(i + loop) + ".custommodeldata"));
                    } else {
                        item = new ItemStack(Skulls.createSkull(config.getString("Balloons." + list.get(i + loop) + ".head")));
                        itemmeta = item.getItemMeta();


                    }

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

    public static void editInventory(Player player, String id, ItemStack balloon) {
        Inventory inventory = Bukkit.createInventory(null, InventoryType.HOPPER, "Balloon Editing");
        player.openInventory(inventory);

        for (int i = 0; i < 4; i++) {
            ItemStack item = new ItemStack(Material.BLUE_STAINED_GLASS_PANE, 1);
            ItemMeta itemmeta = item.getItemMeta();
            itemmeta.setDisplayName(" ");
            item.setItemMeta(itemmeta);
            inventory.setItem(i, item);

        }

        ItemStack item = new ItemStack(Material.GREEN_STAINED_GLASS_PANE, 1);
        ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName("§aSave");
        item.setItemMeta(itemmeta);
        inventory.setItem(4, item);

        ItemMeta itemMeta = balloon.getItemMeta();
        itemMeta.setLore(null);
        balloon.setItemMeta(itemMeta);
        inventory.setItem(2, balloon);


        playerIdEditing.put(player, id);
    }


    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) throws IOException, NoSuchFieldException, IllegalAccessException {
        Player player = (Player) event.getWhoClicked();

        int slot = event.getSlot();

        if (event.getView().getTitle().equalsIgnoreCase("Balloon Editing")) {
            if (event.getClickedInventory().getType() != InventoryType.PLAYER) {
                if (slot == 0 || slot == 1 || slot == 3) {
                    event.setCancelled(true);
                }
            }

            if (slot == 4) {
                event.setCancelled(true);
                if (event.getView().getTopInventory().getItem(2) != null) {
                    player.closeInventory();
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String displayname = event.getInventory().getItem(2).getItemMeta().getDisplayName();

                    config.set("Balloons." + playerIdEditing.get(player) + ".item", null);
                    config.set("Balloons." + playerIdEditing.get(player) + ".displayname", null);
                    config.set("Balloons." + playerIdEditing.get(player) + ".custommodeldata", null);
                    config.set("Balloons." + playerIdEditing.get(player) + ".head", null);


                    if (event.getInventory().getItem(2).getType() == Material.PLAYER_HEAD) {
                        config.set("Balloons." + playerIdEditing.get(player) + ".head", Skulls.getSkull(event.getInventory().getItem(2)));


                    } else {
                        String item = event.getInventory().getItem(2).getType().toString();

                        int custommodeldata;
                        if (event.getInventory().getItem(2).getItemMeta().hasCustomModelData()) {
                            custommodeldata = event.getInventory().getItem(2).getItemMeta().getCustomModelData();
                        } else {
                            custommodeldata = 0;
                        }

                        config.set("Balloons." + playerIdEditing.get(player) + ".item", item);
                        config.set("Balloons." + playerIdEditing.get(player) + ".custommodeldata", custommodeldata);
                    }

                    config.set("Balloons." + playerIdEditing.get(player) + ".displayname", displayname);
                    config.save(file);

                    Balloons.reload(player);
                }
            }

        }

        if (pages.get(player) != null) {
            if (event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfig().getString("BalloonsMenuName") + " (" + ((pages.get(player) / 45) + 1) + "/" + ((list.size() / 45) + 1) + ")")) {
                    event.setCancelled(true);

                    if (Main.showOnlyBallonsWithPermission) {

                        if (event.getCurrentItem() != null) {


                            if (slot < 45) {
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                                if (event.isRightClick() && player.hasPermission("Balloons+.editing")) {
                                    editInventory(player, playerlist.get(player).get(slot + pages.get(player)), event.getCurrentItem());
                                } else {
                                    if (SummonBalloons.balloons.containsKey(player)) {
                                        if (config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".item") != null) {
                                            ItemStack itemStack = new ItemStack(Material.valueOf(config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".item")));
                                            ItemMeta itemMeta = itemStack.getItemMeta();
                                            itemMeta.setCustomModelData(config.getInt("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".custommodeldata"));
                                            itemStack.setItemMeta(itemMeta);
                                            SummonBalloons.as.get(player).getEquipment().setHelmet(itemStack);
                                        } else {
                                            SummonBalloons.as.get(player).getEquipment().setHelmet(Skulls.createSkull(config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".head")));
                                        }
                                    } else {
                                        if (config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".item") != null) {
                                            ItemStack itemStack = new ItemStack(Material.valueOf(config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".item")));
                                            ItemMeta itemMeta = itemStack.getItemMeta();
                                            itemMeta.setCustomModelData(config.getInt("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".custommodeldata"));
                                            itemStack.setItemMeta(itemMeta);
                                            SummonBalloons.summonBalloon(player, itemStack);

                                        } else {
                                            SummonBalloons.summonBalloon(player, Skulls.createSkull(config.getString("Balloons." + (playerlist.get(player).get(slot + pages.get(player))) + ".head")));
                                        }
                                    }
                                    SummonBalloons.playerBalloons.put(player, (playerlist.get(player).get(slot + pages.get(player))));
                                    player.closeInventory();
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


                    } else {
                        if (event.getCurrentItem() != null) {


                            if (slot < 45) {
                                File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                                int ballons = 0;
                                ConfigurationSection cs = config.getConfigurationSection("Balloons");
                                for (String key : cs.getKeys(false)) {
                                    if (ballons == (slot + pages.get(player))) {

                                        String permission = config.getString("Balloons." + key + ".permission");
                                        if (permission != null) {
                                            if (player.hasPermission(permission)) {
                                                if (event.isRightClick() && player.hasPermission("Balloons+.editing")) {
                                                    editInventory(player, key, event.getCurrentItem());
                                                } else {
                                                    if (SummonBalloons.balloons.containsKey(player)) {

                                                        if (config.getString("Balloons." + key + ".item") != null) {
                                                            ItemStack itemStack = new ItemStack(Material.valueOf(config.getString("Balloons." + key + ".item")));
                                                            ItemMeta itemMeta = itemStack.getItemMeta();
                                                            itemMeta.setCustomModelData(config.getInt("Balloons." + key + ".custommodeldata"));
                                                            itemStack.setItemMeta(itemMeta);
                                                            SummonBalloons.as.get(player).getEquipment().setHelmet(itemStack);
                                                        } else {
                                                            SummonBalloons.as.get(player).getEquipment().setHelmet(Skulls.createSkull(config.getString("Balloons." + key + ".head")));
                                                        }
                                                    } else {
                                                        if (config.getString("Balloons." + key + ".item") != null) {
                                                            ItemStack itemStack = new ItemStack(Material.valueOf(config.getString("Balloons." + key + ".item")));
                                                            ItemMeta itemMeta = itemStack.getItemMeta();
                                                            itemMeta.setCustomModelData(config.getInt("Balloons." + key + ".custommodeldata"));
                                                            itemStack.setItemMeta(itemMeta);
                                                            SummonBalloons.summonBalloon(player, itemStack);

                                                        } else {
                                                            SummonBalloons.summonBalloon(player, Skulls.createSkull(config.getString("Balloons." + key + ".head")));
                                                        }
                                                    }

                                                    SummonBalloons.playerBalloons.put(player, key);
                                                    player.closeInventory();
                                                }
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
                                SummonBalloons.playerBalloons.remove(player);
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
}