package api.bottleofench.fruitfulapi.builders.itemstack;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

public class BowBuilder extends ItemStackBuilder implements Listener {
    protected List<Consumer<EntityShootBowEvent>> bowShootHandlers = new ArrayList<>();

    public BowBuilder() {
        super(Material.BOW);
    }

    @SafeVarargs
    public final BowBuilder addBowShootHandler(Consumer<EntityShootBowEvent>... consumers) {
        this.bowShootHandlers.addAll(List.of(consumers));
        return this;
    }

    @EventHandler
    private void onShoot(EntityShootBowEvent event) {
        if (!Objects.equals(event.getBow(), item)) return;
        this.bowShootHandlers.forEach(entityShootBowHandler -> entityShootBowHandler.accept(event));
    }
}
