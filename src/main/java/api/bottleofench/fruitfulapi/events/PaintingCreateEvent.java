package api.bottleofench.fruitfulapi.events;

import org.bukkit.entity.Painting;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PaintingCreateEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled;
    private final Player player;
    private final Painting painting;

    public PaintingCreateEvent(Player player, Painting painting) {
        this.player = player;
        this.painting = painting;
    }

    public Player getPlayer() {
        return player;
    }

    public Painting getPainting() {
        return painting;
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
