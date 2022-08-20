package api.bottleofench.fruitfulapi.builders.entity;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.exceptions.EntityBuilderException;
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

public class EntityBuilder implements Listener, Cloneable {
    protected EntityType defaultType = EntityType.ZOMBIE;

    protected Entity entity;


    public EntityBuilder(EntityType entityType, Location location) {
        if (entityType == null) {
            entityType = defaultType;
        }

        if (location == null) {
            throw new EntityBuilderException("Location cannot be null!");
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

    @Override
    public EntityBuilder clone() {
        try {
            EntityBuilder clone = (EntityBuilder) super.clone();
            clone.entity = this.entity;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new EntityBuilderException("Clone operation is not correctly executed!");
        }
    }
}
