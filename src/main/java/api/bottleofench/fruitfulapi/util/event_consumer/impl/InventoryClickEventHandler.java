package api.bottleofench.fruitfulapi.util.event_consumer.impl;

import api.bottleofench.fruitfulapi.util.event_consumer.EventConsumer;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.function.Consumer;

public class InventoryClickEventHandler extends EventConsumer {
    public final Consumer<InventoryClickEvent> eventConsumer;

    public InventoryClickEventHandler(EventPriority priority, Consumer<InventoryClickEvent> eventConsumer) {
        super(priority);
        this.eventConsumer = eventConsumer;
    }

    public Consumer<InventoryClickEvent> getEventConsumer() {
        return eventConsumer;
    }
}
