package api.bottleofench.fruitfulapi.itemstack;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.UUID;

public class PlayerHeadBuilder extends ItemStackBuilder {
    public PlayerHeadBuilder() {
        super(new ItemStack(Material.PLAYER_HEAD));
    }

    public PlayerHeadBuilder setTexture(String texture) {
        item.editMeta(itemMeta -> {
            SkullMeta headMeta = (SkullMeta) itemMeta;
            GameProfile profile = new GameProfile(UUID.randomUUID(), null);
            profile.getProperties().put("textures", new Property("textures", texture));
            try {
                Method mtd = headMeta.getClass().getDeclaredMethod("setProfile", GameProfile.class);
                mtd.setAccessible(true);
                mtd.invoke(headMeta, profile);
            } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException ex) {
                ex.printStackTrace();
            }
        });
        return this;
    }
}
