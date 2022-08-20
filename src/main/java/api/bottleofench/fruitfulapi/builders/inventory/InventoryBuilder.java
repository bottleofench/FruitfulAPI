package api.bottleofench.fruitfulapi.builders.inventory;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.exceptions.InventoryBuilderException;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Consumer;

public class InventoryBuilder implements Listener, Cloneable {
    private final int defaultInventorySize = 27;
    private Inventory inventory;
    private Map<Integer, Consumer<InventoryClickEvent>> itemHandlers = new HashMap<>();
    private List<Consumer<InventoryOpenEvent>> openHandlers = new ArrayList<>();
    private List<Consumer<InventoryClickEvent>> clickHandlers = new ArrayList<>();
    private List<Consumer<InventoryCloseEvent>> closeHandlers = new ArrayList<>();

    public InventoryBuilder() {
        inventory = Bukkit.createInventory(null, defaultInventorySize);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(int size) {
        inventory = Bukkit.createInventory(null, size);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(InventoryHolder holder) {
        inventory = Bukkit.createInventory(holder, defaultInventorySize);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(InventoryHolder holder, int size) {
        inventory = Bukkit.createInventory(holder, size);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(InventoryHolder holder, InventoryType type) {
        inventory = Bukkit.createInventory(holder, type);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(InventoryHolder holder, InventoryType type, Component title) {
        inventory = Bukkit.createInventory(holder, type, title);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(InventoryHolder holder, int size, Component title) {
        inventory = Bukkit.createInventory(holder, size, title);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder(Component title) {
        inventory = Bukkit.createInventory(null, defaultInventorySize, title);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public InventoryBuilder setItem(int index, ItemStack itemStack) {
        inventory.setItem(index, itemStack);
        return this;
    }

    public InventoryBuilder setItem(int index, ItemStack itemStack, Consumer<InventoryClickEvent>... consumers) {
        inventory.setItem(index, itemStack);
        List.of(consumers).forEach(inventoryClickEventConsumer -> itemHandlers.put(index, inventoryClickEventConsumer));
        return this;
    }

    public InventoryBuilder removeItem(int index) {
        inventory.setItem(index, null);
        itemHandlers.remove(index);
        return this;
    }

    public InventoryBuilder addItems(ItemStack... itemStacks) {
        inventory.addItem(itemStacks);
        return this;
    }

    public InventoryBuilder addInventoryClickHandler(Consumer<InventoryClickEvent> onInventoryClick) {
        clickHandlers.add(onInventoryClick);
        return this;
    }

    public InventoryBuilder addItemClickHandler(int rawSlot, Consumer<InventoryClickEvent> onItemClick) {
        itemHandlers.put(rawSlot, onItemClick);
        return this;
    }

    public InventoryBuilder addOpenHandler(Consumer<InventoryOpenEvent> onOpen) {
        openHandlers.add(onOpen);
        return this;
    }

    public InventoryBuilder addCloseHandler(Consumer<InventoryCloseEvent> onClose) {
        closeHandlers.add(onClose);
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
    private void handleClick(InventoryClickEvent event) {
        if (!Objects.equals(event.getClickedInventory(), inventory)) return;

        clickHandlers.forEach(c -> c.accept(event));

        Consumer<InventoryClickEvent> clickConsumer = itemHandlers.get(event.getRawSlot());
        if (clickConsumer != null) clickConsumer.accept(event);
    }

    @EventHandler
    private void handleOpen(InventoryOpenEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        openHandlers.forEach(c -> c.accept(event));
    }

    @EventHandler
    private void handleClose(InventoryCloseEvent event) {
        if (!event.getInventory().equals(inventory)) return;
        closeHandlers.forEach(c -> c.accept(event));
    }

    @Override
    public InventoryBuilder clone() {
        try {
            InventoryBuilder clone = (InventoryBuilder) super.clone();
            clone.clickHandlers = this.clickHandlers;
            clone.inventory = this.inventory;
            clone.itemHandlers = this.itemHandlers;
            clone.openHandlers = this.openHandlers;
            clone.closeHandlers = this.closeHandlers;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new InventoryBuilderException("Clone operation is not correctly executed!");
        }
    }
}
