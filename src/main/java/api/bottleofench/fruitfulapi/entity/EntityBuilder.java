package api.bottleofench.fruitfulapi.entity;

import api.bottleofench.fruitfulapi.exceptions.EntityBuildException;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class EntityBuilder {
    private Entity entity;

    public EntityBuilder(EntityType entityType) {
        if (entityType == null) {
            throw new EntityBuildException("EntityType cannot be null!");
        }

        if (entityType.getEntityClass() == null) {
            throw new EntityBuildException("Entity class cannot be null!");
        }

        this.entity = entityType.getEntityClass().cast(Entity.class);
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

    public EntityBuilder setAI(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setAI() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setAI(flag);
        return this;
    }

    public EntityBuilder setInvisible(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setInvisible() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setInvisible(flag);
        return this;
    }

    public EntityBuilder setHealth(double health) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setHealth() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setHealth(health);
        return this;
    }

    public EntityBuilder setMaxHealth(double maxHealth) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setMaxHealth() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(maxHealth);
        return this;
    }

    public EntityBuilder setRemoveWhenFarAway(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setRemoveWhenFarAway() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setRemoveWhenFarAway(flag);
        return this;
    }

    public boolean spawnAt(Location location) {
        return entity.spawnAt(location);
    }

    public Entity build() {
        return entity;
    }
}
