package api.bottleofench.fruitfulapi.events;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class PlayerUseSpawnEggEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();
    private boolean isCancelled;
    private final Player player;

    private final ItemStack spawnEgg;
    private final LivingEntity spawnedEntity;

    public PlayerUseSpawnEggEvent(Player player, ItemStack spawnEgg, LivingEntity spawnedEntity) {
        this.player = player;
        this.spawnEgg = spawnEgg;
        this.spawnedEntity = spawnedEntity;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getSpawnEgg() {
        return spawnEgg;
    }

    public LivingEntity getSpawnedEntity() {
        return spawnedEntity;
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
