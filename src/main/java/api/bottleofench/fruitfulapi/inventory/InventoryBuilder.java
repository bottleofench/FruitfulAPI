package api.bottleofench.fruitfulapi.inventory;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;
import org.bukkit.event.Listener;

public class InventoryBuilder implements Listener {
    private final int defaultInventorySize = 27;
    private Inventory inventory;
    private final Map<Integer, Consumer<InventoryClickEvent>> itemHandlers = new HashMap<>();
    private List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
    private List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
    private List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();

    public InventoryBuilder() {
        inventory = Bukkit.createInventory(null, defaultInventorySize);
    }

    public InventoryBuilder(int size) {
        inventory = Bukkit.createInventory(null, size);
    }

    public InventoryBuilder(InventoryHolder holder) {
        inventory = Bukkit.createInventory(holder, defaultInventorySize);
    }

    public InventoryBuilder(InventoryHolder holder, int size) {
        inventory = Bukkit.createInventory(holder, size);
    }

    public InventoryBuilder(InventoryHolder holder, InventoryType type) {
        inventory = Bukkit.createInventory(holder, type);
    }

    public InventoryBuilder(InventoryHolder holder, InventoryType type, Component title) {
        inventory = Bukkit.createInventory(holder, type, title);
    }

    public InventoryBuilder(InventoryHolder holder, int size, Component title) {
        inventory = Bukkit.createInventory(holder, size, title);
    }

    public InventoryBuilder(Component title) {
        inventory = Bukkit.createInventory(null, defaultInventorySize, title);
    }

    public InventoryBuilder setItem(int index, ItemStack itemStack) {
        inventory.setItem(index, itemStack);
        return this;
    }

    public InventoryBuilder addItems(ItemStack... itemStacks) {
        inventory.addItem(itemStacks);
        return this;
    }

    public InventoryBuilder addInventoryClickHandler(Consumer<InventoryClickEvent> eventConsumer) {
        clickHandlers.add(eventConsumer);
        return this;
    }

    public InventoryBuilder addItemClickHandler(int rawSlot, Consumer<InventoryClickEvent> eventConsumer) {
        itemHandlers.put(rawSlot, eventConsumer);
        return this;
    }

    public InventoryBuilder addOpenHandler(Consumer<InventoryOpenEvent> eventConsumer) {
        openHandlers.add(eventConsumer);
        return this;
    }

    public InventoryBuilder addCloseHandler(Consumer<InventoryCloseEvent> eventConsumer) {
        closeHandlers.add(eventConsumer);
        return this;
    }

    public InventoryBuilder open(Player player) {
        player.openInventory(inventory);
        return this;
    }

    public Inventory build() {
        return inventory;
    }

    @EventHandler
    private void handleClick(InventoryClickEvent e) {
        if (!Objects.equals(e.getClickedInventory(), inventory)) return;

        clickHandlers.forEach(c -> c.accept(e));

        Consumer<InventoryClickEvent> clickConsumer = itemHandlers.get(e.getRawSlot());
        if (clickConsumer != null) clickConsumer.accept(e);
    }

    @EventHandler
    private void handleOpen(InventoryOpenEvent e) {
        if (!e.getInventory().equals(inventory)) return;
        openHandlers.forEach(c -> c.accept(e));
    }

    @EventHandler
    private void handleClose(InventoryCloseEvent e) {
        if (!e.getInventory().equals(inventory)) return;
        closeHandlers.forEach(c -> c.accept(e));
    }
}
