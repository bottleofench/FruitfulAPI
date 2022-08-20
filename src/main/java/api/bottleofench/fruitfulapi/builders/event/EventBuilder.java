package api.bottleofench.fruitfulapi.builders.event;

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
public class EventBuilder implements Listener {
    private List<Consumer<PlayerJoinEvent>> joinHandlers;
    private List<Consumer<PlayerQuitEvent>> quitHandlers;
    private List<Consumer<PlayerInteractEvent>> interactHandlers;
    private List<Consumer<BlockBreakEvent>> blockBreakHandlers;
    private List<Consumer<BlockPlaceEvent>> blockPlaceHandlers;
    private List<Consumer<EntityDamageEvent>> entityDamageHandlers;

    public EventBuilder(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    public EventBuilder join(Consumer<PlayerJoinEvent>... consumers) {
        this.joinHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event) {
        joinHandlers.forEach(join -> join.accept(event));
    }

    public EventBuilder quit(Consumer<PlayerQuitEvent>... consumers) {
        this.quitHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event) {
        quitHandlers.forEach(quit -> quit.accept(event));
    }

    public EventBuilder interact(Consumer<PlayerInteractEvent>... consumers) {
        this.interactHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onInteract(PlayerInteractEvent event) {
        interactHandlers.forEach(interact -> interact.accept(event));
    }

    public EventBuilder blockBreak(Consumer<BlockBreakEvent>... consumers) {
        this.blockBreakHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        blockBreakHandlers.forEach(blockBreak -> blockBreak.accept(event));
    }

    public EventBuilder blockPlace(Consumer<BlockPlaceEvent>... consumers) {
        this.blockPlaceHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onBlockPlace(BlockPlaceEvent event) {
        blockPlaceHandlers.forEach(blockPlace -> blockPlace.accept(event));
    }

    public EventBuilder entityDamage(Consumer<EntityDamageEvent>... consumers) {
        this.entityDamageHandlers = List.of(consumers);
        return this;
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        entityDamageHandlers.forEach(entityDamage -> entityDamage.accept(event));
    }
}
