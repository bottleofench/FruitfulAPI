package api.bottleofench.fruitfulapi.util.event_consumer;

import org.bukkit.event.EventPriority;

public abstract class EventConsumer {
    public final EventPriority priority;

    public EventConsumer(EventPriority priority) {
        this.priority = priority;
    }

    public EventPriority getPriority() {
        return priority;
    }
}
