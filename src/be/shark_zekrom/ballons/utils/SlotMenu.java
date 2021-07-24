package be.shark_zekrom.ballons.utils;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;

public class SlotMenu {

    public static void slot26(Inventory inventory) {

        int[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 19, 20,21, 23, 24, 25, 26};

        for (Integer integer : slot) {
            inventory.setItem(integer, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
        }
    }

    public static void slot35(Inventory inventory) {

        int[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26,27, 28, 29, 30, 32, 33, 34, 35};

        for (Integer integer : slot) {
            inventory.setItem(integer, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
        }
    }

    public static void slot44(Inventory inventory) {

        int[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18,26, 27, 35,36, 37, 38, 39, 41, 42, 43, 44};

        for (Integer integer : slot) {
            inventory.setItem(integer, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
        }
    }

    public static void slot53(Inventory inventory) {

        int[] slot = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 17, 18, 26, 27, 35, 36, 44, 45, 46, 47, 47, 48, 50, 51, 52, 53};

        for (Integer integer : slot) {
            inventory.setItem(integer, new ItemStack(Material.ORANGE_STAINED_GLASS_PANE));
        }
    }

}
