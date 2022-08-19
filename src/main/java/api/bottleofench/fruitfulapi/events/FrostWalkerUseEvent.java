package api.bottleofench.fruitfulapi.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class FrostWalkerUseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled;
    private final Player player;
    private final Block frostedIce;

    public FrostWalkerUseEvent(Player player, Block frostedIce) {
        this.player = player;
        this.frostedIce = frostedIce;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getFrostedIce() {
        return frostedIce;
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
