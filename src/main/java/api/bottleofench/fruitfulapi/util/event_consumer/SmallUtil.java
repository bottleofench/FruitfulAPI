package api.bottleofench.fruitfulapi.util.event_consumer;

import org.bukkit.event.EventPriority;

import java.util.List;

public class SmallUtil {
    private static final List<EventPriority> priorityOrder = List.of(EventPriority.LOWEST, EventPriority.LOW, EventPriority.NORMAL,
            EventPriority.HIGH, EventPriority.HIGHEST, EventPriority.MONITOR);

    private SmallUtil() {
    }

    public static List<EventPriority> getPriorityOrder() {
        return priorityOrder;
    }
}
