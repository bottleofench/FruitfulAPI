package api.bottleofench.fruitfulapi.builders.event;

import api.bottleofench.fruitfulapi.exceptions.EventBuilderException;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.ApiStatus;

import java.util.List;
import java.util.function.Consumer;

@ApiStatus.Experimental
public class EventBuilder implements Listener, Cloneable {
    private List<Consumer<PlayerJoinEvent>> joinHandlers;
    private List<Consumer<PlayerQuitEvent>> quitHandlers;
    private List<Consumer<PlayerInteractEvent>> interactHandlers;
    private List<Consumer<BlockBreakEvent>> blockBreakHandlers;
    private List<Consumer<BlockPlaceEvent>> blockPlaceHandlers;
    private List<Consumer<EntityDamageEvent>> entityDamageHandlers;

    public EventBuilder(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @SafeVarargs
    public final EventBuilder join(Consumer<PlayerJoinEvent>... consumers) {
        this.joinHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        joinHandlers.forEach(join -> join.accept(event));
    }

    @SafeVarargs
    public final EventBuilder quit(Consumer<PlayerQuitEvent>... consumers) {
        this.quitHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        quitHandlers.forEach(quit -> quit.accept(event));
    }

    @SafeVarargs
    public final EventBuilder interact(Consumer<PlayerInteractEvent>... consumers) {
        this.interactHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        interactHandlers.forEach(interact -> interact.accept(event));
    }

    @SafeVarargs
    public final EventBuilder blockBreak(Consumer<BlockBreakEvent>... consumers) {
        this.blockBreakHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        blockBreakHandlers.forEach(blockBreak -> blockBreak.accept(event));
    }

    @SafeVarargs
    public final EventBuilder blockPlace(Consumer<BlockPlaceEvent>... consumers) {
        this.blockPlaceHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        blockPlaceHandlers.forEach(blockPlace -> blockPlace.accept(event));
    }

    @SafeVarargs
    public final EventBuilder entityDamage(Consumer<EntityDamageEvent>... consumers) {
        this.entityDamageHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onEntityDamage(EntityDamageEvent event) {
        entityDamageHandlers.forEach(entityDamage -> entityDamage.accept(event));
    }

    @Override
    public EventBuilder clone() {
        try {
            EventBuilder clone = (EventBuilder) super.clone();
            clone.joinHandlers = this.joinHandlers;
            clone.quitHandlers = this.quitHandlers;
            clone.entityDamageHandlers = this.entityDamageHandlers;
            clone.blockBreakHandlers = this.blockBreakHandlers;
            clone.blockPlaceHandlers = this.blockPlaceHandlers;
            clone.interactHandlers = this.interactHandlers;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new EventBuilderException("Clone operation is not correctly executed!");
        }
    }
}
