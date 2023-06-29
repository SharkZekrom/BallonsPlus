package be.shark_zekrom;

import be.shark_zekrom.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RecipesGui implements Listener {

    private static final HashMap<Player, Integer> items = new HashMap<>();
    private static final ArrayList<Player> editingRecipe = new ArrayList<>();

    public static void recipeCreateGui(Player player) {
        editingRecipe.add(player);

        Inventory inventory = Bukkit.createInventory(null, 45, "Create recipe");
        ItemStack accept = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
        ItemMeta acceptMeta = accept.getItemMeta();
        acceptMeta.setDisplayName("§aAccept");
        accept.setItemMeta(acceptMeta);
        inventory.setItem(40, accept);

        Integer[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 23, 25, 26, 27, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 42, 43, 44};

        for (Integer integer : slot) {
            inventory.setItem(integer, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
        }

        player.openInventory(inventory);
    }

    public static void showRecipe(Player player, Integer number) {
        Inventory inventory = Bukkit.createInventory(null, 45, "Show recipe");
        inventory.setItem(40, new ItemStack(Material.ARROW));

        File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Integer[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 23, 25, 26, 27, 31, 32, 33, 34, 35, 36, 37, 38, 39, 41, 42, 43, 44};

        for (Integer integer : slot) {
            inventory.setItem(integer, new ItemStack(Material.LIGHT_BLUE_STAINED_GLASS_PANE));
        }

        ItemStack itemStack = ItemStack.deserialize(config.getConfigurationSection("Recipes." + number + ".item").getValues(true));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(null);
        itemStack.setItemMeta(itemMeta);

        inventory.setItem(24,itemStack);

        ArrayList<ItemStack> itemstack = new ArrayList<>();
        for (Map<?, ?> map : config.getMapList("Recipes." + number + ".items")) {
            itemstack.add(ItemStack.deserialize((Map<String, Object>) map));
        }

        Integer[] itemSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30};
        for (int i = 0; i < itemSlots.length; i++) {
            inventory.setItem(itemSlots[i], itemstack.get(i));
        }

        player.openInventory(inventory);
    }

    public static void recipeListGui(Player player, Integer start) {
        items.put(player, start);
        editingRecipe.add(player);

        File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Inventory inventory = Bukkit.createInventory(null, 54, "Recipe list");
        player.openInventory(inventory);

        if (config.getConfigurationSection("Recipes") == null) return;
        Object[] fields = config.getConfigurationSection("Recipes").getKeys(false).toArray();

        int slot = 0;
        for (Object key : fields) {
            if (Integer.parseInt((String) key) > start) {
                ItemStack itemStack = ItemStack.deserialize(config.getConfigurationSection("Recipes." + key + ".item").getValues(true));
                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setLore(null);
                itemStack.setItemMeta(itemMeta);

                inventory.setItem(slot, itemStack);
                slot++;

                if (items.get(player) > 1) {
                    inventory.setItem(48, new ItemStack(Material.ARROW));
                }

                if (slot == 45) {
                    inventory.setItem(50, new ItemStack(Material.ARROW));
                    break;
                }
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) throws IOException {
        if (event.getClickedInventory() == null) return;
        if (event.getView().getTitle().equals("Show recipe") && event.getClickedInventory().getType() == InventoryType.CHEST) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();

            if (event.getSlot() == 40) {
                recipeListGui(player, items.get(player));
            }
        } else if (event.getView().getTitle().equals("Recipe list") && event.getClickedInventory().getType() == InventoryType.CHEST) {
            event.setCancelled(true);
            Player player = (Player) event.getWhoClicked();
            if (event.getClick().equals(ClickType.RIGHT)) {

                File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                int number = event.getSlot() + items.get(player) + 1;

                Object[] fields = config.getConfigurationSection("Recipes").getKeys(false).toArray();

                int slot = 0;
                for (Object key : fields) {
                    if (Integer.parseInt((String) key) > number) {
                        config.set("Recipes." + (Integer.parseInt((String) key) - 1) ,config.getConfigurationSection("Recipes." +key));
                    }
                    slot++;
                }
                config.set("Recipes." + slot, null);

                config.save(file);
                Recipes.removeRecipe();
                Recipes.loadRecipes();
                recipeListGui(player, items.get(player));


            } else {


                if (event.getSlot() != 48 && event.getSlot() != 50) {
                    if (event.getInventory().getItem(event.getSlot()) != null) {
                        showRecipe(player, items.get(player) + (event.getSlot() + 1));
                    }
                }

                if (event.getInventory().getItem(50) != null && event.getSlot() == 50) {
                    recipeListGui(player, items.get(player) + 45);
                }

                if (event.getInventory().getItem(48) != null && event.getSlot() == 48) {
                    recipeListGui(player, items.get(player) - 45);
                }
            }
        } else if (event.getView().getTitle().equals("Create recipe") && event.getClickedInventory().getType() == InventoryType.CHEST) {
            Player player = (Player) event.getWhoClicked();
            Integer[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 13, 14, 15, 16, 17, 18, 22, 23, 25, 26, 27, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44};

            if (Arrays.asList(slot).contains(event.getSlot())) {
                event.setCancelled(true);
            }

            if (event.getSlot() == 40 && event.getInventory().getItem(24) != null) {
                player.closeInventory();

                Recipes.removeRecipe();

                File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
                YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                ArrayList<Map<String, Object>> recipes = new ArrayList<>();

                Integer[] itemSlots = {10, 11, 12, 19, 20, 21, 28, 29, 30};
                for (int i = 0; i < itemSlots.length; i++) {
                    ItemStack item = event.getInventory().getItem(itemSlots[i]);
                    recipes.add(item != null ? item.serialize() : new ItemStack(Material.AIR).serialize());
                }

                int integer = 0;
                if (config.getConfigurationSection("Recipes") != null) {
                    Object[] object = config.getConfigurationSection("Recipes").getKeys(false).toArray();
                    integer = object.length;
                }

                config.set("Recipes." + (integer + 1) + ".items", recipes);


                ItemStack balloon = event.getInventory().getItem(24);
                ItemMeta balloonMeta = balloon.getItemMeta();
                balloonMeta.setDisplayName("§eBalloons+ : " + balloon.getItemMeta().getDisplayName());
                balloonMeta.setLore(Arrays.asList("§6Percentage : 100%"));
                balloon.setItemMeta(balloonMeta);
                config.set("Recipes." + (integer + 1) + ".item",balloon.serialize());
                config.save(file);
                Recipes.loadRecipes();
            }
        }
    }
}
