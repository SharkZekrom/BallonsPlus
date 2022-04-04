package be.shark_zekrom.balloons.utils;

import be.shark_zekrom.balloons.Main;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class InventoryItems {


    public static void border(Inventory inventory) {
        Integer[] border = {45, 46, 47, 51, 52, 53};
        for (int i = 0; i < border.length; i++) {
            ItemStack borderItem = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
            ItemMeta borderMeta = borderItem.getItemMeta();
            borderMeta.setDisplayName(" ");
            borderItem.setItemMeta(borderMeta);
            inventory.setItem(border[i], borderItem);
        }

    }

    public static void remove(Inventory inventory) {

        ItemStack remove = new ItemStack(Material.BARRIER);
        ItemMeta removemeta = remove.getItemMeta();
        removemeta.setDisplayName(Main.getInstance().getConfig().getString("BalloonsMenuRemove"));
        remove.setItemMeta(removemeta);
        inventory.setItem(49, remove);
    }

    public static void next(Inventory inventory) {
        ItemStack next = new ItemStack(Material.ARROW);
        ItemMeta nextmeta = next.getItemMeta();
        nextmeta.setDisplayName(Main.getInstance().getConfig().getString("BalloonsMenuNext"));
        next.setItemMeta(nextmeta);
        inventory.setItem(50, next);
    }

    public static void previous(Inventory inventory) {
        ItemStack previous = new ItemStack(Material.ARROW);
        ItemMeta previousmeta = previous.getItemMeta();
        previousmeta.setDisplayName(Main.getInstance().getConfig().getString("BalloonsMenuPrevious"));
        previous.setItemMeta(previousmeta);
        inventory.setItem(48, previous);
    }
}

