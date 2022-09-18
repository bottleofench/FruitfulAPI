package api.bottleofench.fruitfulapi.builders.event;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.util.event_consumer.SmallUtil;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import java.util.List;
import java.util.function.Consumer;

public class EventBuilder<T extends Event> {
    private final List<Consumer<T>> consumer;
    private final Handler handler;

    @SafeVarargs
    public EventBuilder(EventPriority priority, Consumer<T>... consumer) {
        this(consumer);
        this.handler.eventPriority = priority;
    }

    @SafeVarargs
    public EventBuilder(Consumer<T>... consumer) {
        this.consumer = List.of(consumer);
        handler = new Handler(this);
    }

    class Handler implements Listener {
        private EventPriority eventPriority = EventPriority.NORMAL;
        private final EventBuilder<T> eventConsumer;
        protected Handler(EventBuilder<T> eventConsumer)  {
            this.eventConsumer = eventConsumer;
            Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
        }

        @EventHandler
        private void handle(T t) {
            eventConsumer.consumer.forEach(consumer1 -> SmallUtil.getPriorityOrder().forEach(priority -> {
                if (priority.equals(eventConsumer.handler.eventPriority)) consumer1.accept(t);
            }));
        }
    }
}
