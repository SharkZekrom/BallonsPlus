package be.shark_zekrom.utils;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.UUID;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class Skulls {

    public static ItemStack createSkull(String url) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1, (short) 3);
        if (url.isEmpty()) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        profile.getProperties().put("textures", new Property("textures", url));

        try {
            Field profileField = headMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(headMeta, profile);

        } catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
            error.printStackTrace();
        }
        head.setItemMeta(headMeta);
        return head;
    }

    public static String getSkull(ItemStack head) throws NoSuchFieldException, IllegalAccessException {

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();

        Field profileField = headMeta.getClass().getDeclaredField("profile");
        profileField.setAccessible(true);
        GameProfile profile = (GameProfile) profileField.get(headMeta);
        Collection<Property> textures = profile.getProperties().get("textures");


        return textures.iterator().next().getValue();
    }

}
