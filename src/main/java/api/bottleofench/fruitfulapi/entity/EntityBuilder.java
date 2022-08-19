package api.bottleofench.fruitfulapi.entity;

import api.bottleofench.fruitfulapi.FruitfulAPI;
import api.bottleofench.fruitfulapi.exceptions.EntityBuildException;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EntityBuilder implements Listener {
    private Entity entity;
    private List<Consumer<EntityDeathEvent>> deathHandlers = new ArrayList<>();
    private List<Consumer<EntityTargetEvent>> targetHandlers = new ArrayList<>();
    private List<Consumer<EntityDamageEvent>> damageHandlers = new ArrayList<>();
    private List<Consumer<EntityDamageByEntityEvent>> attackHandlers = new ArrayList<>();


    public EntityBuilder(EntityType entityType, Location location) {
        if (entityType == null) {
            throw new EntityBuildException("EntityType cannot be null!");
        }

        if (entityType.getEntityClass() == null) {
            throw new EntityBuildException("Entity class cannot be null!");
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

    public EntityBuilder setAI(boolean flag) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setAI() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.setAI(flag);
        return this;
    }

    public EntityBuilder setAttributeBaseValue(Attribute attribute, double value) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("setAttributeBaseValue() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        livingEntity.getAttribute(attribute).setBaseValue(value);
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

    public EntityBuilder addPotionEffects(PotionEffect... effects) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("addPotionEffects() was not successfully executed because entity isn't instance of LivingEntity!");
        }

        livingEntity.addPotionEffects(List.of(effects));
        return this;
    }

    public EntityBuilder editEquipment(Consumer<EntityEquipment> equipmentConsumer) {
        if (!(entity instanceof LivingEntity livingEntity)) {
            throw new EntityBuildException("editEquipment() was not successfully executed because entity isn't instance of LivingEntity!");
        }
        equipmentConsumer.accept(livingEntity.getEquipment());
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
