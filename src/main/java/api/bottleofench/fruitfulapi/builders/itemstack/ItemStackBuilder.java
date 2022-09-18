package api.bottleofench.fruitfulapi.builders.itemstack;

import api.bottleofench.fruitfulapi.exceptions.ItemStackBuilderException;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ItemStackBuilder<T extends ItemMeta> implements Cloneable {
    private final ItemStack item;
    private T itemMeta;
    private List<Consumer<PlayerInteractEvent>> interactHandlers = new ArrayList<>();

    public ItemStackBuilder(ItemStackBuilder<T> builder) {
        this.item = builder.item;
        this.itemMeta = builder.itemMeta;
        this.interactHandlers = builder.interactHandlers;
    }

    public ItemStackBuilder(Material material, Consumer<T> consumer) {
        this(material, 1, consumer);
    }

    public ItemStackBuilder(Material material, int amount, Consumer<T> consumer) {
        if (material == null) {
            throw new ItemStackBuilderException("Material cannot be null!");
        }
        if (consumer == null) {
            throw new ItemStackBuilderException("ItemMeta cannot be null!");
        }
        item = new ItemStack(material, amount);

        consumer.accept(itemMeta);
        item.setItemMeta(itemMeta);
    }

    public ItemStackBuilder<T> addInteractHandler(Consumer<PlayerInteractEvent> onInteract) {
        interactHandlers.add(onInteract);
        return this;
    }

    public ItemStack build() {
        return item;
    }

    @Override
    public ItemStackBuilder<T> clone() {
        return new ItemStackBuilder<>(this);
    }
}
