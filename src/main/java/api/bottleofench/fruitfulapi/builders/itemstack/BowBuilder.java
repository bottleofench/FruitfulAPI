package api.bottleofench.fruitfulapi.builders.itemstack;

import api.bottleofench.fruitfulapi.builders.event.EventBuilder;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BowBuilder<T extends ItemMeta> extends ItemStackBuilder<T> implements Listener {
    protected List<Consumer<EntityShootBowEvent>> bowShootHandlers = new ArrayList<>();

    public BowBuilder(Consumer<T> consumer) {
        super(Material.BOW, consumer);

        new EventBuilder<EntityShootBowEvent>(event -> {
            if (!Objects.equals(event.getBow(), super.build())) return;
            this.bowShootHandlers.forEach(entityShootBowHandler -> entityShootBowHandler.accept(event));
        });
    }

    @SafeVarargs
    public final BowBuilder<T> addBowShootHandler(Consumer<EntityShootBowEvent>... consumers) {
        this.bowShootHandlers.addAll(List.of(consumers));
        return this;
    }
}
