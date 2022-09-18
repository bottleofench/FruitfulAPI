package api.bottleofench.fruitfulapi.events;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ChickenLayEggEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled;
    private final Chicken chicken;
    private final Item egg;

    public ChickenLayEggEvent(Chicken chicken, Item egg) {
        this.chicken = chicken;
        this.egg = egg;
    }

    public Chicken getChicken() {
        return chicken;
    }

    public Item getEgg() {
        return egg;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() { return HANDLER_LIST; }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
