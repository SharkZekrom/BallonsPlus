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
    public static ArrayList<String> list = new ArrayList<>();

    public static void inventory(Player player, int loop) {
        pages.put(player, loop);
        File file = new File(Main.getInstance().getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        Inventory inventory = Bukkit.createInventory(null, 54, Main.getInstance().getConfig().getString("BalloonsMenuName") + " (" + ((loop / 45) + 1 ) + "/" + ((list.size() / 45) + 1) +")");
        player.openInventory(inventory);

        Integer[] border = {45,46,47,51,52,53};
        for (int i = 0; i < border.length; i++) {
            ItemStack borderItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta borderMeta = borderItem.getItemMeta();
            borderMeta.setDisplayName(" ");
            borderItem.setItemMeta(borderMeta);
            inventory.setItem(border[i], borderItem);

        }

        ItemStack remove = new ItemStack(Material.BARRIER);
        ItemMeta removemeta = remove.getItemMeta();
        removemeta.setDisplayName(Main.getInstance().getConfig().getString("BalloonsMenuRemove"));
        remove.setItemMeta(removemeta);
        inventory.setItem(49, remove);

        if (pages.get(player) > 0) {
            ItemStack previous = new ItemStack(Material.ARROW);
            ItemMeta previousmeta = previous.getItemMeta();
            previousmeta.setDisplayName(Main.getInstance().getConfig().getString("BalloonsMenuPrevious"));
            previous.setItemMeta(previousmeta);
            inventory.setItem(48, previous);
        }


        int slot = 0;
        for (int i = 0; i < 45; i++) {
            if (list.size() > i + loop) {

                ItemStack item = new ItemStack(GetSkull.createSkull(config.getString("Balloons." + list.get(i + loop) + ".head")));
                ItemMeta itemmeta = item.getItemMeta();
                itemmeta.setDisplayName("§e" + list.get(i + loop));
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
                        ItemStack next = new ItemStack(Material.ARROW);
                        ItemMeta nextmeta = next.getItemMeta();
                        nextmeta.setDisplayName(Main.getInstance().getConfig().getString("BalloonsMenuNext"));
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
        if (event.getView().getTitle().equalsIgnoreCase(Main.getInstance().getConfig().getString("BalloonsMenuName") + " (" + ((pages.get(player) / 45) + 1) + "/" + ((list.size() / 45) + 1)+")")) {
            event.setCancelled(true);


            if (event.getCurrentItem() != null) {


                if (event.getCurrentItem().getType().equals(Material.PLAYER_HEAD)) {
                    File file = new File(Main.getInstance().getDataFolder(), "config.yml");
                    YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

                    String permission = config.getString("Balloons." + inv.getItem(slot).getItemMeta().getDisplayName().substring(2) + ".permission");
                    if (permission != null) {
                        if (player.hasPermission(permission)) {
                            if (SummonBallons.balloons.containsKey(player)) {
                                SummonBallons.removeBalloon(player);
                            }
                            SummonBallons.summonBalloon(player, GetSkull.createSkull(config.getString("Balloons." + inv.getItem(slot).getItemMeta().getDisplayName().substring(2) + ".head")));
                            player.closeInventory();
                        }
                    }
                }

                if (event.getCurrentItem().getType().equals(Material.BARRIER)) {
                    player.closeInventory();
                    SummonBallons.removeBalloon(player);
                }

                if (slot == 48) {

                    inventory(player ,pages.get(player) - 45);
                }
                if (slot == 50) {

                    inventory(player, pages.get(player) + 45);
                }
            }
        }
    }
}
