package api.bottleofench.fruitfulapi.events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class FarmlandTrampleEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled;
    private final Player player;
    private final Block farmland;

    public FarmlandTrampleEvent(Player player, Block farmland) {
        this.player = player;
        this.farmland = farmland;
    }

    public Player getPlayer() {
        return player;
    }

    public Block getFarmland() {
        return farmland;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }
}
