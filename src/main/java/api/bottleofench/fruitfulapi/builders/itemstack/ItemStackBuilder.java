package api.bottleofench.fruitfulapi.builders.itemstack;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.exceptions.ItemStackBuildException;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.*;
import java.util.function.Consumer;

public class ItemStackBuilder implements Listener {
    protected ItemStack item;
    protected List<Consumer<PlayerInteractEvent>> interactHandlers = new ArrayList<>();

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