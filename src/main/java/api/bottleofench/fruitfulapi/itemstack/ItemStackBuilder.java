package api.bottleofench.fruitfulapi.itemstack;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.exceptions.ItemStackBuildException;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.*;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.event.Listener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Consumer;

public class ItemStackBuilder implements Listener {
    private ItemStack item;
    private List<Consumer<PlayerInteractEvent>> interactHandlers = new ArrayList<>();

    public ItemStackBuilder(Material itemType) {
        item = new ItemStack(itemType);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public ItemStackBuilder(ItemStack itemStack) {
        item = itemStack;
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public ItemStackBuilder(Material itemType, int amount) {
        item = new ItemStack(itemType, amount);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public ItemStackBuilder() {
        item = new ItemStack(Material.AIR);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public ItemStackBuilder setType(Material material) {
        item.setType(material);
        return this;
    }

    public ItemStackBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setDisplayName(Component name) {
        item.editMeta(itemMeta -> itemMeta.displayName(name));
        return this;
    }

    public ItemStackBuilder setDamage(int damage) {
        if (!(item.getItemMeta() instanceof Damageable)) {
            throw new ItemStackBuildException("ItemMeta of ItemStack isn't instance of Damageable!");
        }
        item.editMeta(itemMeta -> ((Damageable) item).setDamage(damage));
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment, int level) {
        item.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder addEnchantment(Enchantment enchantment) {
        item.addUnsafeEnchantment(enchantment, 1);
        return this;
    }

    public ItemStackBuilder addItemFlags(ItemFlag... flags) {
        item.addItemFlags(flags);
        return this;
    }

    public ItemStackBuilder removeItemFlags(ItemFlag... flags) {
        item.removeItemFlags(flags);
        return this;
    }

    public ItemStackBuilder setEnchantments(int level, Enchantment... enchantments) {
        item.getEnchantments().clear();
        for (Enchantment enchantment : enchantments) {
            item.addUnsafeEnchantment(enchantment, level);
        }
        return this;
    }

    public ItemStackBuilder setEnchantments(Enchantment... enchantments) {
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

    public ItemStackBuilder setLore(Component... lore) {
        item.editMeta(itemMeta -> itemMeta.lore(List.of(lore)));
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

    public ItemStackBuilder setPlayerHeadTexture(String texture) {
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

    public ItemStackBuilder editMeta(Consumer<ItemMeta> meta) {
        meta.accept(item.getItemMeta());
        return this;
    }

    public ItemStackBuilder editPersistentDataContainer(Consumer<PersistentDataContainer> dataContainerConsumer) {
        dataContainerConsumer.accept(item.getItemMeta().getPersistentDataContainer());
        return this;
    }

    public ItemStackBuilder addPages(Component... pages) {
        if (!item.getType().equals(Material.WRITTEN_BOOK)) {
            throw new ItemStackBuildException("ItemStack type is not a book!");
        }
        item.editMeta(itemMeta -> {
            BookMeta meta = (BookMeta) itemMeta;
            meta.addPages(pages);
        });
        return this;
    }

    public ItemStackBuilder setPage(int id, Component page) {
        if (!item.getType().equals(Material.WRITTEN_BOOK)) {
            throw new ItemStackBuildException("ItemStack type is not a book!");
        }
        item.editMeta(itemMeta -> {
            BookMeta meta = (BookMeta) itemMeta;
            meta.page(id, page);
        });
        return this;
    }

    public ItemStackBuilder setPotionColor(Color color) {
        if (!(item.getType().equals(Material.POTION) ||
                item.getType().equals(Material.LINGERING_POTION) ||
                item.getType().equals(Material.SPLASH_POTION))) {
            throw new ItemStackBuildException("ItemStack type is not a potion!");
        }

        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            meta.setColor(color);
        });
        return this;
    }

    public ItemStackBuilder setBasePotionData(PotionData data) {
        if (!(item.getType().equals(Material.POTION) ||
                item.getType().equals(Material.LINGERING_POTION) ||
                item.getType().equals(Material.SPLASH_POTION))) {
            throw new ItemStackBuildException("ItemStack type is not a potion!");
        }

        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            meta.setBasePotionData(data);
        });
        return this;
    }

    public ItemStackBuilder setCustomEffects(boolean override, PotionEffect... effects) {
        if (!(item.getType().equals(Material.POTION) ||
                item.getType().equals(Material.LINGERING_POTION) ||
                item.getType().equals(Material.SPLASH_POTION))) {
            throw new ItemStackBuildException("ItemStack type is not a potion!");
        }

        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            meta.clearCustomEffects();
            List.of(effects).forEach(potionEffect -> meta.addCustomEffect(potionEffect, override));
        });
        return this;
    }

    public ItemStackBuilder addCustomEffects(boolean override, PotionEffect... effects) {
        if (!(item.getType().equals(Material.POTION) ||
                item.getType().equals(Material.LINGERING_POTION) ||
                item.getType().equals(Material.SPLASH_POTION))) {
            throw new ItemStackBuildException("ItemStack type is not a potion!");
        }

        item.editMeta(itemMeta -> {
            PotionMeta meta = (PotionMeta) itemMeta;
            List.of(effects).forEach(potionEffect -> meta.addCustomEffect(potionEffect, override));
        });
        return this;
    }

    public ItemStackBuilder addInteractHandler(Consumer<PlayerInteractEvent> onInteract) {
        interactHandlers.add(onInteract);
        return this;
    }

    @EventHandler
    private void handleInteract(PlayerInteractEvent event) {
        if (!Objects.equals(event.getItem(), item)) return;
        interactHandlers.forEach(eventConsumer -> eventConsumer.accept(event));
    }

    public ItemStack build() {
        return item;
    }
}