package api.bottleofench.fruitfulapi.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FrostWalkerUseEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled;
    private final Player player;
    private final Block frosted_ice;

    public FrostWalkerUseEvent(Player player, Block frosted_ice) {
        this.player = player;
        this.frosted_ice = frosted_ice;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getFrostedIce() {
        return frosted_ice;
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
