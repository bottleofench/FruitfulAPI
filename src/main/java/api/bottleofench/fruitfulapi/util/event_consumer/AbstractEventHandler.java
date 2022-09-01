package api.bottleofench.fruitfulapi.util.event_consumer;

import org.bukkit.event.EventPriority;

public abstract class AbstractEventHandler {
    private final EventPriority priority;

    protected AbstractEventHandler(EventPriority priority) {
        this.priority = priority;
    }

    public final EventPriority getPriority() {
        return priority;
    }

}