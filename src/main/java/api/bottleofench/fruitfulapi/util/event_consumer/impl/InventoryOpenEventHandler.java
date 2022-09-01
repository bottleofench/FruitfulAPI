package api.bottleofench.fruitfulapi.util.event_consumer.impl;

import api.bottleofench.fruitfulapi.util.event_consumer.EventConsumer;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;

import java.util.function.Consumer;

public class InventoryOpenEventHandler extends EventConsumer {
    public final Consumer<InventoryOpenEvent> eventConsumer;

    public InventoryOpenEventHandler(EventPriority priority, Consumer<InventoryOpenEvent> eventConsumer) {
        super(priority);
        this.eventConsumer = eventConsumer;
    }

    public Consumer<InventoryOpenEvent> getEventConsumer() {
        return eventConsumer;
    }
}
