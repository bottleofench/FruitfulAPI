package api.bottleofench.fruitfulapi.itemstack;

import api.bottleofench.fruitfulapi.exceptions.ItemStackBuildException;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class ItemStackBuilder {
    private ItemStack item;

    public ItemStackBuilder(Material itemType) {
        item = new ItemStack(itemType);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        item = itemStack;
    }

    public ItemStackBuilder() {
        item = new ItemStack(Material.AIR);
    }

    public ItemStackBuilder setType(Material material) {
        item.setType(material);
        return this;
    }

    public ItemStackBuilder setAmount(Integer itemAmt) {
        item.setAmount(itemAmt);
        return this;
    }

    public ItemStackBuilder setDisplayName(Component name) {
        item.editMeta(itemMeta -> itemMeta.displayName(name));
        return this;
    }

    public ItemStackBuilder setDamage(int damage) {
        item.editMeta(itemMeta -> ((Damageable) item).setDamage(damage));
        return this;
    }

    public ItemStackBuilder setEnchantment(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder setEnchantment(Enchantment enchantment) {
        item.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder setEnchantments(Enchantment[] enchantments, int level) {
        item.getEnchantments().clear();
        for (Enchantment enchantment : enchantments) {
            item.addUnsafeEnchantment(enchantment, level);
        }
        return this;
    }

    public ItemStackBuilder setEnchantments(Enchantment[] enchantments) {
        item.getEnchantments().clear();
        for(Enchantment enchantment : enchantments) {
            item.addUnsafeEnchantment(enchantment, 1);
        }
        return this;
    }

    public ItemStackBuilder clearEnchantment(Enchantment enchantment) {
        Map<Enchantment, Integer> itemEnchantments = item.getEnchantments();
        for(Enchantment enchantmentC : itemEnchantments.keySet()) {
            if (enchantment == enchantmentC) {
                itemEnchantments.remove(enchantmentC);
            }
        }
        return this;
    }

    public ItemStackBuilder clearEnchantments() {
        item.getEnchantments().clear();
        return this;
    }

    public ItemStackBuilder addLore(Component lore) {
        item.editMeta(itemMeta -> {
            List<Component> lores = item.lore();
            if (lores != null) lores.add(lore);
            else lores = Collections.singletonList(lore);
            itemMeta.lore(lores);
        });
        return this;
    }

    public ItemStackBuilder setLore(List<Component> lore) {
        item.editMeta(itemMeta -> itemMeta.lore(lore));
        return this;
    }

    public ItemStackBuilder clearLores() {
        item.editMeta(itemMeta -> itemMeta.lore(Collections.emptyList()));
        return this;
    }

    public ItemStackBuilder setColor(Color color) {
        if (!(item.getType() == Material.LEATHER_HELMET
                || item.getType() == Material.LEATHER_CHESTPLATE
                || item.getType() == Material.LEATHER_LEGGINGS
                || item.getType() == Material.LEATHER_BOOTS)) {
            throw new ItemStackBuildException("Material of ItemStack != leather armor!");
        }

        item.editMeta(itemMeta -> {
            LeatherArmorMeta meta = (LeatherArmorMeta) itemMeta;
            meta.setColor(color);
        });
        return this;
    }

    public ItemStackBuilder clearColor() {
        if (!(item.getType() == Material.LEATHER_HELMET
                || item.getType() == Material.LEATHER_CHESTPLATE
                || item.getType() == Material.LEATHER_LEGGINGS
                || item.getType() == Material.LEATHER_BOOTS)) {
            throw new ItemStackBuildException("Material of ItemStack != leather armor!");
        }
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        meta.setColor(null);
        item.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setTextureOfHead(String texture) {
        if (!item.getType().equals(Material.PLAYER_HEAD)) {
            throw new ItemStackBuildException("Material of ItemStack != Material.PLAYER_HEAD!");
        }
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

    public ItemStackBuilder setMeta(ItemMeta meta) {
        if (!item.setItemMeta(meta)) {
            throw new ItemStackBuildException("ItemMeta cannot be set correctly!");
        }
        return this;
    }

    public ItemStackBuilder editPersistentDataContainer(Consumer<PersistentDataContainer> dataContainerConsumer) {
        dataContainerConsumer.accept(item.getItemMeta().getPersistentDataContainer());
        return this;
    }

    public ItemStack build() {
        return item;
    }
}