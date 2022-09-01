package api.bottleofench.fruitfulapi.util.event_consumer.impl;

import api.bottleofench.fruitfulapi.util.event_consumer.EventConsumer;
import org.bukkit.event.EventPriority;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.function.Consumer;

public class InventoryCloseEventHandler extends EventConsumer {
    public final Consumer<InventoryCloseEvent> eventConsumer;

    public InventoryCloseEventHandler(EventPriority priority, Consumer<InventoryCloseEvent> eventConsumer) {
        super(priority);
        this.eventConsumer = eventConsumer;
    }

    public Consumer<InventoryCloseEvent> getEventConsumer() {
        return eventConsumer;
    }
}
