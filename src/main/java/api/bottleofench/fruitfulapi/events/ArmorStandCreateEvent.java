package api.bottleofench.fruitfulapi.events;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ArmorStandCreateEvent extends Event implements Cancellable {
    private static final HandlerList HANDLER_LIST = new HandlerList();

    private boolean isCancelled;
    private final Player player;
    private final ArmorStand armorStand;

    public ArmorStandCreateEvent(Player player, ArmorStand armorStand) {
        this.player = player;
        this.armorStand = armorStand;
    }

    public Player getPlayer() {
        return player;
    }

    public ArmorStand getArmorStand() {
        return armorStand;
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
