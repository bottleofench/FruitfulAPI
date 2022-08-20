package api.bottleofench.fruitfulapi.entity;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.exceptions.EntityBuildException;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityBuilder implements Listener {
    protected EntityType defaultType = EntityType.ZOMBIE;

    protected Entity entity;
    protected List<Consumer<EntityDeathEvent>> deathHandlers = new ArrayList<>();
    protected List<Consumer<EntityTargetEvent>> targetHandlers = new ArrayList<>();
    protected List<Consumer<EntityDamageEvent>> damageHandlers = new ArrayList<>();
    protected List<Consumer<EntityDamageByEntityEvent>> attackHandlers = new ArrayList<>();


    public EntityBuilder(EntityType entityType, Location location) {
        if (entityType == null) {
            entityType = defaultType;
        }

        if (location == null) {
            throw new EntityBuildException("Location cannot be null!");
        }

        entity = location.getWorld().spawnEntity(location, entityType);
        Bukkit.getPluginManager().registerEvents(this, FruitfulAPI.getInstance());
    }

    public EntityBuilder setInvulnerable(boolean flag) {
        entity.setInvulnerable(flag);
        return this;
    }

    public EntityBuilder setCustomName(Component name) {
        entity.customName(name);
        return this;
    }

    public EntityBuilder setGlowing(boolean flag) {
        entity.setGlowing(flag);
        return this;
    }

    public EntityBuilder setCustomNameVisible(boolean flag) {
        entity.setCustomNameVisible(flag);
        return this;
    }

    public EntityBuilder setVisualFire(boolean flag) {
        entity.setVisualFire(flag);
        return this;
    }

    public EntityBuilder setSilent(boolean flag) {
        entity.setSilent(flag);
        return this;
    }

    public EntityBuilder setGravity(boolean flag) {
        entity.setGravity(flag);
        return this;
    }

    public EntityBuilder setFireTicks(int ticks) {
        entity.setFireTicks(ticks);
        return this;
    }

    public EntityBuilder setFreezeTicks(int ticks) {
        entity.setFreezeTicks(ticks);
        return this;
    }

    public EntityBuilder setPassenger(Entity passenger) {
        entity.addPassenger(passenger);
        return this;
    }

    public EntityBuilder eject() {
        entity.eject();
        return this;
    }

    public Entity build() {
        return entity;
    }

    public EntityBuilder addDeathListener(Consumer<EntityDeathEvent> deathEventConsumer) {
        deathHandlers.add(deathEventConsumer);
        return this;
    }

    public EntityBuilder addTargetListener(Consumer<EntityTargetEvent> targetEventConsumer) {
        targetHandlers.add(targetEventConsumer);
        return this;
    }

    public EntityBuilder addDamageListener(Consumer<EntityDamageEvent> damageEventConsumer) {
        damageHandlers.add(damageEventConsumer);
        return this;
    }

    public EntityBuilder addAttackListener(Consumer<EntityDamageByEntityEvent> damageByEntityEventConsumer) {
        attackHandlers.add(damageByEntityEventConsumer);
        return this;
    }

    @EventHandler
    private void onDeath(EntityDeathEvent event) {
        if (!event.getEntity().equals(entity)) return;
        deathHandlers.forEach(deathHandler -> deathHandler.accept(event));
    }

    @EventHandler
    private void onTarget(EntityTargetEvent event) {
        if (!event.getEntity().equals(entity)) return;
        targetHandlers.forEach(targetHandler -> targetHandler.accept(event));
    }

    @EventHandler
    private void onDamage(EntityDamageEvent event) {
        if (!event.getEntity().equals(entity)) return;
        damageHandlers.forEach(damageHandler -> damageHandler.accept(event));
    }

    @EventHandler
    private void onAttack(EntityDamageByEntityEvent event) {
        if (!event.getDamager().equals(entity)) return;
        attackHandlers.forEach(attackHandler -> attackHandler.accept(event));
    }

}
