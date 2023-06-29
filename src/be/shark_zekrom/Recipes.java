package be.shark_zekrom;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;

import java.io.File;
import java.util.*;

public class Recipes {

    public static void loadRecipes() {
        File file = new File(Main.getPlugin(Main.class).getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        if (config.getConfigurationSection("Recipes") != null) {
            Set<String> recipeKeys = config.getConfigurationSection("Recipes").getKeys(false);

            for (String key : recipeKeys) {
                List<Map<?, ?>> itemList = config.getMapList("Recipes." + key + ".items");
                List<ItemStack> itemStacks = new ArrayList<>();

                for (Map<?, ?> map : itemList) {
                    itemStacks.add(ItemStack.deserialize((Map<String, Object>) map));
                }

                StringBuilder shape1 = new StringBuilder();
                StringBuilder shape2 = new StringBuilder();
                StringBuilder shape3 = new StringBuilder();

                Character[] shape = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};

                for (int i = 0; i < 3; i++) {
                    if (itemStacks.get(i).getType() != Material.AIR) {
                        shape1.append(shape[i]);
                    } else {
                        shape1.append(" ");
                    }
                }
                for (int i = 3; i < 6; i++) {
                    if (itemStacks.get(i).getType() != Material.AIR) {
                        shape2.append(shape[i]);
                    } else {
                        shape2.append(" ");
                    }
                }
                for (int i = 6; i < 9; i++) {
                    if (itemStacks.get(i).getType() != Material.AIR) {
                        shape3.append(shape[i]);
                    } else {
                        shape3.append(" ");
                    }
                }

                ShapedRecipe recipe = new ShapedRecipe(new NamespacedKey(Main.getPlugin(Main.class), "item_" + key), ItemStack.deserialize(config.getConfigurationSection("Recipes." + key + ".item").getValues(true)))
                        .shape(shape1.toString(), shape2.toString(), shape3.toString());

                for (int i = 0; i < 9; i++) {
                    if (itemStacks.get(i).getType() != Material.AIR) {
                        recipe.setIngredient(shape[i], new RecipeChoice.ExactChoice(itemStacks.get(i)));
                    }
                }
                Bukkit.addRecipe(recipe);
            }
        }
    }

    public static void removeRecipe() {
        Bukkit.resetRecipes();
    }
}




